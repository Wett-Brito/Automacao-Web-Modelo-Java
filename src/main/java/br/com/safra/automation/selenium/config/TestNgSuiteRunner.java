package br.com.safra.automation.selenium.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.FailurePolicy;
import org.testng.xml.XmlSuite.ParallelMode;

import br.com.safra.automation.selenium.config.properties.ExecutionProperties;
import br.com.safra.automation.selenium.domain.OrderOfExecution;
import br.com.safra.automation.selenium.domain.TestPlatform;
import br.com.safra.automation.selenium.exceptions.AutomationException;
import br.com.safra.automation.selenium.exceptions.ConfigurationException;

import org.testng.xml.XmlTest;

public class TestNgSuiteRunner
{
	private static final Logger LOG = LogManager.getLogger(TestNgSuiteRunner.class);
	protected static final ExecutionProperties executionProperties = ExecutionProperties.getInstance();
	
	/**Executa uma suíte de testng localmente ou sobre o MobileCenter.
	 * 
	 * @param testClassNames os nomes canônicos das classes de teste.
	 * @param devices os udids dos dispositivos onde será feita a execução.
	 */
	protected void execute( List<String> classesParaExecutar, String IdTeste )
	{

		LOG.info( "------------------------ Início da execução ------------------------" );
		
		XmlSuite suite = null;
				
		suite = setUpLocalSuite( classesParaExecutar, IdTeste);

		
		TestPlatform testEnvironment = executionProperties.getTestPlatform();
		
		try {
			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add( suite );
			TestNG tng = new TestNG();
			tng.setPreserveOrder( true );
			tng.setUseDefaultListeners( false );
			tng.setXmlSuites( suites );
			tng.run();
		} catch (Exception e) {
			String mensagem = "Falha ao iniciar suite de teste sobre a plataforma '" + testEnvironment + "' ...";
			LOG.error( mensagem, e );
			throw new AutomationException( mensagem );
		}
	
	}	
	
	/**Executa uma suíte de testng para abastecimento de massa de teste.
	 * 
	 * @param testClassNames os nomes canônicos das classes de teste.
	 * @param includedGroups os grupos a ser incluídos, ou null se nenhum.
	 * @param dispositivosDeExecucao os udids dos dispositivos onde será feita a execução.
	 * @param threadCount a quantidade de threads simultâneas.
	 * @param dispositivosDeMassa os udids dos dispositivos que
	 * se quer abastecer de massa, cujos usuários serão usados para logar.
	 */
	protected void executarMassa(
			List<String> testClassNames,
			List<String> includedGroups,
			List<String> dispositivosDeExecucao,
			int threadCount,
			List<String> dispositivosDeMassa )
	{
		LOG.info( "------------------------ Início da execução ------------------------" );
		
		XmlSuite suite = null;
		XmlRun runTag = null;
		XmlGroups groups = null;
		
		if ( includedGroups != null )
		{
			runTag = new XmlRun();
			includedGroups.forEach( runTag::onInclude );
			groups = new XmlGroups();
			groups.setRun( runTag );
		}
		
		suite = setUpMassa( testClassNames, groups, dispositivosDeExecucao,
				threadCount, dispositivosDeMassa );
		
		try {
			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			suites.add( suite );
			TestNG tng = new TestNG();
			tng.setPreserveOrder( true );
			tng.setUseDefaultListeners( false );
			tng.setXmlSuites( suites );
			tng.run();
		} catch (Exception e) {
			String message = "Falha ao iniciar suite de teste sobre a plataforma 'MASSA' ...";
			LOG.error( e.getMessage(), e );
			throw new AutomationException( message );
		}
	}
	
	/**Prepara uma suíte de Testng para execução local, em um único
	 * dispositivo somente.
	 * 
	 * @param testClassNames os nomes canônicos das classes de teste.
	 * @param groups os grupos a ser incluídos, ou null se nenhum.
	 * @param devices os udids dos dispositivos onde será feita a execução.
	 * @return suíte de Testng
	 */
	protected XmlSuite setUpLocalSuite( List<String> testClassNames,
									  String IdTest )
	{
		LOG.info("Gerando suíte para execução local...");
		
		ArrayList<XmlClass> classes = new ArrayList<XmlClass>();

		for ( String className : testClassNames )
		{
			classes.add( new XmlClass(className) );
		}
		
		XmlSuite suite = new XmlSuite();
		suite.setName("LocalSuite");
		suite.setConfigFailurePolicy( FailurePolicy.SKIP );
		
		suite.addListener(br.com.safra.automation.selenium.listeners
				.TestListener.class.getCanonicalName());
		suite.addListener(br.com.safra.automation.selenium.listeners
				.SuiteListener.class.getCanonicalName());

		XmlTest test = new XmlTest(suite, 0);
		test.setName( "Test-Local-" + IdTest );
		Map<String, String> parameters = new HashMap<String, String>();
		test.setParameters(parameters);
		test.setXmlClasses( classes );
		
		return suite;
	}
	
	/**Prepara suite de teste para o MobileCenter, com as classes executadas
	 * em seqüência, e sobre os dispositivos em paralelo.
	 * 
	 * @param classes Testar quais classes.
	 * @param groups Testar somente as classes de quais grupos, se houver.
	 * @param devices Executar em quais dispositivos.
	 * @param threadCount Quantos testes em paralelo (em quantos dispositivos
	 * ao mesmo tempo).
	 * @return
	 */
	protected XmlSuite setUpMobileCenterSuite(
			List<String> testClassNames,
			XmlGroups groups,
			List<String> devices,
			int threadCount )
	{
		LOG.info( "Gerando suíte para execução sobre o MobileCenter em 'COLUNA' ..." );
		
		if ( threadCount <= 0 )
		{
			String message = "É necessário definir o número de threads.";
			throw new AutomationException( message );
		}
		
		ArrayList<XmlClass> classes = new ArrayList<XmlClass>();
		for ( String className : testClassNames )
		{
			classes.add( new XmlClass(className) );
		}
		
		XmlSuite suite = new XmlSuite();
		suite.setName( "MobileCenterSuite" );
		suite.setParallel( ParallelMode.TESTS );
		suite.setConfigFailurePolicy( FailurePolicy.SKIP );
		
		suite.addListener( br.com.safra.automation.selenium.listeners.
				TestListener.class.getCanonicalName() );
		
		suite.addListener( br.com.safra.automation.selenium.listeners.
				SuiteListener.class.getCanonicalName() );
		
		int i = 0;
		for (String deviceInfo : devices) {
			LOG.info("Gerando um <test> para o dispositivo '" + deviceInfo + "' ...");

			XmlTest test = new XmlTest(suite, i);
			test.setName( "Test-MobileCenter-" + deviceInfo );
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put( "udid", deviceInfo );
			test.setParameters(parameters);
			if ( groups != null )
			{
				test.setGroups( groups );
			}
			test.setXmlClasses( classes );
			
			i++;
		}
		
		LOG.info("Número de threads em paralelo na suíte: " + threadCount);
		suite.setThreadCount(threadCount);
		return suite;
	}
	
	/**Prepara suite de teste para o MobileCenter, com classes alternadas,
	 * de forma que a execução sempre use o máximo de licenças disponíveis.
	 * Isto é, a continuação da execução das próximas classes nos próximos
	 * dispositivos não aguarda o término da execução das classes anteriores
	 * nos dispositivos anteriores.
	 * 
	 * @param classes Testar quais classes.
	 * @param groups Testar somente as classes de quais grupos, se houver.
	 * @param devices Executar em quais dispositivos.
	 * @param threadCount Quantos testes em paralelo (em quantos dispositivos
	 * ao mesmo tempo).
	 * @return
	 */
	protected XmlSuite setUpMobileCenterAlternatingSuite(
			List<String> testClassNames,
			XmlGroups groups,
			List<String> devices,
			int threadCount )
	{
		LOG.info( "Gerando suíte para execução sobre o MobileCenter em 'LINHA' ..." );
		
		if ( threadCount <= 0 )
		{
			String message = "É necessário definir o número de threads.";
			throw new AutomationException( message );
		}
		
		ArrayList<ArrayList<XmlClass>> classes = new ArrayList<ArrayList<XmlClass>>();
		for ( String className : testClassNames )
		{
			ArrayList<XmlClass> singleList = new ArrayList<XmlClass>();
			singleList.add( new XmlClass(className) );
			classes.add( singleList );
		}
		
		XmlSuite suite = new XmlSuite();
		suite.setName( "MobileCenterSuite" );
		suite.setParallel( ParallelMode.TESTS );
		suite.setConfigFailurePolicy( FailurePolicy.SKIP );
		
		suite.addListener( br.com.safra.automation.selenium.listeners.
				TestListener.class.getCanonicalName() );
		
		suite.addListener( br.com.safra.automation.selenium.listeners.
				SuiteListener.class.getCanonicalName() );
		
		int i = 0;
		for ( ArrayList<XmlClass> singleList : classes )
		{
			String className = singleList.get( 0 ).getName();
			LOG.info( "Gerando os <test> para a classe :" + className );
			for ( String deviceInfo : devices )
			{
				LOG.info( "Gerando um <test> para o dispositivo '" + deviceInfo + "' ..." );

				XmlTest test = new XmlTest(suite, i);
				test.setName( "Test-MobileCenter-" + className + "-" + deviceInfo );
				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put( "udid", deviceInfo );
				test.setParameters(parameters);
				if ( groups != null )
				{
					test.setGroups( groups );
				}
				test.setXmlClasses( singleList );
				
				i++;
			}
		}
		
		LOG.info( "Número de threads em paralelo na suíte: " + threadCount );
		suite.setThreadCount( threadCount );
		return suite;
	}
	
	/**Prepara uma suíte de testng para abastecimento de massa de teste.
	 * 
	 * @param testClassNames os nomes canônicos das classes de teste.
	 * @param groups os grupos a ser incluídos, ou null se nenhum.
	 * @param devices os udids dos dispositivos onde será feita a execução.
	 * @param threadCount a quantidade de threads simultâneas.
	 * @param dispositivosDeMassa os udids dos dispositivos que
	 * se quer abastecer de massa, cujos usuários serão usados para logar.
	 * @return suíte de Testng
	 */
	protected XmlSuite setUpMassa(
			List<String> testClassNames,
			XmlGroups groups,
			List<String> devices,
			int threadCount,
			List<String> dispositivosDeMassa )
	{
		if ( threadCount <= 0 )
		{
			String message = "É necessário definir o número de threads.";
			throw new AutomationException( message );
		}
		
		ArrayList<ArrayList<XmlClass>> classes = new ArrayList<ArrayList<XmlClass>>();
		for ( String className : testClassNames )
		{
			ArrayList<XmlClass> singleList = new ArrayList<XmlClass>();
			singleList.add( new XmlClass(className) );
			classes.add( singleList );
		}
		
		XmlSuite suite = new XmlSuite();
		suite.setName( "MobileCenterSuite" );
		suite.setParallel( ParallelMode.TESTS );
		suite.setConfigFailurePolicy( FailurePolicy.SKIP );
		
		suite.addListener( br.com.safra.automation.selenium.listeners.
				TestListener.class.getCanonicalName() );
		
		suite.addListener( br.com.safra.automation.selenium.listeners.
				SuiteListener.class.getCanonicalName() );
		
		int i = 0;
		for ( ArrayList<XmlClass> singleList : classes )
		{
			String className = singleList.get( 0 ).getName();
			LOG.info( "Gerando os <test> para a classe :" + className );
			for ( String deviceInfo : dispositivosDeMassa )
			{
				LOG.info( "Gerando um <test> para o dispositivo '" + deviceInfo + "' ..." );

				XmlTest test = new XmlTest(suite, i);
				test.setName( "Test-MobileCenter-" + className + "-" + deviceInfo );
				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put( "udid", devices.get( 0 ) );
				parameters.put( "dispositivoDeMassa", deviceInfo.toString() );
				test.setParameters(parameters);
				if ( groups != null )
				{
					test.setGroups( groups );
				}
				test.setXmlClasses( singleList );
				
				i++;
			}
		}
		
		LOG.info( "Número de threads em paralelo na suíte: " + threadCount );
		suite.setThreadCount( threadCount );
		return suite;
	}
}

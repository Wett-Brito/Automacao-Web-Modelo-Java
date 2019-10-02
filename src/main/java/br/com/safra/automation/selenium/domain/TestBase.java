package br.com.safra.automation.selenium.domain;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import br.com.safra.automation.selenium.config.DriverFactory;
import br.com.safra.automation.selenium.config.LocalDriverManager;
import br.com.safra.automation.selenium.config.properties.ExecutionProperties;
import br.com.safra.automation.storage.SQLConnectionManager;
import cucumber.api.testng.TestNGCucumberRunner;

public class TestBase
{
	private static final Logger LOG = LogManager.getLogger(TestBase.class);
	protected static final ExecutionProperties executionProperties = ExecutionProperties.getInstance();

	protected TestNGCucumberRunner testNGCucumberRunner;
	protected List<?> usuarios;
	
	WebDriver driver;
	
	String nomeTeste;
	
	protected ExtendsReport extendsReport;
	
	@BeforeClass( alwaysRun = true )
	public void setUpClass( XmlTest xmlTest) throws Exception
	{

		TestPlatform testEnvironment = executionProperties.getTestPlatform();
		
		switch( testEnvironment )
		{
		case CHROME:
			break;
		default:
			System.out.println("Erro na BeforeClass dentro da SetUpClass, não achou um ambiente valido na proprierts");
			break;
		}

		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}	
	
	@BeforeTest
	public void testSetUp() {
		ExtendsReport.InicioTeste();
	}

	@DataProvider
	public Object[][] features()
	{
		return testNGCucumberRunner.provideFeatures();
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception
	{
		ExtendsReport.FinalizarTeste();

		if ( testNGCucumberRunner != null )
		{
			testNGCucumberRunner.finish();
		}
	}

//	protected boolean verificaSeDeveRodar( String id ) throws SkipException
//	{
//		if ( executionProperties.isBancoDados() )
//		{
//			boolean deveRodar = false;
//			try {
//				deveRodar = SQLConnectionManager.deveRodar( id );
//				LOG.info( "Cenário " + id + " deve rodar? " + deveRodar );
//			}
//			catch ( Exception e )
//			{
//				String mensagem = "---- Erro ao ler o banco de dados ----";
//				LOG.error( mensagem, e );
//				throw new SkipException( mensagem );
//			}
//
//			if ( !deveRodar ) {
//				String mensagem = "---- Ja houve um teste bem sucedido para o ID '" + id + "' ----";
//				LOG.info( mensagem );
//				throw new SkipException( mensagem );
//			} else {
//				return true;
//			}
//		}
//		else
//		{
//			return true;
//		}
//	}
	
	@SuppressWarnings("unchecked")
	protected void instanciaDriver( String idCenario )
	{
		driver = null;
        try {
        	driver = new DriverFactory().createDriver( );
        	driver.manage().window().maximize();
		}
        catch (Exception e)
        {
			LOG.error(e);
			e.printStackTrace();
			throw new SkipException(e.getMessage());
		}
        
        if ( driver == null )
        {
        	String message = "Não foi possível iniciar o Driver ...";
        	LOG.error( message );
        	throw new SkipException( message );
        }
        
        LocalDriverManager.setDriver( driver );
	}
}

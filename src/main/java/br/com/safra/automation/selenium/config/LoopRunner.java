package br.com.safra.automation.selenium.config;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.safra.app.teste_geral.Massa;
import br.com.safra.automation.selenium.config.properties.ExecutionProperties;
import br.com.safra.automation.selenium.config.properties.LoopProperties;
import br.com.safra.automation.selenium.config.testclasses.GerenciadorDeClassesDeTesteParaRodar;
import br.com.safra.automation.selenium.domain.ExtendsReport;
import br.com.safra.automation.selenium.domain.TestPlatform;
import br.com.safra.automation.selenium.exceptions.ConfigurationException;
import br.com.safra.automation.selenium.web.utils.ArquivoUtil;

public class LoopRunner
{
	private static final Logger LOG = LogManager.getLogger(LoopRunner.class);
	protected static final ExecutionProperties executionProperties = ExecutionProperties.getInstance();
	protected static final GerenciadorDeClassesDeTesteParaRodar gerenciadorDeClassesDeTesteParaRodar = GerenciadorDeClassesDeTesteParaRodar.getInstance();
	public ArquivoUtil arquivoUtil = new ArquivoUtil();
	
	public void executar()
	{
		executar(null, null);
	}
	
	public void executar(
			TestPlatform testPlatform,
			List<String> classesParaExecutar)
	{
		aguardarDataHoraDeInicioDaExecucao();
		if ( testPlatform == null )
			testPlatform = executionProperties.getTestPlatform();

		classesParaExecutar = gerenciadorDeClassesDeTesteParaRodar.getList();
		
		String idTeste = executionProperties.getIdTeste();
		
		switch( testPlatform )
		{
		case CHROME:
			executarLocal( classesParaExecutar, idTeste );
			break;
		case FIREFOX:
			
		default:
			String mensagem = "Ainda não há uma implementação para a plataforma '"
					+ testPlatform + "' ...";
			LOG.error( mensagem );
			throw new ConfigurationException( mensagem );
		}
	}

	
	protected void executarLocal(
			List<String> classesParaExecutar,
			String idTeste)
	{
		arquivoUtil.criaResumo();
		boolean loop = true;
		int i = 0;
		while ( loop )
		{
			arquivoUtil.criarProximaExecucao();
			new TestNgSuiteRunner().execute(classesParaExecutar, idTeste );
			loop = new LoopProperties().isLoop();
			LOG.info( String.format( "LOOP=%s", loop ) );
		}
		
		ExtendsReport.FinalizaExecucao();
	}
	
	protected void aguardarDataHoraDeInicioDaExecucao()
	{
		while ( !LocalDateTime.now().isAfter( executionProperties.getDataHoraDoInicioDaExecucao() ) )
		{
			System.out.println( String.format(
					"Agora são '%s'. Aguardando início da execução às '%s' ...",
					LocalDateTime.now(),
					executionProperties.getDataHoraDoInicioDaExecucao() ) );
			try {
				Thread.sleep( 60_000 );
			}
			catch ( InterruptedException x )
			{
				LOG.error( x );
			}
		}
	}	
}

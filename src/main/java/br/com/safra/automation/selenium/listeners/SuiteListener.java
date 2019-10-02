package br.com.safra.automation.selenium.listeners;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import br.com.safra.automation.storage.SQLConnectionManager;

public class SuiteListener implements ISuiteListener
{
	private static final Logger LOG = LogManager.getLogger( SuiteListener.class );
	//private static final ExecutionProperties executionProperties = ExecutionProperties.getInstance();
	
	@Override
	public void onStart( ISuite suite ) {}
	
	@Override
	public void onFinish( ISuite suite )
	{
		LOG.info( "Finalizando a suíte de teste..." );

		try {
			SQLConnectionManager.closeConnection();
		}
		catch ( SQLException x )
		{
			LOG.error( "Erro ao fechar SQLConnection ...", x );
		}
	}
}

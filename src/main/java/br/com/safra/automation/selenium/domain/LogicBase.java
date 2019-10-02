package br.com.safra.automation.selenium.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.safra.automation.selenium.web.utils.UtilidadesDeDriver;


public abstract class LogicBase<T extends UtilidadesDeDriver>
{
	private static final Logger LOG = LogManager.getLogger(LogicBase.class);
	
	protected T utils;
	protected final String contexto;
	
	protected LogicBase( final Class<T> classeDeUtilidades, final String contexto )
	{
		try {
			this.utils = (T) classeDeUtilidades.newInstance();
		}
		catch ( InstantiationException | IllegalAccessException e )
		{
			LOG.error( String.format("Erro ao instanciar a classe '%s' ...", classeDeUtilidades.getCanonicalName()), e );
		}
		this.contexto = contexto;
	}
}

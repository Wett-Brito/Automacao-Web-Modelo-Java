package br.com.safra.automation.storage;

import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.safra.automation.selenium.exceptions.AutomationException;

public class LeitorDeArquivoJson
{
	private static final Logger LOG = LogManager.getLogger(LeitorDeArquivoJson.class);

	public JsonObject carregar( String caminhoDoArquivo )
	{
		JsonObject jsonObject;
		JsonParser parser = new JsonParser();
		try ( FileReader fileReader = new FileReader( caminhoDoArquivo ) )
		{
			jsonObject = (JsonObject) parser.parse( fileReader );
			return jsonObject;
		}
		catch ( Exception e )
		{
			String mensagem = "Falha ao carregar o arquivo JSON: " + caminhoDoArquivo;
			LOG.error( mensagem, e );
			throw new AutomationException( mensagem, e );
		}
	}
}

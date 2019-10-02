package br.com.safra.automation.selenium.config.properties;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.safra.automation.selenium.domain.OrderOfExecution;
import br.com.safra.automation.selenium.domain.TestPlatform;
import br.com.safra.automation.selenium.exceptions.ConfigurationException;
import br.com.safra.automation.selenium.exceptions.PropertyWithIncorrentValueException;
import br.com.safra.automation.storage.LeitorDeArquivoProperties;

public class ExecutionProperties extends GerenciadorDeProperties
{
	protected ExecutionProperties() {}
	
	private static final Logger LOG = LogManager.getLogger(ExecutionProperties.class);
	protected static ExecutionProperties gerenciador;
	
	protected String suiteName, customerName, testerName, platform, webDriverPath, implicityTimeOut;
	
	protected boolean isRemote, bancoDados;
	
	protected TestPlatform testPlatform;
	
	protected LocalDate dataDeAgendamento;
	
	protected LocalDateTime dataHoraDoInicioDaExecucao;
	
	protected OrderOfExecution orderOfExecution;
	
	protected int reattemptsPerTest, invocationCount;
	
	protected String idTeste;
	
	/**./config/execution.properties*/
	@Override
	protected void setDefaultFileLocation()
	{
		setCustomFileLocation( "./config/execution.properties" );
	}
	
	/**Obtém uma instância contendo todos os valores do arquivo de propriedades.*/
	public static synchronized ExecutionProperties getInstance()
	{
		return getInstance( null );
	}
	
	/**Obtém uma instância contendo todos os valores do arquivo de propriedades.*/
	public static synchronized ExecutionProperties getInstance( String customFileLocation )
	{
		if ( gerenciador == null )
		{
			gerenciador = new ExecutionProperties();
			gerenciador.setCustomFileLocation( customFileLocation );
			Properties properties = new LeitorDeArquivoProperties()
					.loadProperties( new File( gerenciador.getFileLocation() ).getAbsolutePath() );
			gerenciador.suiteName = properties.getProperty("suite.name");
			gerenciador.customerName = properties.getProperty("customer.name");
			gerenciador.testerName = properties.getProperty("tester.name");
			try {
				gerenciador.testPlatform = TestPlatform.valueOf(properties.getProperty("testPlatform"));
			}catch (IllegalArgumentException | NullPointerException e) {
				String mensagem = "O formato da propriedade 'dataHoraDoInicioDaExecucao'"
						+ " deve ser 'aaaa-MM-dd HH:mm:ss' ...";
				LOG.error( mensagem, e );
			}

			gerenciador.implicityTimeOut = properties.getProperty("implicityTimeout");
			gerenciador.webDriverPath = properties.getProperty("web.driver.path");
			try {
				gerenciador.dataHoraDoInicioDaExecucao = LocalDateTime.parse(
						properties.getProperty("dataHoraDoInicioDaExecucao"),
						DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss") );
			}
			catch ( DateTimeParseException x )
			{
				String mensagem = "O formato da propriedade 'dataHoraDoInicioDaExecucao'"
						+ " deve ser 'aaaa-MM-dd HH:mm:ss' ...";
				LOG.error( mensagem, x );
				throw new ConfigurationException( mensagem, x );
			}
			try {
				gerenciador.orderOfExecution = OrderOfExecution.valueOf(
						properties.getProperty("orderOfExecution").toUpperCase());
			}
			catch ( IllegalArgumentException | NullPointerException e )
			{
				LOG.warn("Não foi possível ler a propriedade 'orderOfExecution' em '"
						+ gerenciador.getFileLocation()
						+ "'. Os valores permitidos são: " + OrderOfExecution.valoresPermitidos()
						+ ". Por padrão, o teste prosseguirá com a ordem por LINHA ...", e);
				gerenciador.orderOfExecution = OrderOfExecution.LINHA;
			}
			gerenciador.idTeste = properties.getProperty("IdTest");
		}
		return gerenciador;
	}

	public String getWebDriverPath() {
		if (webDriverPath == null) {
			String message = "A propriedade web.driver.path precisa receber o caminho para o executável do driver";
			LOG.error(message);
			throw new PropertyWithIncorrentValueException(message);
		}
		return this.webDriverPath;
	}

	public String getSuiteName() {
		return suiteName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getTesterName() {
		return testerName;
	}

	public String getPlatform() {
		return platform;
	}

	public String getImplicityTimeOut() {
		return implicityTimeOut;
	}

	public boolean isRemote() {
		return isRemote;
	}

	public boolean isBancoDados() {
		return bancoDados;
	}

	public TestPlatform getTestPlatform() {
		return testPlatform;
	}

	public LocalDate getDataDeAgendamento() {
		return dataDeAgendamento;
	}

	public LocalDateTime getDataHoraDoInicioDaExecucao() {
		return dataHoraDoInicioDaExecucao;
	}

	public OrderOfExecution getOrderOfExecution() {
		return orderOfExecution;
	}

	public int getReattemptsPerTest() {
		return reattemptsPerTest;
	}

	public int getInvocationCount() {
		return invocationCount;
	}
	
	public String getIdTeste() {
		return idTeste;
	}
}

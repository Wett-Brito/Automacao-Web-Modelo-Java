package br.com.safra.automation.selenium.listeners;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import br.com.safra.app.teste_geral.Massa;
import br.com.safra.automation.selenium.config.LocalDriverManager;
import br.com.safra.automation.selenium.config.properties.ProjectProperties;
import br.com.safra.automation.selenium.domain.ExtendsReport;
import br.com.safra.automation.selenium.web.utils.ArquivoUtil;
import br.com.safra.automation.selenium.web.utils.ScreenshotUtil;

public class TestListener implements ITestListener {
	private static final Logger LOG = LogManager.getLogger(TestListener.class);
	//private static final ExecutionProperties executionProperties = ExecutionProperties.getInstance();
	private ScreenshotUtil screenshotUtil = new ScreenshotUtil();
	private ArquivoUtil arquivoUtil = new ArquivoUtil();
	private Contador contador = new Contador();

	@Override
	public void onTestStart(ITestResult result) {
//		arquivoUtil.criaResumo("cenario", "iniciado");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
		arquivoUtil.logTotal(contador.getPassed(), contador.getFailed(), contador.getSkipped());
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		ChromeDriver driver = ( ChromeDriver ) LocalDriverManager.getDriver();
		screenshotUtil.printFinal(Massa.getId(), "Erro final", driver);
//		LOG.info(Massa.NOME_TESTE.getText() + " falhou ...");
//		finalizaTesteFeito(false);
		ExtendsReport.TestLogFailed();
		arquivoUtil.logResumo("Falha");
		contador.setFailed(contador.getFailed() +1);
		FecharDriver();
	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
//		LOG.info(Massa.NOME_TESTE.getText() + " foi pulado ...");
//		finalizaTesteFeito(false);
		ExtendsReport.TestLogSkiped();
		arquivoUtil.logResumo("Pulado");
		contador.setSkipped(contador.getSkipped() +1);
		FecharDriver();
	}

	@Override
	public void onTestSuccess(ITestResult testResult) {
//		LOG.info(Massa.NOME_TESTE.getText() + " teve sucesso ...");
		arquivoUtil.moverArquivoPassed();
//		moveCapturaDeTelaDeCenarioReprocessadoComSucesso(idCompleto + "_" + nomeCenario);
		arquivoUtil.logResumo("Sucesso");
		contador.setPassed(contador.getPassed() +1);
		ExtendsReport.TestLogPassed();
		FecharDriver();	
	}

//	private void finalizaTesteFeito(boolean status) {
//		if (LocalDriverManager.getDriver() != null) {
//
//			capturaTela(Massa.getValor("ID"), status);
//
//			if (executionProperties.isBancoDados()) {
//
//				enviaResultadoAoBancoDeDados(Massa.getValor("ID"), status);
//			}
//		}
//	}

	public boolean createFile(File screenshot) {
		boolean fileCreated = false;

		if (screenshot.exists()) {
			fileCreated = true;
		} else {
			File parentDirectory = new File(screenshot.getParent());
			if (parentDirectory.exists() || parentDirectory.mkdirs()) {
				try {
					fileCreated = screenshot.createNewFile();
				} catch (IOException errorCreatingScreenshot) {
					LOG.error("Erro de criação de captura de tela.", errorCreatingScreenshot);
				}
			}
		}

		return fileCreated;
	}

	private void writeScreenshotToFile(WebDriver driver, File screenshot) {
		try {
			FileOutputStream screenshotStream = new FileOutputStream(screenshot);
			screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
			screenshotStream.close();
		} catch (IOException unableToWriteScreenshot) {
			LOG.error("Não foi possível salvar a captura de tela " + screenshot.getAbsolutePath(),
					unableToWriteScreenshot);
		}
	}
	WebDriver driver = LocalDriverManager.getDriver();
	private void saveScreenshot(String testName) {
		try {
			WebDriver driver = LocalDriverManager.getDriver();
			if (driver != null) {
				String screenshotDirectory = ProjectProperties.getInstance().getPastaEvidencias();
				String screenshotAbsolutePath = screenshotDirectory + File.separator + testName + ".png";
				File screenshot = new File(screenshotAbsolutePath);
				if (createFile(screenshot)) {
					try {
						writeScreenshotToFile(driver, screenshot);
					} catch (ClassCastException weNeedToAugmentOurDriverObject) {
						writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
					}
				} else {
					LOG.error("Não foi possível criar " + screenshotAbsolutePath);
				}
			} else {
				LOG.error("Não foi possível fazer captura de tela porque o driver está null...");
			}

		} catch (Exception ex) {
			LOG.error("Não foi possível fazer captura de tela...", ex);
		}
	}

	public void capturaTela(String nome, boolean status) {
		LocalDateTime tempo = LocalDateTime.now();
		String data = tempo.format(DateTimeFormatter.ofPattern("uuuu_MM_dd"));
		String hora = tempo.format(DateTimeFormatter.ofPattern("HHmmss"));
		saveScreenshot(data + File.separator + (status ? "success" : "failure") + File.separator + nome + File.separator
				+ hora);
	}
	

//	private void moveCapturaDeTelaDeCenarioReprocessadoComSucesso(String idNome) {
//		LocalDateTime tempo = LocalDateTime.now();
//		String data = tempo.format(DateTimeFormatter.ofPattern("uuuu_MM_dd"));
//		String pastaEvidencias = ProjectProperties.getInstance().getPastaEvidencias();
//		File origem = new File(
//				pastaEvidencias + File.separator + data + File.separator + "failure" + File.separator + idNome);
//		File destino = new File(
//				pastaEvidencias + File.separator + data + File.separator + "reprocessed" + File.separator + idNome);
//		if (origem.exists()) {
//			try {
//				FileUtils.copyDirectory(origem, destino);
//			} catch (IOException e) {
//				LOG.error(e);
//			}
//			arquivoUtil.deleta(origem);
//		}
//	}

	public void filtrarScreenshotsDeFalhaAPartirDosSucessos(String nomeSubpastaEvidencias) {
		String pastaEvidencias = ProjectProperties.getInstance().getPastaEvidencias();
		File pastaSuccess = new File(
				pastaEvidencias + File.separator + nomeSubpastaEvidencias + File.separator + "success");
		File[] listaDeSubpastasDeSuccess = pastaSuccess.listFiles();
		for (File subpasta : listaDeSubpastasDeSuccess) {
			String nomeDaSubpasta = subpasta.getName();
			System.out.println(nomeDaSubpasta);
			File origem = new File(pastaEvidencias + File.separator + nomeSubpastaEvidencias + File.separator
					+ "failure" + File.separator + nomeDaSubpasta);
			File destino = new File(pastaEvidencias + File.separator + nomeSubpastaEvidencias + File.separator
					+ "reprocessed" + File.separator + nomeDaSubpasta);
			if (origem.exists()) {
				try {
					FileUtils.copyDirectory(origem, destino);
				} catch (IOException e) {
					LOG.error(e);
				}
				arquivoUtil.deleta(origem);
			}
		}
	}

//	private void deleta(File file) {
//		if (file.isDirectory()) {
//			File[] files = file.listFiles();
//			for (int i = 0; i < files.length; i++) {
//				deleta(files[i]);
//			}
//		}
//		file.delete();
//	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	private void enviaResultadoAoBancoDeDados(String idCompleto, boolean status) {
//		LocalDateTime tempo = LocalDateTime.now();
//		String dataBD = tempo.format(DateTimeFormatter.ofPattern("uuuu/MM/dd"));
//		String horaBD = tempo.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//		try {
//			SQLConnectionManager.salvaStatusExecucaoTabelaFuncionalidade(idCompleto, status, dataBD, horaBD);
//			LOG.info("Resultado salvo no banco de dados: " + idCompleto + " | " + (status ? "passed" : "failed"));
//		} catch (SQLException x) {
//			LOG.error("Erro ao enviar resultado da execução ao banco de dados ...", x);
//		}
//	}

//	private String idCompleto(ITestResult testResult) {
//		String id = testResult.getMethod().getDescription();
//		if (id == null || id.isEmpty()) {
//			id = "00000";
//		}
//
//		return id;
//	}
//
//	private String nomeDoCenario(ITestResult testResult) {
//		String simpleName = testResult.getTestClass().getRealClass().getSimpleName();
//		simpleName = simpleName.replaceAll("^MPF_\\d+_", "").replaceAll("^Teste", "");
//
//		return simpleName;
//	}
	
	private void FecharDriver(){
		ChromeDriver driver = ( ChromeDriver ) LocalDriverManager.getDriver();
		driver.close();
	}

}

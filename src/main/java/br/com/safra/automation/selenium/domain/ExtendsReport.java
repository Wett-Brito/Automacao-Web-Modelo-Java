package br.com.safra.automation.selenium.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class ExtendsReport {
	
	public static ExtentTest logger;
	public static ExtentReports extent;


	public static void InicioTeste() {
			
		extent = new ExtentReports(".\\report\\Report-Safra-" + dataDoNome() +".html");
		extent.addSystemInfo("Host Name", "Note_Wett").addSystemInfo("Environment", "Safra_CRL_WEB");
		
	}
	
	public static void Teste(String nomeTeste) {
		logger = extent.startTest(nomeTeste);
	}
	
	public static void FinalizarTeste() {
		extent.endTest(logger);

	}
	
	public static void FinalizaExecucao() {
		extent.flush();
	}
	
	public static void TestLogPassed() {
		logger.log(LogStatus.PASS, "Esse teste Passou");

	}
	
	public static void TestLogFailed() {
		logger.log(LogStatus.FAIL, "Esse teste Não Passou");
	}
	
	public static void TestLogSkiped() {
		logger.log(LogStatus.SKIP, "Esse teste foi pulado");
	}
	
	public static String dataDoNome(){
		LocalDateTime data = LocalDateTime.now();
		
		
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd-MM-uuuu");
		String dataFormatada = formatterData.format(data);

		DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH_mm_ss");
		String horaFormatada = formatterHora.format(data);

		
		return dataFormatada + " " + horaFormatada;
	}
	
}
package br.com.safra.automation.selenium.web.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.safra.automation.selenium.config.LocalDriverManager;
import br.com.safra.automation.selenium.config.properties.ProjectProperties;

public class ScreenshotUtil {

	ArquivoUtil arquivoUtil = new ArquivoUtil();	
	ChromeDriver driver = ( ChromeDriver ) LocalDriverManager.getDriver();

	public void printFinal(String nomeTeste, String etapa, ChromeDriver driver) {
		this.driver = driver;
		capturaTela(nomeTeste, etapa);
	}
	
	public void tiraScreenshot(File caminho) {
		try {
			FileOutputStream screenshotStream = new FileOutputStream(caminho);
			screenshotStream.write(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
			screenshotStream.close();
		} catch (IOException unableToWriteScreenshot) {
			System.out.printf("Não foi possível salvar a captura de tela " + caminho.getAbsolutePath(),
					unableToWriteScreenshot);
			unableToWriteScreenshot.printStackTrace();
		}
	
	}
	
	public void SalvaScreenshot(String testName) {
		try {
			if (driver != null) {
				String screenshotAbsolutePath = testName + ".png";
				File caminho = new File(screenshotAbsolutePath);
				arquivoUtil.criarPasta(caminho.getParent());
				
				tiraScreenshot(caminho);
			}
		} catch(Exception ex) {
			System.out.printf("Não foi possível fazer captura de tela...", ex);
		}

	}
	
	public void capturaTela(String nomeTeste, String etapa) {
		String screenshotDirectory = ProjectProperties.getInstance().getPastaEvidencias();
		String caminhoBase = (screenshotDirectory + File.separator + pegarUltimoArquivoData(screenshotDirectory) + File.separator );
		String CaminhoIntermediario = (pegarUltimoArquivoData(screenshotDirectory) + File.separator + pegarNomeDaExecucao(caminhoBase) + 
				  File.separator +
				  "2 - Falha" + 
				  File.separator +
				  nomeTeste + 
				  File.separator);
				
		SalvaScreenshot(screenshotDirectory +  File.separator + CaminhoIntermediario + "Passo " + pegarProximoNumeroPasso(screenshotDirectory +  File.separator + CaminhoIntermediario) + " - " +  etapa );
	}
	
	public String  pegarUltimoArquivoExecucao(String caminhoBase) {
		File src = new File(caminhoBase);
		File[] files = src.listFiles();		
		return files[files.length - 1].getName();
	}
	
	
	public String  pegarUltimoArquivoData(String screenshotDirectory ) {
		File src = new File(screenshotDirectory + File.separator);
		File[] files = src.listFiles();		
		return files[files.length - 1].getName();
	}
	
	public String pegarProximoNumeroPasso(String CaminhoIntermediario) {
		File src = new File(CaminhoIntermediario);
		int maiorNumero = 0;
		try {
			File[] files = src.listFiles();

			for (File f : files) {
				String Passo = f.getName().replaceAll("\\D", "");
				
				if (Integer.parseInt(Passo) > maiorNumero) {
					maiorNumero = Integer.parseInt(Passo);
				}
			}
		} catch(NullPointerException e){}

		return String.valueOf(maiorNumero + 1);
	}

	public String  pegarNomeDaExecucao(String caminhoBase) {
		File src = new File(caminhoBase);
		File[] files = src.listFiles();
		int maiorNumero = 0;
		String NomeDoArquivoFinal = "";
		String numeroBase = "";
		String NomeDoArquivo = "";
		
		for (File f : files) {
			
			try {


				for (int i=0; i<f.getName().length(); i++)
				{
					
					if (f.getName().charAt(i) == '-') //Encontrou o primeiro "-"
					{
						numeroBase = f.getName().substring(0, i-1); //Define o length da string como o index do "-", menos 1
						NomeDoArquivo = f.getName();
						break;
					}
				}
				
				if (Integer.parseInt(numeroBase) > maiorNumero && !numeroBase.equals("")) {
					maiorNumero = Integer.parseInt(numeroBase);
					NomeDoArquivoFinal = NomeDoArquivo;
				}
			} catch(NumberFormatException e) {}
		}
		
		return NomeDoArquivoFinal;

	}	
	
}

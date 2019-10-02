package br.com.safra.automation.selenium.web.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.safra.app.teste_geral.Massa;
import br.com.safra.automation.selenium.config.properties.ProjectProperties;

public class ArquivoUtil {
	public String dataInicio;
	public String horaInicio;

	public ArquivoUtil() {
		pegarData();
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public boolean criaAPasta(File caminho) {
		boolean fileCreated = false;

		if (caminho.exists()) {
			fileCreated = true;
		} else {
			fileCreated = caminho.mkdirs();
		}
		return fileCreated;
	}

	public void criarPasta(String pasta) {
		File arquivo = new File(pasta);
		if (arquivo.mkdirs()) {
		}
	}

	public void criarProximaExecucao() {
		ScreenshotUtil screenshotUtil = new ScreenshotUtil();
		String screenshotDirectory = ProjectProperties.getInstance().getPastaEvidencias();
		pegarData();
		pegarHora();
		String caminho = screenshotDirectory + File.separator + getDataInicio() + File.separator;
		excluiArquivoVazio(caminho + screenshotUtil.pegarNomeDaExecucao(caminho));

		int ProximoNumeroExecucao = Integer.parseInt(
				pegarNumeroDaExecucao(screenshotDirectory + File.separator + getDataInicio() + File.separator)) + 1;
		
		criarPasta(caminho + ProximoNumeroExecucao + " - Exec - " + getHoraInicio());
		
	}

	public void pegarData() {
		LocalDateTime tempo = LocalDateTime.now();
		setDataInicio(tempo.format(DateTimeFormatter.ofPattern("uuuu_MM_dd")));
	}

	public void pegarHora() {
		LocalDateTime tempo = LocalDateTime.now();
		setHoraInicio(tempo.format(DateTimeFormatter.ofPattern("HHmm")));
	}

	public String pegarNumeroDaExecucao(String caminhoBase) {
		File src = new File(caminhoBase);
		// evitar nullpointerexception
		criarPasta(caminhoBase);
		File[] files = src.listFiles();
		int maiorNumero = 0;

		for (File f : files) {

			try {
				String numeroBase = "";
				for (int i = 0; i < f.getName().length(); i++) {

					if (f.getName().charAt(i) == '-') // Encontrou o primeiro "-"
					{
						numeroBase = f.getName().substring(0, i - 1); // Define o length da string como o index do "-",
																		// menos 1
						break;
					}
				}

				if (Integer.parseInt(numeroBase) > maiorNumero && !numeroBase.equals("")) {
					maiorNumero = Integer.parseInt(numeroBase);
				}
			} catch (NumberFormatException e) {
			}
		}

		return String.valueOf(maiorNumero);

	}

	public void moverArquivoPassed() {
		ScreenshotUtil screenshotUtil = new ScreenshotUtil();

		String screenshotDirectory = ProjectProperties.getInstance().getPastaEvidencias();

		String caminhoBase = (screenshotDirectory + File.separator
				+ screenshotUtil.pegarUltimoArquivoData(screenshotDirectory) + File.separator);

		String CaminhoOrigem = (screenshotDirectory + File.separator
				+ screenshotUtil.pegarUltimoArquivoData(screenshotDirectory) + File.separator
				+ screenshotUtil.pegarNomeDaExecucao(caminhoBase) + File.separator + "2 - Falha" + File.separator
				+ Massa.getId());

		File fileCaminhoOrigem = new File(CaminhoOrigem);

		File listOrigem[] = fileCaminhoOrigem.listFiles();

		String caminhoDestino = (screenshotDirectory + File.separator
				+ screenshotUtil.pegarUltimoArquivoData(screenshotDirectory) + File.separator
				+ screenshotUtil.pegarNomeDaExecucao(caminhoBase) + File.separator + "1 - Sucesso" + File.separator
				+ Massa.getId());

		for (File f : listOrigem) {
			File filecaminhoDestino = new File(caminhoDestino + File.separator + (f.getName()));

			if (new File((filecaminhoDestino).getParent()).mkdirs())
				System.out.println("Criou Arquivo");
			
			else
				System.out.println("Não criou o arquivo");

			if (f.renameTo(filecaminhoDestino))
				System.out.println("Sucesso");
			
			else
				System.out.println("Errou");

		}
		
		deleta(fileCaminhoOrigem);

	}

	public void excluiArquivoVazio(String arquivo) {
		if(diretorioEstaVazio(arquivo)) {
			deleta(new File(arquivo));
			
		}
	}

//	public void excluiArquivo(String arquivo) {
//		File fileArquivo = new File(arquivo);
//		
//		if (fileArquivo.delete()) {
//			System.out.println("Deletou arquivo");
//		}
//	}
	
	
	
	//Esse método estava em "TestListener"
	
	public void deleta(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleta(files[i]);
			}
		}
		file.delete();
	}

	public boolean diretorioEstaVazio(String diretorio) {
		File dir = new File(diretorio);
		String[] filho = dir.list();
		
		if (filho ==  null || filho.length == 0) {
			return true;
			
		} else if(filho.length > 0) {
			return false;
			
		} else {
			return false;
			
		}
	}
	
	public String pegarUltimaExecucao(String caminhoBase) {
		File src = new File(caminhoBase);
		File[] files = src.listFiles();
		File teste = null;
		try {
		teste = files[files.length - 2];
		}catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
		
		return teste.getName();
	}
	
	public void criaResumo() {
		String screenshotDirectory = ProjectProperties.getInstance().getPastaEvidencias();
		pegarData();
		String caminho = screenshotDirectory + File.separator + getDataInicio() + File.separator + "Resumo" + File.separator + "Resumo Teste Automatizado " + getDataInicio() + ".txt";
		File fileCaminho = new File(caminho);
		try {
			//Criando arquivo, se necessário
			if(!fileCaminho.exists())
			{
				new File(fileCaminho.getParent()).mkdirs();
				fileCaminho.createNewFile();	
				logLine ("Iniciado A execução - " + getDataInicio());
				logLine ("Execução;Teste;Cenário;resultado");
			}

			
		}catch(IOException e) {
			System.out.println("Não foi possivel criar ou escrever no arquivo de resumo..." + e.getMessage());
		}
	}
	
	public void logResumo (String status)
	{
		String screenshotDirectory = ProjectProperties.getInstance().getPastaEvidencias();
		String resultLine = "";
			resultLine = "Exec" + pegarNumeroDaExecucao(screenshotDirectory + File.separator + getDataInicio()) + ";" + Massa.getId() + ";" + status;
		logLine (resultLine);
	}
	
	public void logTotal (int passed, int failed, int skipped)
	{
		logLine ("Total: " + (passed + failed + skipped) + " Passed: " + passed + " Failed: " + failed + " Skipped: " + skipped);
	}
	
	public void logLine (String line)
	{
		String screenshotDirectory = ProjectProperties.getInstance().getPastaEvidencias();
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(screenshotDirectory + File.separator + getDataInicio() + File.separator + "Resumo" + File.separator + "Resumo Teste Automatizado " + getDataInicio() + ".txt", true));
			bw.write(line);
			bw.newLine();
			bw.close();
		}
		catch (Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}
	}
}

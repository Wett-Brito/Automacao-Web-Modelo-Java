package br.com.safra.automation.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.safra.automation.selenium.config.properties.ExecutionProperties;

public final class RelatorioUtils {
	
	private static final Logger LOG = LogManager.getLogger(RelatorioUtils.class);
	private static final ExecutionProperties executionProperties = ExecutionProperties.getInstance();
	
	private RelatorioUtils() {}

	/**Busca na planilha de relatorio por ID e por Segmento. Verifica se já
	 * houve um teste, e se o teste foi ou não bem-sucedido.
	 * 
	 * @param id Identificador único de cada cenário, por exemplo, IBPJ_0001.
	 * @param segmento Um dos segmentos: E1, E1_DIGITAL, E2, E3, GCB, CORPORATE.
	 * @return 0 - ID/seg foi encontrado e ainda não teve sucesso;
	 * <p>1 - ID/seg foi encontrado mas já teve sucesso;</p>
	 * <p>2 - ID/seg não foi encontrado.</p>
	 */
	public static synchronized int seraQueJaDeuCerto(String id) {
		final int colunaID = 0;
		int colunaStatus = 5;
		boolean foiEncontrado = false;
		boolean estaOk = false;
		int resultadoPesquisa;
		XSSFWorkbook workbook = null;
		XSSFSheet planilha = null;
		String caminhoDoArquivo = "./relatorio/"
				+ "relatorio_"
				+ executionProperties.getSuiteName()
				+ ".xlsx";
		FileInputStream fi = null;
		try {
			fi = new FileInputStream(new File(caminhoDoArquivo));
			workbook = new XSSFWorkbook(fi);
		} catch (IOException e) {
			LOG.error( e );
		}
	
		planilha = workbook.getSheetAt(0);
		
		search:
			for (Row row : planilha) {
				Cell cellFn = row.getCell(colunaID);
				Cell cellSeg = row.getCell(colunaStatus);
				if (cellFn == null) {
					break search;
				}
				if (cellFn.toString().trim().equals(id)) {
					foiEncontrado = true;
					if (cellSeg.toString().trim().equals("ok")) {
						estaOk = true;
					}
					break search;
				}
			}
		
		try {
			fi.close();
		} catch (IOException e) {
			LOG.error( e );
		}
		
		if (estaOk) { // ID foi encontrado mas ja esta ok.
			resultadoPesquisa = 1;
		} else {
			if (foiEncontrado) { // ID foi encontrado e nao esta ok.
				resultadoPesquisa = 0;
			} else { // ID nao foi encontrado.
				resultadoPesquisa = 2;
			}
		}
		
		return resultadoPesquisa;
	}

	/**Encontra a primeira linha onde houver o ID. Idealmente, há um ID
	 * único para cada linha.
	 * 
	 * @param planilha Onde fazer a busca
	 * @param id Identificador que serve para buscar cada linha
	 * @return o índice da linha encontrada
	 */
	private static synchronized int localizarLinhaPorID(XSSFSheet planilha, String id) {
		int rowNumber = -1;
	
		search:
		for (Row row : planilha) {
			Cell cell = row.getCell(0);
			if (cell.toString().trim().toUpperCase().equals(id)) {
				rowNumber = row.getRowNum();
				break search;
			}
		}
	
		return rowNumber;
	}

	/**Escreve na planilha de relatorio qual foi o resultado de um cenário testado.
	 * 
	 * @param id Identificador único do cenário.
	 * @param segmento Em qual segmento o cenário foi testado.
	 * @param passou Se o teste passou ou não.
	 */
	public static synchronized void escreverResultado(String id, boolean passou) {
		String fileName = "./relatorio/"
				+ "relatorio_"
				+ executionProperties.getSuiteName()
				+ ".xlsx";
	
		XSSFSheet planilha = null;
		XSSFWorkbook workbook = null;
		FileOutputStream os = null;
		
		try {
			workbook = new XSSFWorkbook(new FileInputStream(new File(fileName)));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	
		planilha = workbook.getSheetAt(0);
		
		int linha = localizarLinhaPorID(planilha, id);
		int colunaStatus = 5;
		planilha.getRow(linha).getCell(colunaStatus).setCellValue(passou ? "ok" : "nok");
		
		try {
			os = new FileOutputStream(new File(fileName));
			workbook.write(os);
			os.flush();
			os.close();
			planilha = null;
			workbook = null;
		} catch (IOException e) {
			LOG.error( e );
		}
	}

}

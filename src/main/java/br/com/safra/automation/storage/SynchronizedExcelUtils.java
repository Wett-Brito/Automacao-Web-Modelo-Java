package br.com.safra.automation.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.format.CellNumberFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class SynchronizedExcelUtils
{
	protected static String fileName;
	protected static XSSFWorkbook wb;
	
	protected static synchronized void carregar(String nomeDoArquivo) {
		fileName = nomeDoArquivo;
		abrePastaDeTrabalho();
	}

	/**
	 * Método que retorna o nome do arquivo
	 * 
	 * @return String com o caminho do arquivo aberto
	 */
	protected static synchronized String getFileName() {
		return fileName;
	}

	/**
	 * Método que retorna a planilha aberta
	 * 
	 * @return atributo do tipo XSSFWorkbook {@link XSSFWorkbook}
	 */
	protected static synchronized XSSFWorkbook getWorkBook() {
		return wb;
	}

	/**
	 * Método que abre a planilha
	 */
	protected static synchronized void abrePastaDeTrabalho() {
		try {
			wb = new XSSFWorkbook(new FileInputStream(new File(getFileName())));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Método que grava as alterações efetuadas na planilha
	 */
	protected static synchronized void salvaPastaDeTrabalho() {
		FileOutputStream arquivoSaida = null;
		try {
			arquivoSaida = new FileOutputStream(new File(getFileName()));
			getWorkBook().write(arquivoSaida);
			arquivoSaida.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		wb = null;
	}

	/**
	 * Método que pega o valor de uma célula em texto
	 * 
	 * @param linha
	 *            inteiro que representa a linha da célula desejada, iniciando
	 *            em 0
	 * @param coluna
	 *            inteiro que representa a coluna da célula desejada, iniciando
	 *            em 0
	 * @return String com o texto contido na célula encontrada
	 */
	protected static synchronized String getTextoCelula(XSSFSheet planilha, int linha, int coluna) {
		String textoCelula;
		try {
			textoCelula = planilha.getRow(linha).getCell(coluna).getStringCellValue();
		} catch (Exception ex) {
			textoCelula = Integer
					.toString((int) planilha.getRow(linha).getCell(coluna).getNumericCellValue());
		}
		return textoCelula;
	}
	
	protected static synchronized String getTextoCelula( Cell cell )
	{
		String textoCelula;
		try {
			textoCelula = cell.getStringCellValue();
		}
		catch ( Exception ex )
		{
			textoCelula = Integer.toString( (int) cell.getNumericCellValue() );
		}
		return textoCelula;
	}

	/**
	 * Método que pega o texto absoluto de uma célula
	 * 
	 * @param linha
	 *            inteiro que representa a linha da célula desejada, iniciando
	 *            em 0
	 * @param coluna
	 *            inteiro que representa a coluna da célula desejada, iniciando
	 *            em 0
	 * @return String com o texto absoluto contido na célula encontrada
	 */
	protected static synchronized String getTextoSimplesCelula(XSSFSheet planilha, int linha, int coluna) {
		String textoCelula;

		textoCelula = planilha.getRow(linha).getCell(coluna).getStringCellValue();

		String stringFormat = planilha.getRow(linha).getCell(coluna).getCellStyle().getDataFormatString();
		CellNumberFormatter fmt = new CellNumberFormatter(stringFormat);
		textoCelula = fmt.format(planilha.getRow(linha).getCell(coluna).getNumericCellValue());

		return textoCelula;
	}

	/**
	 * Método que pega o valor da célula em Double
	 * 
	 * @param linha
	 *            inteiro que representa a linha da célula desejada, iniciando
	 *            em 0
	 * @param coluna
	 *            inteiro que representa a coluna da célula desejada, iniciando
	 *            em 0
	 * @return Double com o valor contido na célula encontrada
	 */
	protected static synchronized double getValorCelulaDouble(XSSFSheet planilha, int linha, int coluna) {
		double valorCelula;
		try {
			valorCelula = planilha.getRow(linha).getCell(coluna).getNumericCellValue();
		} catch (Exception ex) {
			valorCelula = Double.parseDouble(planilha.getRow(linha).getCell(coluna).getStringCellValue());
		}

		return valorCelula;

	}

	/**
	 * Método que pega o valor da célula em int
	 * 
	 * @param linha
	 *            inteiro que representa a linha da célula desejada, iniciando
	 *            em 0
	 * @param coluna
	 *            inteiro que representa a coluna da célula desejada, iniciando
	 *            em 0
	 * @return inteiro com o valor contido na célula encontrada
	 */
	protected static synchronized int getValorCelulaInt(XSSFSheet planilha, int linha, int coluna) {
		int valorCelula;
		try {
			valorCelula = (int) planilha.getRow(linha).getCell(coluna).getNumericCellValue();
		} catch (Exception ex) {
			valorCelula = Integer.parseInt(planilha.getRow(linha).getCell(coluna).getStringCellValue());
		}

		return valorCelula;
	}

	/**
	 * Método que insere o valor em texto em uma célula
	 * 
	 * @param linha
	 *            inteiro que representa a linha da célula desejada, iniciando
	 *            em 0
	 * @param coluna
	 *            inteiro que representa a coluna da célula desejada, iniciando
	 *            em 0
	 * @param value
	 *            String com o texto a ser inserido na célula
	 */
	protected static synchronized void setTextoCelula(XSSFSheet planilha, int linha, int coluna, String value) {
		planilha.getRow(linha).getCell(coluna).setCellValue(value);
	}

	/**
	 * Método que insere o valor em Double em uma célula
	 * 
	 * @param linha
	 *            inteiro que representa a linha da célula desejada, iniciando
	 *            em 0
	 * @param coluna
	 *            inteiro que representa a coluna da célula desejada, iniciando
	 *            em 0
	 * @param value
	 *            Double com o valor a ser inserido na célula
	 */
	protected static synchronized void setValorCelulaDouble(XSSFSheet planilha, int linha, int coluna, double value) {
		planilha.getRow(linha).getCell(coluna).setCellValue(value);
	}

	/**
	 * Método que insere o valor em int em uma célula
	 * 
	 * @param linha
	 *            inteiro que representa a linha da célula desejada, iniciando
	 *            em 0
	 * @param coluna
	 *            inteiro que representa a coluna da célula desejada, iniciando
	 *            em 0
	 * @param value
	 *            int ocm o valor a ser inserido na célula
	 */
	protected static synchronized void setValorCelulaInt(XSSFSheet planilha, int linha, int coluna, int value) {
		planilha.getRow(linha).getCell(coluna).setCellValue(value);
	}

	/**
	 * Método que retorna lista com os itens de uma coluna
	 * 
	 * @param coluna
	 *            índice da coluna desejada
	 * @return List com os valores encontrados na coluna
	 */
	protected static synchronized List<String> itensColuna(Integer coluna) {
		List<String> valores = new ArrayList<String>();
		for (Row r : getWorkBook().getSheetAt(0)) {
			Cell c = r.getCell(coluna);
			if (c != null) {
				valores.add(c.getStringCellValue());
			}
		}

		return valores;
	}

	/**
	 * Método que procura um texto em uma planilha, e retorna o índice da linha
	 * onde o texto foi encontrado
	 * 
	 * @param textoCelula
	 *            valor a ser encontrado em uma célula
	 * @return número da linha onde o texto foi encontrado. Caso o texto não
	 *         tenha sido encontrado, retorna 1
	 */
	protected static synchronized int achaLinhaPorTexto(String textoCelula) {
		for (Row linha : getWorkBook().getSheetAt(0)) {
			for (Cell celula : linha) {
				if (celula.getCellType() == Cell.CELL_TYPE_STRING) {
					if (celula.getRichStringCellValue().getString().trim().equals(textoCelula)) {
						return linha.getRowNum();
					}
				}
			}
		}
		return -1;
	}
}

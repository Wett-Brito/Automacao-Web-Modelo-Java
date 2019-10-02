package br.com.safra.automation.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.format.CellNumberFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Clase que encapsula as operações com Excel
 * 
 * @author Denis Luna Borges da Silva
 *
 */
public class ExcelUtils {
	
	private static final Logger LOG = LogManager.getLogger(ExcelUtils.class);
	
	private String fileName;
	private XSSFWorkbook wb;
	private XSSFSheet planilha;
	
	/**
	 * Construtor vazio da classe, permitindo que se escolha o arquivo a ser
	 * aberto através dos métodos setNomePlanilha
	 */
	public ExcelUtils() {
	}

	/**
	 * Construtor com parâmetro de nome do arquivo, permitindo a abertura de
	 * qualquer arquivo do excel.
	 * 
	 * @param fileName
	 *            nome do arquivo a ser aberto
	 * @throws IOException 
	 */
	public ExcelUtils(String fileName) throws IOException
	{
		this.fileName = fileName;
		this.abrePlanilha();
	}
	
	/**Construtor com parâmetro de nome do arquivo, permitindo a abertura de
	 * qualquer arquivo do excel.
	 * 
	 * @param fileName nome do arquivo a ser aberto
	 * @param indicePlanilha índice da planilha a ser aberta.
	 * @throws IOException 
	 */
	public ExcelUtils(String fileName, int indicePlanilha) throws IOException
	{
		this.fileName = fileName;
		this.abrePlanilha(indicePlanilha);
	}
	
	/**Construtor com parâmetro de nome do arquivo, permitindo a abertura de
	 * qualquer arquivo do excel.
	 * 
	 * @param fileName nome do arquivo a ser aberto
	 * @param nomePlanilha nome da planilha a ser aberta.
	 * @throws IOException 
	 */
	public ExcelUtils(String fileName, String nomePlanilha) throws IOException {
		this.fileName = fileName;
		this.abrePlanilha(nomePlanilha);
	}

	/**
	 * Método que retorna o objeto manipulador de arquivos do excel
	 * 
	 * @return atributo do tipo XSSFSheet {@link XSSFSheet}
	 */
	public XSSFSheet getPlanilha() {
		return this.planilha;
	}

	/**
	 * Método que retorna o nome do arquivo
	 * 
	 * @return String com o caminho do arquivo aberto
	 */
	public String getFileName() {
		return this.fileName;
	}
	
	//NEW
	
	public void abrirArquivo (String fileName) 
	{
		setFileName(fileName);
		abrePlanilha();
	}
	
	public void setFileName (String fileName)
	{
		this.fileName = fileName;
	}
	
	public String pegarValorDoCampo (String nomeDoCampo)
	{
		for (Row linha : this.getPlanilha())
		{
			for (Cell celula : linha)
			{
				if (celula.getRichStringCellValue().getString().trim().equals(nomeDoCampo)) 
				{
					return getTextoCelula(linha.getRowNum() +1, celula.getColumnIndex());
				}
			}
		}
		return "CAMPO NÂO ENCONTRADO";
	}
	
	public String pegarValorDoCampo (String nomeDoCampo, int rowIndex)
	{
		for (Row linha : this.getPlanilha())
		{
			for (Cell celula : linha)
			{
				
				if (celula.getRichStringCellValue().getString().trim().equals(nomeDoCampo)) 
				{

					return getTextoCelula(rowIndex, celula.getColumnIndex());
				}
			}
		}
		return "CAMPO NÂO ENCONTRADO";
	}
	
	//

	/**
	 * Método que retorna a planilha aberta
	 * 
	 * @return atributo do tipo XSSFWorkbook {@link XSSFWorkbook}
	 */
	public XSSFWorkbook getWorkBook() {
		return this.wb;
	}

	/**
	 * Método que abre a planilha (a primeira da pasta de trabalho do excel).
	 * O mesmo que abrePlanilha(0).
	 * @throws IOException 
	 */
	public void abrePlanilha() 
	{
		abrePlanilha( 0 );
	}
	
	/**Método que abre a planilha.
	 * 
	 * @param indicePlanilha índice da planilha a ser aberta.
	 * @throws IOException 
	 */
	public void abrePlanilha(int indicePlanilha) 
	{
		try ( FileInputStream is = new FileInputStream( new File( this.getFileName() ) ) )
		{
			this.wb = new XSSFWorkbook( is );
		}
		catch ( IOException ex )
		{
			String message = "Erro ao abrir a pasta de Excel em '" + this.getFileName() + "' ...";
			System.out.println(message);
			ex.printStackTrace();	
		}

		this.planilha = this.getWorkBook().getSheetAt(indicePlanilha);
	}
	
	/**Método que abre a planilha.
	 * 
	 * @param nomePlanilha nome da planilha a ser aberta.
	 * @throws IOException 
	 */
	public void abrePlanilha(String nomePlanilha) throws IOException
	{
		try ( FileInputStream is = new FileInputStream( new File( this.getFileName() ).getAbsolutePath() ) )
		{
			this.wb = new XSSFWorkbook( is );
		}
		catch ( IOException ex )
		{
			String message = "Erro ao abrir a pasta de Excel em '" + this.getFileName() + "' ...";
			LOG.error(message);
			throw new IOException(message, ex);
		}

		this.planilha = this.getWorkBook().getSheet(nomePlanilha);
		if ( this.planilha == null )
		{
			String message = "Não foi possível abrir a planilha de nome '" + nomePlanilha + "' ...";
			LOG.error(message);
			throw new IOException(message);
		}
	}

	/**
	 * Método que grava as alterações efetuadas na planilha
	 */
	public void salvaPlanilha() {
		FileOutputStream arquivoSaida = null;
		try {
			arquivoSaida = new FileOutputStream(new File(this.getFileName()));
			this.getWorkBook().write(arquivoSaida);
			
		}
		catch (IOException ex)
		{
			LOG.error("Erro ao salvar a pasta de Excel em '" + this.getFileName() + "' ...", ex);
		}
		finally
		{
			if ( arquivoSaida != null )
			{
				try {
					arquivoSaida.close();
				}
				catch (IOException e)
				{
					LOG.error("Erro ao salvar a pasta de Excel em '" + this.getFileName() + "' ...", e);
				}
			}
		}
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
	public String getTextoCelula(int linha, int coluna) {
		String textoCelula;
		try {
			textoCelula = this.getPlanilha().getRow(linha).getCell(coluna).getStringCellValue();
		} catch (Exception e1) {
			try {
				textoCelula = Integer
						.toString((int) this.getPlanilha().getRow(linha).getCell(coluna).getNumericCellValue());
			} catch (Exception e2) {
				textoCelula = null;
			}
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
	public String getTextoSimplesCelula(int linha, int coluna) {
		String textoCelula;

		textoCelula = this.getPlanilha().getRow(linha).getCell(coluna).getStringCellValue();

		String stringFormat = this.getPlanilha().getRow(linha).getCell(coluna).getCellStyle().getDataFormatString();
		CellNumberFormatter fmt = new CellNumberFormatter(stringFormat);
		textoCelula = fmt.format(this.getPlanilha().getRow(linha).getCell(coluna).getNumericCellValue());

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
	public double getValorCelulaDouble(int linha, int coluna) {
		double valorCelula;
		try {
			valorCelula = this.getPlanilha().getRow(linha).getCell(coluna).getNumericCellValue();
		} catch (Exception ex) {
			valorCelula = Double.parseDouble(this.getPlanilha().getRow(linha).getCell(coluna).getStringCellValue());
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
	public int getValorCelulaInt(int linha, int coluna) {
		int valorCelula;
		try {
			valorCelula = (int) this.getPlanilha().getRow(linha).getCell(coluna).getNumericCellValue();
		} catch (Exception ex) {
			valorCelula = Integer.parseInt(this.getPlanilha().getRow(linha).getCell(coluna).getStringCellValue());
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
	public void setTextoCelula(int linha, int coluna, String value) {
		this.getPlanilha().getRow(linha).getCell(coluna).setCellValue(value);
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
	public void setValorCelulaDouble(int linha, int coluna, double value) {
		this.getPlanilha().getRow(linha).getCell(coluna).setCellValue(value);
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
	public void setValorCelulaInt(int linha, int coluna, int value) {
		this.getPlanilha().getRow(linha).getCell(coluna).setCellValue(value);
	}

	/**
	 * Método que retorna lista com os itens de uma coluna
	 * 
	 * @param coluna
	 *            índice da coluna desejada
	 * @return List com os valores encontrados na coluna
	 */
	public List<String> itensColuna(Integer coluna) {
		List<String> valores = new ArrayList<String>();
		for (Row r : this.getPlanilha()) {
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
	public int achaLinhaPorTexto(String textoCelula) {
		for (Row linha : this.getPlanilha()) {
			for (Cell celula : linha) {
				if (celula.getCellType() == Cell.CELL_TYPE_STRING) {
					if (celula.getRichStringCellValue().getString().trim().equals(textoCelula)) {
						return linha.getRowNum();
					}
				}
			}
		}
		return 1;
	}
	
	public int achaLinhaPorTexto(String textoCelula, int coluna) {
		int linhaEncontrada = -1;
		for (Row linha : this.getPlanilha()) {
			Cell celula = linha.getCell(coluna);
			if (celula != null && celula.getCellType() == Cell.CELL_TYPE_STRING) {
				if (celula.getRichStringCellValue().getString().trim().equals(textoCelula)) {
					linhaEncontrada =  linha.getRowNum();
					break;
				}
			}
		}
		return linhaEncontrada;
	}
	
	
	public void setTipoDaCelulaString(int row, int column)
	{
		try {
			this.getPlanilha().getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_STRING);
		} catch(Exception e) {
			
		}

	}
	
	public void setTipoDaCelulaBlank(int row, int column)
	{
		this.getPlanilha().getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_BLANK);
	}
	
	public void setTipoDaCelulaBoolean(int row, int column)
	{
		this.getPlanilha().getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_BOOLEAN);
	}
	
	public void setTipoDaCelulaError(int row, int column)
	{
		this.getPlanilha().getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_ERROR);
	}
	
	public void setTipoDaCelulaFormula(int row, int column)
	{
		this.getPlanilha().getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_FORMULA);
	}
	
	public void setTipoDaCelulaNumeric(int row, int column)
	{
		this.getPlanilha().getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_NUMERIC);
	}
}

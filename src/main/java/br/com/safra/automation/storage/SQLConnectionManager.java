package br.com.safra.automation.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.safra.automation.selenium.config.LocalTestContextManager;
import br.com.safra.automation.selenium.config.properties.SQLConnectionProperties;
import br.com.safra.automation.selenium.domain.Status;

public class SQLConnectionManager
{
	private static final SQLConnectionProperties props = SQLConnectionProperties.getInstance();
	private static final String url = String.format( "jdbc:sqlserver://%s;databaseName=%s",
			props.getSqlServer(), props.getDatabase() );
	private static volatile Connection con;
	
	private SQLConnectionManager() {}
	
	private static synchronized Connection getConnection() throws SQLException
	{
		if ( con == null )
		{
			con = DriverManager.getConnection(url, props.getUsername(), props.getPassword());
		}
		return con;
	}
	
	public static synchronized void closeConnection() throws SQLException
	{
		if ( con != null )
		{
			con.close();
		}
		con = null;
	}
	
	/**Salva o status de execução de um cenário no banco de dados
	 * apontado em 'sqlconnection.properties'.
	 * 
	 * @param codigoFN ID único do cenário.
	 * @param statusExec true (sucesso), false (falha).
	 * @param dataExecucao uuuu/MM/dd
	 * @param horaExecucao HH:mm:ss
	 * @throws SQLException 
	 */
	public static synchronized void salvaStatusExecucaoTabelaFuncionalidade(
			String codigoFN,
			boolean statusExec,
			String dataExecucao,
			String horaExecucao) throws SQLException
	{
		if ( props.isFiltrarEnvioDeStatus() )
		{
			Status ultimoStatusBD = LocalTestContextManager.getUltimoStatusBD();
			Status statusDaUltimaExecucao = statusExec ? Status.OK : Status.NOK;
			if ( statusDaUltimaExecucao.compareTo( ultimoStatusBD ) > 0 )
			{
				salvaStatusExecucaoNoBD(codigoFN, statusExec, dataExecucao, horaExecucao);
			}
		}
		else
		{
			salvaStatusExecucaoNoBD(codigoFN, statusExec, dataExecucao, horaExecucao);
		}
	}
	
	/**Salva o status de execução de um cenário no banco de dados
	 * apontado em 'sqlconnection.properties'.
	 * 
	 * @param codigoFN ID único do cenário.
	 * @param statusExec true (sucesso), false (falha).
	 * @param dataExecucao uuuu/MM/dd
	 * @param horaExecucao HH:mm:ss
	 * @throws SQLException 
	 */
	private static synchronized void salvaStatusExecucaoNoBD(
			String codigoFN,
			boolean statusExec,
			String dataExecucao,
			String horaExecucao) throws SQLException
	{
		PreparedStatement preparedStatement = null;

		String statusExecucao = (statusExec) ? "PASSED" : "FAILED";
		
		String updateTabelaSQL = "EXEC [dbo].[Report_Aut] @COD_PROJETO = ?, @COD_FN = ?, @STATUS_AUTO = ?, @DATA_EXEC = ?, @HORA_EXEC = ?";
		
		try {
			preparedStatement = SQLConnectionManager.getConnection().prepareStatement(updateTabelaSQL);

			preparedStatement.setString(1, props.getCodigoProjeto());
			preparedStatement.setString(2, codigoFN);
			preparedStatement.setString(3, statusExecucao);
			preparedStatement.setString(4, dataExecucao);
			preparedStatement.setString(5, horaExecucao);

			preparedStatement.executeUpdate();
		}
		finally
		{
			if ( preparedStatement != null )
				preparedStatement.close();
		}
	}
	
	/**Consulta o status de execução de um cenário no banco de dados.
	 * O período de pesquisa é determinado em 'sqlconnection.properties'.
	 * Se nenhuma data for dada, então considera como período o dia de hoje.
	 * 
	 * @param codigoCenario ID único do cenário.
	 * @return true somente se o cenário ainda não teve nenhum sucesso.
	 * @throws SQLException 
	 */
//	public static synchronized boolean deveRodar( String codigoCenario ) throws SQLException
//	{
//		boolean deveRodar = false;
//		String dataInicio = props.getDataInicio();
//		String dataFim = props.getDataFim();
//		if ( dataInicio.isEmpty() || dataFim.isEmpty() )
//		{
//			dataInicio = new UtilidadesDeDataTempo().obterDataDeHoje( 0, "uuuu-MM-dd" );
//			dataFim = dataInicio;
//		}
//		Status status = leStatusExecucao( codigoCenario, dataInicio, dataFim );
//		LocalTestContextManager.setUltimoStatusBD( status );
//		deveRodar = status.equals( Status.OK ) ? false : true;
//		return deveRodar;
//	}
	
	/**Consulta o status de execução de um cenário no banco de dados.
	 * Se houver pelo menos uma execução de sucesso, então OK.
	 * Se houver somente execuções falhadas, então NOK.
	 * Se não houver nenhuma execução no período conforme 'sqlconnection.properties',
	 * então NE.
	 * 
	 * @param codigoCenario ID único do cenário.
	 * @param dataInicio uuuu-MM-dd
	 * @param dataFim uuuu-MM-dd
	 * @return NE (Não Executado), NOK (Falhou), OK (Passou)
	 * @throws SQLException 
	 */
	private static synchronized Status leStatusExecucao(
			String codigoCenario,
			String dataInicio,
			String dataFim) throws SQLException
	{
		Status status = null;
		ResultSet result = null;
		PreparedStatement preparedStatement = null;
		
		String statement = "select STATUS_AUT from [dbo].[EXEC_AUT2] "
				+ "where COD_PROJ = ? "
				+ "and COD_FN = ? "
				+ "and DATA_EXEC between ? and ? "
				+ "order by STATUS_AUT desc ";
		
		try {
			preparedStatement = SQLConnectionManager.getConnection().prepareStatement( statement );

			preparedStatement.setString( 1, props.getCodigoProjeto() );
			preparedStatement.setString( 2, codigoCenario );
			preparedStatement.setString( 3, dataInicio );
			preparedStatement.setString( 4, dataFim );

			result = preparedStatement.executeQuery();

			if ( result != null )
			{
				if ( result.next() )
				{
					status = Status.NOK;
					do {
						String s = result.getString( 1 );
						if ( s.equals("PASSED") ) { status = Status.OK; }
					}
					while ( result.next() && !status.equals( Status.OK ) );
				}
				else
				{
					status = Status.NE;
				}
			}
		}
		finally
		{
			if ( preparedStatement != null )
				preparedStatement.close();
		}
		
		return status;
	}
	
	/**Pesquisa execuções entre duas datas (inclusivo, ie, >= d1 && <= d2),
	 * trazendo ID da execução, data, hora, e o maior status (OK > NOK > NE).
	 * O 'código de projeto' deve ser definido em config/sqlconnection.properties.
	 * A pesquisa é exibida na saída padrão do sistema.
	 * 
	 * @param data1 uuuu-MM-dd
	 * @param data2	uuuu-MM-dd
	 * @return Uma lista contendo vetores de String, cada um representando uma
	 * linha da pesquisa. Ou nulo, se a pesquisa não trouxer nenhum dado.
	 * @throws SQLException 
	 */
	public static synchronized List<String[]> pesquisar( String data1, String data2 ) throws SQLException
	{
		ResultSet result = null;
		PreparedStatement preparedStatement = null;
		List<String[]> pesquisa = null;
		
		String statement = ""
				+ "select COD_FN, max( DATA_EXEC ) as DATA_EXEC, max( HORA_EXEC ) as HORA_EXEC, max( STATUS_AUT ) as STATUS_AUT "
				+ "from [dbo].[EXEC_AUT2] "
				+ "where COD_PROJ = ? "
				+ "and DATA_EXEC between ? and ? "
				+ "group by COD_FN ";
		
		try {
			preparedStatement = SQLConnectionManager.getConnection().prepareStatement( statement );

			preparedStatement.setString( 1, props.getCodigoProjeto() );
			preparedStatement.setString( 2, data1 );
			preparedStatement.setString( 3, data2 );

			result = preparedStatement.executeQuery();
			
			if ( result != null )
			{
				if ( result.next() )
				{
					int contagem = 0;
					pesquisa = new ArrayList<String[]>();
					do {
						String[] linha = new String[] { result.getString( 1 ), result.getString( 2 ), result.getString( 3 ), result.getString( 4 ) };
						pesquisa.add( linha );
						System.out.println( linha[0] + "\t" + linha[1] + "\t" + linha[2] + "\t" + linha[3] );
						contagem++;
					}
					while ( result.next() );
					System.out.println("Total: " + contagem);
				}
			}
		}
		finally
		{
			if ( preparedStatement != null )
				preparedStatement.close();
			closeConnection();
		}
		return pesquisa;
	}
}

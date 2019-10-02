package br.com.safra.automation.selenium.config;

import java.time.LocalDate;
import java.util.HashMap;

import br.com.safra.automation.selenium.domain.Status;

public class LocalTestContextManager {
	private static ThreadLocal<String> idCenario = new ThreadLocal<String>();
	
	private static ThreadLocal<Integer> atualTentativa = new ThreadLocal<Integer>();
		
	private static ThreadLocal<LocalDate> dataDeAgendamento = new ThreadLocal<LocalDate>();
	private static ThreadLocal<Status> ultimoStatusBD = new ThreadLocal<Status>();
	
	private static final ThreadLocal<HashMap<String, Object>> info = new ThreadLocal<HashMap<String, Object>>();
	
	public static Status getUltimoStatusBD()
	{
        return ultimoStatusBD.get();
    }
 
    public static void setUltimoStatusBD( Status s )
    {
    	ultimoStatusBD.set( s );
    }
	
	public static void setInfo( String key, Object value )
	{
		HashMap<String, Object> map = info.get();
		if ( map == null )
		{
			map = new HashMap<String, Object>();
		}
		map.put( key, value );
		info.set( map );
	}
	
	public static Object getInfo( String key )
	{
		return info.get().get( key );
	}
	
	public static LocalDate getDataDeAgendamento() {
        return dataDeAgendamento.get();
    }
 
    public static void setDataDeAgendamento(LocalDate d) {
    	dataDeAgendamento.set(d);
    }
		
	public static int getAtualTentativa() {
        return atualTentativa.get().intValue();
    }
 
    public static void setAtualTentativa(int t) {
    	atualTentativa.set(Integer.valueOf(t));
    }
    
    public static void incrementAtualTentativa() {
    	int t = atualTentativa.get().intValue();
    	atualTentativa.set(Integer.valueOf(t + 1));
    }
	 
    public static String getIdCenario() {
        return idCenario.get();
    }
 
    public static void setIdCenario(String id) {
    	idCenario.set(id);
    }
}

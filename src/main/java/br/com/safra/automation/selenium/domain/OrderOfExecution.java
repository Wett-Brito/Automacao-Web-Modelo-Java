package br.com.safra.automation.selenium.domain;

public enum OrderOfExecution
{
	LINHA, COLUNA;
	
	public static String valoresPermitidos()
	{
		String valoresPermitidos = "";
		OrderOfExecution[] valores = OrderOfExecution.values();
		for (OrderOfExecution valor : valores)
		{
			valoresPermitidos = valoresPermitidos + valor.toString() + ", ";
		}
		valoresPermitidos = valoresPermitidos.substring(0, valoresPermitidos.length() - 2);
		return valoresPermitidos;
	}
}

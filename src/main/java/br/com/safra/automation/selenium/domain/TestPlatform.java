package br.com.safra.automation.selenium.domain;

public enum TestPlatform {
	CHROME, FIREFOX;
	
	public static String valoresPermitidos() {
		String valoresPermitidos = "";
		TestPlatform[] valores = TestPlatform.values();
		for (TestPlatform valor : valores) {
			valoresPermitidos = valoresPermitidos + valor.toString() + ", ";
		}
		valoresPermitidos = valoresPermitidos.substring(0, valoresPermitidos.length() - 2);
		return valoresPermitidos;
	}
}

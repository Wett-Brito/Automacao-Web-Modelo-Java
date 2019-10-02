package br.com.safra.app.teste.Exemplo1;

import org.testng.annotations.Test;

import br.com.safra.app.teste_geral.Massa;
import br.com.safra.automation.selenium.domain.ExtendsReport;
import br.com.safra.automation.selenium.domain.TestBase;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;

@CucumberOptions(                 
		features = "src/main/java/br/com/safra/app/teste/Exemplo1/Exemplo1.feature", 
		glue = { "br/com/safra/app/teste_geral", "br/com/safra/app/teste/Exemplo1" },
        plugin = { "pretty", "json:target/cucumber.json" }, 
        tags = { "~@ignore" }, strict = false)

public class TesteExemplo1 extends TestBase
{
	private static final String idCenario = "Test_0001";
	
	@Test(	groups = {"transacional", "transferencia"},
			description = idCenario,
			dataProvider = "features")
	public void testeClassico( CucumberFeatureWrapper cucumberFeature )
	{
		Massa.setId(idCenario);
		ExtendsReport.Teste(idCenario);
		instanciaDriver(idCenario);
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}
}

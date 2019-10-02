package br.com.safra.app.teste.Exemplo2;

import org.testng.annotations.Test;

import br.com.safra.app.teste_geral.Massa;
import br.com.safra.automation.selenium.domain.ExtendsReport;
import br.com.safra.automation.selenium.domain.TestBase;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;

@CucumberOptions(                 
		features = "src/main/java/br/com/safra/app/teste/Exemplo2/Exemplo2.feature", 
		glue = { "br/com/safra/app/teste_geral", "br/com/safra/app/teste/Exemplo2" },
        plugin = { "pretty", "json:target/cucumber.json" }, 
        tags = { "~@ignore" }, strict = false)

public class TesteExemplo2 extends TestBase
{
	private static final String idCenario = "Test_0002";
	
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

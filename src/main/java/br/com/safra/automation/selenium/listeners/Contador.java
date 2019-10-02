package br.com.safra.automation.selenium.listeners;

public class Contador
{
	private int passed;
	private int failed;
	private int skipped;
	
	public void setPassed(int passed)
	{
		this.passed = passed;
	}
	public void setFailed(int failed)
	{
		this.failed = failed;
	}
	public void setSkipped(int skipped)
	{
		this.skipped = skipped;
	}
	public int getPassed()
	{
		return passed;
	}
	public int getFailed()
	{
		return failed;
	}
	public int getSkipped()
	{
		return skipped;
	}
}
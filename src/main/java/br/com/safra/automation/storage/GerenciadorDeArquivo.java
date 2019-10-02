package br.com.safra.automation.storage;

public abstract class GerenciadorDeArquivo
{
	private String fileLocation;
	
	/**@param fileLocation ./path/to/file.json*/
	protected void setCustomFileLocation( String fileLocation )
	{
		this.fileLocation = fileLocation;
	}
	
	/**Define o local padrão onde espera-se achar o arquivo.*/
	abstract protected void setDefaultFileLocation();
	
	/**Definido por {@link #setCustomFileLocation(String)}.
	 * Se não, então é definido por {@link #setDefaultFileLocation()}.*/
	public String getFileLocation()
	{
		if ( fileLocation == null )
			setDefaultFileLocation();
		return fileLocation;
	}
}

package br.com.safra.automation.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encript
{
	private static final String strkey = "Blowfish";
	private static final Base64 base64 = new Base64(true);

	/**Encripta usando UTF8 e Blowfish.
	 * 
	 * @param data o que será encriptado.
	 * @return os dados encriptados.
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public String encrypt( String data )
			throws UnsupportedEncodingException,
			NoSuchAlgorithmException,
			NoSuchPaddingException,
			InvalidKeyException,
			IllegalBlockSizeException,
			BadPaddingException
	{
		SecretKeySpec key = new SecretKeySpec(strkey.getBytes("UTF8"), "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		return base64.encodeToString(cipher.doFinal(data.getBytes("UTF8")));
	}

	/**Decripta usando UTF8 e Blowfish.
	 * 
	 * @param encrypted o que será decriptado.
	 * @return os dados decriptados.
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String decrypt( String encrypted )
			throws UnsupportedEncodingException,
			NoSuchAlgorithmException,
			NoSuchPaddingException,
			InvalidKeyException,
			IllegalBlockSizeException,
			BadPaddingException
	{
		byte[] encryptedData = Base64.decodeBase64(encrypted);
		SecretKeySpec key = new SecretKeySpec(strkey.getBytes("UTF8"), "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decrypted = cipher.doFinal(encryptedData);
		return new String(decrypted);
	}
}

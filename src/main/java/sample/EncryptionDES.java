package sample;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionDES {
	private static  String key = "aesEncryptionKey";
	private static  String initVector = "encryptionIntVec";
	private static  String originalString = "password";

	public static void main(String[] args) {
		//String originalString = "password";
		System.out.println("Original String to encrypt - " + originalString);
		String encryptedString = encrypt(originalString);
		System.out.println("Encrypted String - " + encryptedString);
//		String decryptedString = decrypt(encryptedString);
//		System.out.println("After decryption - " + decryptedString);
	}
	
	
	public static String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			  return Base64.getEncoder().encodeToString(encrypted);//return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}

package sample;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DecryptionDES {
	private static  String key = "aesEncryptionKey";
	private static  String initVector = "encryptionIntVec";
	private static  String encryptedString = "AIDTAIiCazaQavILI07rtA==";
	public static void main(String[] args) {
//		String originalString = "password";
//		System.out.println("Original String to encrypt - " + originalString);
		
		String decryptedString = decrypt(encryptedString);
		System.out.println("After decryption - " + decryptedString);
	}
	
	
	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}

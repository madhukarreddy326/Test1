package sample;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Entryption {

	public static void main(String[] args) throws Exception {
		System.out.println("Number of Command Line Argument = "+args.length);
        String key = "abcdefghijklmop";
        String clean = "this is madhukar";
        
        int keySize = 16;
		int ivSize=16;
		String abc="SHA-256";
		if(args.length>0)
		{	
			key=args[0];
			clean = args[1];
			ivSize=Integer.parseInt(args[2]);
			abc = args[3];
			keySize=Integer.parseInt(args[4]);
		}
		for(int i = 0; i< args.length; i++) {
			System.out.println(String.format("Command Line Argument %d is %s", i, args[i]));
		}
       byte[] encrypted = encrypt(clean, key,ivSize,abc);
        System.out.println("encrypted"+encrypted);
        String decrypted = decrypt(encrypted, key,ivSize,abc,keySize);
       System.out.println("decrypted:"+decrypted);
    }

    public static byte[] encrypt(String plainText, String key,int ivSize,String abc) throws Exception {
        byte[] clean = plainText.getBytes();

        // Generating IV.
       // int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        //String abc="SHA-256";
        // Hashing key.
        MessageDigest digest = MessageDigest.getInstance(abc);
        digest.update(key.getBytes("UTF-8"));
        byte[] keyBytes = new byte[16];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Encrypt.
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(clean);

        // Combine IV and encrypted part.
        byte[] encryptedIVAndText = new byte[ivSize + encrypted.length];
        System.arraycopy(iv, 0, encryptedIVAndText, 0, ivSize);
        System.arraycopy(encrypted, 0, encryptedIVAndText, ivSize, encrypted.length);

        return encryptedIVAndText;
    }

    public static String decrypt(byte[] encryptedIvTextBytes, String key,int ivSize,String abc,int keySize) throws Exception {
        //int ivSize = 16;
        //int keySize = 16;

        // Extract IV.
        byte[] iv = new byte[ivSize];
        System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extract encrypted part.
        int encryptedSize = encryptedIvTextBytes.length - ivSize;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedIvTextBytes, ivSize, encryptedBytes, 0, encryptedSize);

        // Hash key.
        byte[] keyBytes = new byte[keySize];
        MessageDigest md = MessageDigest.getInstance(abc);
        md.update(key.getBytes());
        System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Decrypt.
        Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);

        return new String(decrypted);
    }
}

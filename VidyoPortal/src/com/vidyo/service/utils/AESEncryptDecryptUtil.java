package com.vidyo.service.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AESEncryptDecryptUtil {

	private static final String TRANSFORMATION = "AES/CBC/PKCS7PADDING";

	private static final String ALGO = "AES";
	
	private static final byte[] IV_BYTES = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public static byte[] calcHashSHA1(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(data); 
		return md.digest();
	}
	
	private static byte[] cryptDeriveKey(byte[] key, int keySize) throws Exception {
		byte[] buffer1 = new byte[64];
		byte[] buffer2 = new byte[64];
		Arrays.fill(buffer1, (byte) 0x36);
		Arrays.fill(buffer2, (byte) 0x5C);
		for (int i = 0; i < key.length; i++) {
			buffer1[i] ^= key[i];
			buffer2[i] ^= key[i];
		}
		try {
			buffer1 = calcHashSHA1(buffer1);
			buffer2 = calcHashSHA1(buffer2);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e);
		}
		byte[] derivedKey = new byte[keySize];
		for (int i = 0; i < keySize; i++) {
			if (i < buffer1.length)
				derivedKey[i] = buffer1[i];
			else
				derivedKey[i] = buffer2[i - buffer1.length];
		}
		return derivedKey;
	}
	
	public static String encryptAESWithBase64(String key, String value) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		
		byte[] hashKey = calcHashSHA1(key.getBytes());
		byte[] derivedKey = cryptDeriveKey(hashKey, 16);
		
		IvParameterSpec ivSpec = new IvParameterSpec(IV_BYTES);
		SecretKeySpec skeySpec = new SecretKeySpec(derivedKey, ALGO);

		Cipher cipher = Cipher.getInstance(TRANSFORMATION, "BC");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

		byte[] encrypted = cipher.doFinal(value.getBytes());
		//String encryptedIV = Base64.encodeBase64String(initVector.getBytes());
		String encryptedString = Base64.encodeBase64String(encrypted);
		//System.out.println("Encrypted IV: " + encryptedIV);
		//System.out.println("Encrypted piece: " + encryptedString);
		//System.out.println("Encrypted extData: " + encryptedIV + encryptedString);

		return encryptedString;
	}

	public static String decryptAESWithBase64(String key, String encrypted) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		
		byte[] hashKey = calcHashSHA1(key.getBytes());
		byte[] derivedKey = cryptDeriveKey(hashKey, 16);
		byte[] pieceDecoded = Base64.decodeBase64(encrypted);
		
		IvParameterSpec iv = new IvParameterSpec(IV_BYTES);
		SecretKeySpec skeySpec = new SecretKeySpec(derivedKey, ALGO);

		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

		byte[] original = cipher.doFinal(pieceDecoded);
		String decrypted = new String(original);
		//System.out.println("Decrypted IV: " + initVectorDecoded);
		//System.out.println("Decrypted piece: " + decrypted);

		return decrypted;
	}

	public static void main(String[] args) throws Exception {
		String MESSAGE = "Usage: java -jar encryptor.jar -decrypt|-encrypt <Secret Key> <Data>";
		if (args.length != 3) {
			System.out.println(MESSAGE);
			return;
		}
		
		String operation = args[0];
		if (!"-decrypt".equalsIgnoreCase(operation) && !"-encrypt".equalsIgnoreCase(operation)) {
			System.out.println(MESSAGE);
			return;
		}
		
		String key = args[1]; // "BarTesttBarTestt"; // 128 bit(16 bytes) key
		String data = args[2]; // "SessionID=7&ConferenceID=245&ExternalID=5&ExternalIDType=1&FirstName=test&LastName=xx&AppointmentTime=22:23";

		if ("-encrypt".equalsIgnoreCase(operation)) {
			System.out.println("\n");
			String encrypted = encryptAESWithBase64(key, data);
			System.out.println("Encrypted extData: " + encrypted);
		}
		
		if ("-decrypt".equalsIgnoreCase(operation)) {
			System.out.println("\n");
			String decrypted = decryptAESWithBase64(key, data);
			System.out.println("Decrypted extData: " + decrypted);
		}
	}
}

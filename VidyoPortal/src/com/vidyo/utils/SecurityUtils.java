package com.vidyo.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SecurityUtils {

	protected final static Logger logger = LoggerFactory.getLogger(SecurityUtils.class.getName());
	public final static String CUSTOMER_SERVICE_PUBLIC_KEY_PATH = "/opt/vidyo/etc/vidyo_support/vidyo_support.key";
	public final static String CUSTOMER_SERVICE_PRIVATE_KEY_PKCS8_PATH = "/opt/vidyo/etc/vidyo_support/vidyo_support.pkcs8.key";

	/**
	 * Input file (inputTarGzFilePath) to encrypt MUST be a tar.gz file!
	 *
	 * @param password
	 * @param tmpDir
	 * @param inputTarGzFilePath
	 * @param outputFilePath
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws ClassNotFoundException
	 */
	public static void createSecureArchive(String password, String tmpDir, String inputTarGzFilePath, String outputFilePath)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
			InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException,
			ClassNotFoundException {

		// hash password to make a key
		int saltSize = 16;
		int iterations = 65536;
		int keySize = 128;
		SecureRandom random = new SecureRandom();
		byte salt[] = new byte[saltSize];
		random.nextBytes(salt);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		PBEKeySpec spec = new PBEKeySpec(
				password.toCharArray(),
				salt,
				iterations,
				keySize
		);
		SecretKey passwordHash = factory.generateSecret(spec);

		// encrypt payload with this key (AES is a 128-bit block cipher supporting keys of 128, 192, and 256 bits.)
		SecretKeySpec key = new SecretKeySpec(passwordHash.getEncoded(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		File inputFile = new File(inputTarGzFilePath);
		String tmpFile = tmpDir + "/" + iterations + "." + Hex.encodeHexString(salt) + "." + Hex.encodeHexString(cipher.getIV()) + ".enc";
		try (FileInputStream in = new FileInputStream(inputFile);
			 FileOutputStream out = new FileOutputStream(new File(tmpFile));
			 CipherOutputStream cipherOut = new CipherOutputStream(out, cipher)) {
			int length = 0;
			byte[] buffer = new byte[1024 * 128];
			while ((length = in.read(buffer)) != -1) {
				cipherOut.write(buffer, 0, length);
			}
		}

		// encrypt key with customer support public key
		String csPubKeyFilePath = CUSTOMER_SERVICE_PUBLIC_KEY_PATH;//"/tmp/cs.pub.key";
		String csPubKey = FileUtils.readFileToString(new File(csPubKeyFilePath));
		csPubKey = csPubKey.replace("-----BEGIN PUBLIC KEY-----", "");
		csPubKey = csPubKey.replace("-----END PUBLIC KEY-----", "");
		csPubKey = StringUtils.deleteWhitespace(csPubKey);

		X509EncodedKeySpec csPubKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(csPubKey));
		KeyFactory fact = KeyFactory.getInstance("RSA");
		PublicKey pubKey = fact.generatePublic(csPubKeySpec);
		Cipher rsaCipher = Cipher.getInstance("RSA");
		rsaCipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] cipherData = rsaCipher.doFinal(passwordHash.getEncoded());

		String tmpCsFile = tmpDir + "/cs." + System.currentTimeMillis() + ".enc";
		FileUtils.writeByteArrayToFile(new File(tmpCsFile), cipherData);

		CompressionUtils.targzip(new String[] { tmpFile, tmpCsFile}, outputFilePath, true);

		FileUtils.deleteQuietly(new File(tmpFile));
		FileUtils.deleteQuietly(new File(tmpCsFile));
	}

	/**
	 * Will extract the encrypted archive including untarring the final result.
	 *
	 * @param password
	 * @param inputFile
	 * @param outputDir
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws DecoderException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static void extractSecureArchiveUsingPassword(String password, String inputFile, String outputDir)
		throws IOException, NoSuchAlgorithmException, DecoderException, InvalidKeySpecException,
	NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
	InvalidAlgorithmParameterException {

		int iterations = 65536;
		int keySize = 128;
		String salt = "?";
		String fileToDecode = "?";
		String csFile = "?";
		byte[] iv = "0".getBytes();

		CompressionUtils.untargzip(inputFile, outputDir, true);
		// contains a *.enc file and a cs.*.enc file
		File dir = new File(outputDir);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".enc");
			}
		});
		if (files.length == 2) {
			for (File file : files) {
				if (!file.getName().startsWith("cs.")) {
					fileToDecode = file.getName();
					String[] parts = fileToDecode.split("\\.");
					if (parts.length == 4) {
						try {
							iterations = Integer.parseInt(parts[0]);
						} catch (NumberFormatException nfe) {
							logger.error("NumberFormatException unexpected");
						}
						salt = parts[1];
						iv = Hex.decodeHex((parts[2]).toCharArray());
					}
				} else {
					csFile = file.getName();
				}
			}
		}

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		PBEKeySpec spec = new PBEKeySpec(
				password.toCharArray(),
				Hex.decodeHex(salt.toCharArray()),
				iterations,
				keySize
		);
		SecretKey passwordHash = factory.generateSecret(spec);

		SecretKeySpec key = new SecretKeySpec(passwordHash.getEncoded(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

		File encFile = new File(outputDir + "/" + fileToDecode);
		String tmpFile = outputDir + "/" +  System.currentTimeMillis() + ".tmp.tar";
		try (FileInputStream in = new FileInputStream(encFile);
			 FileOutputStream out = new FileOutputStream(new File(tmpFile));
			 CipherInputStream cipherIn = new CipherInputStream(in, cipher)){
			int length;
			byte[] buffer = new byte[1024 * 128];
			while ((length = cipherIn.read(buffer)) != -1) {
				out.write(buffer, 0, length);
				out.flush();
			}
			CompressionUtils.untargzip(tmpFile, outputDir, true);
		} finally {
			FileUtils.deleteQuietly(new File(tmpFile));
			FileUtils.deleteQuietly(new File(outputDir + "/" + fileToDecode));
			FileUtils.deleteQuietly(new File(outputDir + "/" + csFile));
		}
	}

	public static void extractSecureArchiveUsingCsPrivateKey(String inputFile, String outputDir)
			throws IOException, NoSuchAlgorithmException, DecoderException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {

		String salt = "?";
		String fileToDecode = "?";
		String csFile = "?";
		byte[] iv = "0".getBytes();
		byte[] unencryptedData = "0".getBytes();

		CompressionUtils.untargzip(inputFile, outputDir, true );
		File dir = new File(outputDir);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".enc");
			}
		});
		if (files.length == 2) {
			for (File file : files) {
				if (!file.getName().startsWith("cs.")) {
					fileToDecode = file.getName();
					String[] parts = fileToDecode.split("\\.");
					if (parts.length == 4) {
						salt = parts[1];
						iv = Hex.decodeHex((parts[2]).toCharArray());
					}
				} else {
					// decrypt cs.enc file
					csFile = file.getName();
					//  openssl pkcs8 -topk8 -inform PEM -outform DER -in cs.priv.key  -nocrypt > cs.priv.pkcs8.key
					String csPrivKeyFilePath = CUSTOMER_SERVICE_PRIVATE_KEY_PKCS8_PATH; //"/tmp/cs.priv.pkcs8.key";
					byte[] encryptedData = FileUtils.readFileToByteArray(new File(csPrivKeyFilePath));
					PKCS8EncodedKeySpec csPrivKeySpec = new PKCS8EncodedKeySpec(encryptedData);
					KeyFactory fact = KeyFactory.getInstance("RSA");
					PrivateKey privKey = fact.generatePrivate(csPrivKeySpec);
					Cipher rsaCipher = Cipher.getInstance("RSA");
					rsaCipher.init(Cipher.DECRYPT_MODE, privKey);
					unencryptedData = rsaCipher.doFinal(FileUtils.readFileToByteArray(file));
				}
			}
		}

		SecretKeySpec key = new SecretKeySpec(unencryptedData, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

		File encFile = new File(outputDir + "/" + fileToDecode);
		String tmpFile = outputDir + "/" + System.currentTimeMillis() + ".tmp.tar";
		try (FileInputStream in = new FileInputStream(encFile);
			 FileOutputStream out = new FileOutputStream(new File(tmpFile));
			 CipherInputStream cipherIn = new CipherInputStream(in, cipher)) {
			int length;
			byte[] buffer = new byte[1024 * 128];
			while ((length = cipherIn.read(buffer)) != -1) {
				out.write(buffer, 0, length);
				out.flush();
			}
		}

		try {
			CompressionUtils.untargzip(tmpFile, outputDir, true);
		} finally {
			FileUtils.deleteQuietly(new File(outputDir + "/" + fileToDecode));
			FileUtils.deleteQuietly(new File(outputDir + "/" + csFile));
		}
	}

	public static void main(String[] args) {
		try {
			createSecureArchive("password", "/home/vikram/test/tmp2", "/home/vikram/test/code.tar.gz", "/home/vikram/test/code.veb");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			extractSecureArchiveUsingPassword("password", "/home/vikram/test/code.veb", "/home/vikram/test/tmp");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			//extractSecureArchiveUsingCsPrivateKey("/home/vikram/test/code.var", "/home/vikram/test/tmp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

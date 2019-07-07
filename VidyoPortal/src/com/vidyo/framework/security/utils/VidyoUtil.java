/**
 * 
 */
package com.vidyo.framework.security.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;

/**
 * @author ganesh
 *
 */
public class VidyoUtil {

	protected static final Logger logger = LoggerFactory
			.getLogger(VidyoUtil.class);
	
	private static final String ENCRYPT = "/usr/bin/openssl enc -aes-256-cbc -a -md sha1 -pass file:";
	
	private static final String DECRYPT = "/usr/bin/openssl enc -aes-256-cbc -d -a -md sha1 -pass file:";
	
	private static final String PASS_FILE = "/opt/vidyo/etc/ssl/private/misc.pem";

	/**
	 * 
	 * @param encrypted
	 * @return
	 */
	public static String decrypt(String encrypted) {
		String decrypted = null;
		File file = new File(System.getProperty("java.io.tmpdir"), generateUniqueFileName());
		try {
			FileUtils.write(file, encrypted + "\n");
			logger.debug("Command executed decrypt ->" + DECRYPT);
			String cmd = DECRYPT + PASS_FILE + " -in " +file.getAbsolutePath();
			ShellCapture capture = ShellExecutor.execute(cmd);
			if (capture == null || capture.getExitCode() != 0) {
				throw new RuntimeException(
						"Failed to decrypt the value, throwing exception to avoid caching");
			}
			decrypted = capture.getStdOut().trim();
		} catch (ShellExecutorException e) {
			throw new RuntimeException(
					"Failed to execute shell script for decryption, throwing exception to avoid caching");
		} catch (IOException e) {
			throw new RuntimeException(
					"Failed to create file, throwing exception to avoid caching");
		} finally {
			FileUtils.deleteQuietly(file);
		}
		logger.debug("password after decryption:" + decrypted);
		// Empty passwords are valid, Throw exception only in case of exit
		// status != 0
		return decrypted;
	}

	/**
	 * 
	 * @return
	 */
	protected static String generateUniqueFileName() {
		String filename = "";
		long millis = System.currentTimeMillis();
		String datetime = new Date().toString();
		datetime = datetime.replace(" ", "");
		datetime = datetime.replace(":", "");
		String rndchars = RandomStringUtils.randomAlphanumeric(16);
		filename = rndchars + "_" + datetime + "_" + millis;
		return filename;
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	public static String encrypt(String val) {
		String encrypted = null;
		File file = new File(System.getProperty("java.io.tmpdir"), generateUniqueFileName());
		try {
			FileUtils.write(file, val + "\n");
			logger.debug("Command executed encrypt->" + ENCRYPT);
			String cmd = ENCRYPT + PASS_FILE + " -in " +file.getAbsolutePath();
			ShellCapture capture = ShellExecutor.execute(cmd);
			if (capture == null || capture.getExitCode() != 0) {
				throw new RuntimeException(
						"Failed to encrypt the value, throwing exception to avoid caching");
			}
			encrypted = capture.getStdOut().trim();
		} catch (ShellExecutorException e) {
			throw new RuntimeException(
					"Failed to execute shell script for encryption, throwing exception to avoid caching");
		} catch (IOException e) {
			throw new RuntimeException(
					"Failed to create file, throwing exception to avoid caching");
		} finally {
			FileUtils.deleteQuietly(file);
		}
		logger.debug("password after encryption:" + encrypted);
		if (StringUtils.isEmpty(encrypted)) {
			throw new RuntimeException(
					"Failed to encrypt the password value, throwing exception to avoid caching");
		}
		return encrypted;
	}

}

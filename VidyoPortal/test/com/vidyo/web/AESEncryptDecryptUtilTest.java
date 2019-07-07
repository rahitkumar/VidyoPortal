package com.vidyo.web;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.vidyo.service.utils.AESEncryptDecryptUtil;

public class AESEncryptDecryptUtilTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testEncryptAndThenDecrypt() throws Exception {
		String key = "BarTesttBarTestt"; // 128 bit(16 bytes) key
		String data = "SessionID=7&ConferenceID=245&ExternalID=5&ExternalIDType=1&FirstName=test&LastName=xx&AppointmentTime=22:23";

		String encrypted = AESEncryptDecryptUtil.encryptAESWithBase64(key, data);
		String decrypted = AESEncryptDecryptUtil.decryptAESWithBase64(key, encrypted);

		Assert.assertEquals(data, decrypted);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDecryptProvidedString() throws Exception {
		String key = "BarTesttBarTestt"; // 128 bit(16 bytes) key
		String data = "BrQQYO4NosvBR/wmVSU42D4bu1sjnAXeo6YICohXHgqQolsZEnZGY+Eldn89uAMSgaZWwTmvxLGpl9mmaLZyI7nWrFHZNyz7e9XaGC1EZ8nUv7zFJg5m54CYnpc+U4b7mHB+o3vr9+Et9JdpizD7Tg==";

		String decrypted = AESEncryptDecryptUtil.decryptAESWithBase64(key, data);

		Assert.assertEquals(
				"SessionID=7&ConferenceID=245&ExternalID=5&ExternalIDType=1&FirstName=test&LastName=xx&AppointmentTime=22:23",
				decrypted);
	}
}

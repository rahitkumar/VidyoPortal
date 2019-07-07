package com.vidyo.service.pki;

import static org.junit.Assert.assertTrue;

import java.security.cert.CertificateException;

import org.junit.Before;
import org.junit.Test;

public class MemberNameTest {
	private static final String LEGACY_REGEX_USERNAME = "^[[^\\p{Punct}]&&[^\\p{Space}]][[[^\\p{Punct}]&&[^\\p{Space}]][_\\-\\.@]]*$";
	private static final String REGEX_USERNAME ="^[a-zA-Z0-9-_\\@\\-\\.]+$";

	@Before
	public void setUp() throws Exception {

	}
	@Test
	public void memberNameValidationTest_0() throws CertificateException{

		String userName="H_j-@";
			assertTrue(validateUserName0(userName));





	}
	private boolean validateUserName0(String userName) throws CertificateException {

		


	
	
			if(userName.matches(LEGACY_REGEX_USERNAME)){
			  return true;
			}
		
	

		return false;

	}
	private String validateUserName(String userName) throws CertificateException {

		if(userName==null || userName.isEmpty()){
			throw new CertificateException("User Name from the certificate is empty");
		}


		char[] tempArrays=userName.toCharArray();
		StringBuffer  specialCharSkippedVersion =new StringBuffer();
		for(char  c:tempArrays){
			String str=String.valueOf(c);
			if(str.matches(REGEX_USERNAME)){
				specialCharSkippedVersion.append(str);
			}
		}
		if(specialCharSkippedVersion.length()>80){
			return specialCharSkippedVersion.substring(0,80);
		}

		return specialCharSkippedVersion.toString();

	}
	@Test
	public void memberNameValidationTest_1() throws CertificateException{

		String userName="Hojin Joy";
		System.out.println("Hojin Joy"+validateUserName(userName));
		assertTrue(!userName.equalsIgnoreCase(validateUserName(userName)));
		assertTrue("HojinJoy".equalsIgnoreCase(validateUserName(userName)));





	}
	@Test
	public void memberNameValidationTest_2() throws CertificateException{

		String userName="Hojin_Joy";
		System.out.println(validateUserName(userName));
		assertTrue(userName.equalsIgnoreCase(validateUserName(userName)));





	}
	@Test
	public void memberNameValidationTest_3() throws CertificateException{

		String userName="Hojin.Joy";
		System.out.println(validateUserName(userName));
		assertTrue(userName.equalsIgnoreCase(validateUserName(userName)));





	}
	@Test
	public void memberNameValidationTest_4() throws CertificateException{

		String userName="Hojin#$%^&()Joy";
		System.out.println(validateUserName(userName));
		assertTrue(!userName.equalsIgnoreCase(validateUserName(userName)));





	}
	@Test
	public void memberNameValidationTest_5() throws CertificateException{

		String userName="Hojin-Joy-@.\\test*-+!@$#%^&*()1234567890+-=_";
		System.out.println(validateUserName(userName));
		assertTrue(!userName.equalsIgnoreCase(validateUserName(userName)));





	}
	@Test
	public void memberNameValidationTest_limit() throws CertificateException{

		String userName="gSmVyc2V5MRMwEQYDVQQHDApIYWNrZW5zYWNrMQ4wDAYDVQQKDAVWaWR5bzEfMB0GA1UEAwwWMDAwYzI5OThmNzEyMTQ0MDYyMDI4NjAeFw0xNTA4MjYyMDE4MDZaFw0zNTA4MjEyMDE4MDZaMGgxCzAJBgNVBAYTAlVTMRMwEQYDVQQIDApOZXcgSmVyc2V5MRMwEQYDVQQHDApIYWNrZW5zYWNrMQ4wDAYDVQQKDAVWaWR5bzEfMB0GA1UEAwwWMDAwYzI5OThmNzEyMTQ0MDYyMDI4NjCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA6Sf7b64qeEAc5If7fOIBl8zUkyaSOJSS3PCQH+GjYPBOqiR5tdVUwd5CoSwEOdY9UK7AUIgLytlI/VXBkdI71tMpXwZyftN9J6ubdiPP+MrSaCrNre0k7povG9+SdcFF8YWowTxqSX/h/owDDZ1Iiz+eMQfnRk8JDzfkVvxJZTECAwEAAaNQME4wHQYDVR0OBBYEFH6msYoUbUs4l7UhDpb4SZcEs4yGMB8GA1UdIwQYMBaAFH6msYoUbUs4l7UhDpb4SZcEs4yGMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADgYEAZNiZraPQHuG/Q0eX8kJXApsqXHu2tube1Ou1/tlT1y4zDlzJAuVaRQLCsc+slcQDH0gib/W1G316KH5ntjgaQEDqo0la5GIvOPIf2r5C5KPc";
		System.out.println(validateUserName(userName));
		assertTrue(!userName.equalsIgnoreCase(validateUserName(userName)));





	}
}

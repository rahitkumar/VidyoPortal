/**
 * 
 */
package com.vidyo.utils;

import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * @author ganesh
 *
 */
public class ValidationUtilsTest {
	
	@Test
	public void testInvalidEmailAddressWithTwoAtSymbols() {
		String emailwith2AtSymbols = "invalid@address@invalid.com";
		Assert.assertFalse(emailwith2AtSymbols.matches(ValidationUtils.EMAIL_REGEX));
	}
	
	@Test
	public void testValidEmailAddress() {
		String emailwith2AtSymbols = "validaddress@valid.com";
		Assert.assertTrue(emailwith2AtSymbols.matches(ValidationUtils.EMAIL_REGEX));
	}
	
	@Test
	public void testValidEmailAddressWithApostrophe() {
		String emailwith2AtSymbols = "valid'address@valid.com";
		Assert.assertTrue(emailwith2AtSymbols.matches(ValidationUtils.EMAIL_REGEX));
	}
	
	@Test
	public void testValidEmailAddressWithMixedCase() {
		String emailWithMixedCharacters = "Cervin-FORUM2@weforum.org";
		Assert.assertTrue(emailWithMixedCharacters.matches(ValidationUtils.EMAIL_REGEX));
	}
	
	@Test
	public void testValidEmailAddressWithAllUpperCase() {
		String emailWithMixedCharacters = "CERVIN-FORUM2@WEFORUM.ORG";
		Assert.assertTrue(emailWithMixedCharacters.matches(ValidationUtils.EMAIL_REGEX));
	}
	
	@Test
	public void testInvalidEmailAddressWithNoAtSymbols() {
		String emailwith2AtSymbols = "invalid_address_invalid.com";
		Assert.assertFalse(emailwith2AtSymbols.matches(ValidationUtils.EMAIL_REGEX));
	}
	
	@Test
	public void testAlphaNumericValidGroupName() {
		String groupNameAlphaNumeric = "groupname123";
		Assert.assertTrue(groupNameAlphaNumeric.matches(ValidationUtils.GROUP_NAME_REGEX));
	}
	
	@Test
	public void testValidGroupNameWithSupportedSpecialChars() {
		String groupNameAlphaNumeric = "groupname.123_hdf@";
		Assert.assertTrue(groupNameAlphaNumeric.matches(ValidationUtils.GROUP_NAME_REGEX));
	}
	
	@Test
	public void testInValidGroupNameWithUnsupportedSpecialChars() {
		String groupNameAlphaNumeric = "groupname123#%";
		Assert.assertFalse(groupNameAlphaNumeric.matches(ValidationUtils.GROUP_NAME_REGEX));
	}
	
	@Test
	public void testInValidGroupNameWithSpaces() {
		String groupNameAlphaNumeric = "groupname 123";
		System.out.println(""+ groupNameAlphaNumeric.matches(ValidationUtils.GROUP_NAME_REGEX));
		Assert.assertFalse(groupNameAlphaNumeric.matches(ValidationUtils.GROUP_NAME_REGEX));
	}
	
	@Test
	public void testValidIPCDomainNames() {
		Assert.assertTrue(ValidationUtils.isValidIPCDomainName("example.com"));
		Assert.assertTrue(ValidationUtils.isValidIPCDomainName("*.example.com"));
		Assert.assertTrue(ValidationUtils.isValidIPCDomainName("example.*.com"));
		Assert.assertTrue(ValidationUtils.isValidIPCDomainName("172.24.248.131"));
		Assert.assertTrue(ValidationUtils.isValidIPCDomainName("2001:db8::ff00:42:8329"));
		Assert.assertTrue(ValidationUtils.isValidIPCDomainName("FE80:CD00:0000:0CDE:1257:0000:211E:729C"));
		Assert.assertTrue(ValidationUtils.isValidIPCDomainName("FE80:CD00:0:CDE:1257:0:211E:729C"));
	}
	
	@Test
	public void testInValidIPCDomainNames() {
		Assert.assertFalse(ValidationUtils.isValidIPCDomainName("example."));
		Assert.assertFalse(ValidationUtils.isValidIPCDomainName("ff:pp"));
		Assert.assertFalse(ValidationUtils.isValidIPCDomainName("172.24.248"));
		Assert.assertFalse(ValidationUtils.isValidIPCDomainName("2001:db8:f"));
		Assert.assertFalse(ValidationUtils.isValidIPCDomainName("<img onerror=alert('XSS') src=a>"));
	}
}

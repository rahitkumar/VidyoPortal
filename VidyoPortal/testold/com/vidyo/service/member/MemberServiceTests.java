/**
 * 
 */
package com.vidyo.service.member;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.spring.annotation.SpringApplicationContext;

import com.vidyo.service.MemberServiceImpl;

/**
 * @author ganesh
 * 
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class MemberServiceTests {

	/**
	 * 
	 */
	@Test
	public void testSingleWordLettersOnlyLegacyUsername() {
		String legacyUsername = "gfgfdgfgfg";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(true, result);
	}

	/**
	 * 
	 */
	@Test
	public void testMultiWordLettersOnlyLegacyUsername() {
		String legacyUsername = "gfgfdgfgfg cvbcvnbcnb vbvcnbn";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(true, result);
	}

	/**
	 * 
	 */
	@Test
	public void testMultiWordAplhaNumericLegacyUsername() {
		String legacyUsername = "gfgfdgfgfg 343344 vbvcnbn";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(true, result);
	}

	/**
	 * 
	 */
	@Test
	public void testMultiWordAplhaNumericLegacyUsernameMixed() {
		String legacyUsername = "gfgf34343dgfgfg 3433xcvc44 vbvc43434nbn";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(true, result);
	}

	/**
	 * 
	 */
	@Test
	public void testSingleWordAlphaNumericLegacyUsernameNumbersLast() {
		String legacyUsername = "gfgfdgfgfg12323234";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(true, result);
	}

	/**
	 * 
	 */
	@Test
	public void testSingleWordAlphaNumericLegacyUsernameNumbersFirst() {
		String legacyUsername = "12323234jghfggfg";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(true, result);
	}

	/**
	 * 
	 */
	@Test
	public void testSingleWordAlphaNumericLegacyUsernameMixed() {
		String legacyUsername = "12323234jghfggfgq3434";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(true, result);
	}

	/**
	 * 
	 */
	@Test
	public void testSingleWordAlphaNumericLegacyUsernameSplChars() {
		String legacyUsername = "1232...323_4j.g_hf-ggfgq3434---";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(true, result);
	}

	/**
	 * 
	 */
	@Test
	public void testMultiWordAlphaNumericLegacyUsernameWithValidSpecialChars() {
		String legacyUsername = "fgfgfg233--- -.fjhjgkfg -.jfkghfgfg -._fgjfg";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(true, result);
	}

	/**
	 * 
	 */
	@Test
	public void testMultiWordAlphaNumericLegacyUsernameWithInValidSpecialChars() {
		String legacyUsername = "fgfgfg!@# %^&&fjhjgkfg jfkghfgfg fgjfg";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(false, result);
	}

	/**
	 * 
	 */
	@Test
	public void testMultiWordAlphaNumericLegacyUsernameWithSpecialCharsBeginning() {
		String legacyUsername = "---fgfgfg... 233fjhjgkfg-- jfkghfgfg fgjfg";
		boolean result = MemberServiceImpl.isValidLegacyUsername(legacyUsername);
		Assert.assertEquals(false, result);
	}

}

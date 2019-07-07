/**
 * 
 */
package com.vidyo.bo;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import com.vidyo.utils.PortalUtils;
import com.vidyo.utils.SecureRandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * @author Ganesh
 * 
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ShaUtilsTest {
	
	@Test
	public void testRoomKeyGeneration() {
		String key = SecureRandomUtils.generateRoomKey();
		System.out.println(key);
	}
	
	@Test
	public void testGuestUsernameGeneration() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String guestUsername = PortalUtils.generateKey(32);
		//String encoded = SHA1.enc(guestUsername);
		System.out.println(guestUsername);
	}
	
	@Test
	public void testNumericKey() {
		Set<Long> longs = new HashSet<Long>();
		for(int i = 0; i < 100; i++) {
			long s = SecureRandomUtils.generateNumericHash();
			longs.add(s);			
		}
		System.out.println("size" + longs.size());
	}

}

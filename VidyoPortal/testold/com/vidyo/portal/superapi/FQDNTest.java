package com.vidyo.portal.superapi;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
/**
 * small test for the reg ex used in the RegExValidator
 */
public class FQDNTest {
	Pattern fqdnPattern=null;
	Pattern fqdnPortNo=null;
	Pattern ipv4Pattern=null;
	Pattern ipv6Pattern=null;
	
	@Before
	public void setUp() throws Exception {
		 fqdnPattern = Pattern.compile("^((?=[a-z0-9-]{1,63}\\.)[a-z0-9]+(-[a-z0-9]+)*\\.)+[a-z0-9]{2,63}$", Pattern.CASE_INSENSITIVE);
		 fqdnPortNo = Pattern.compile("^(((?=[a-z0-9-]{1,63}\\.)[a-z0-9]+(-[a-z0-9]+)*\\.)+[a-z0-9]{2,63})(\\:\\d{1,5})$", Pattern.CASE_INSENSITIVE);
		 ipv4Pattern=Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
		 ipv6Pattern= Pattern.compile("^((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?$");

		  
//^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\-]*[A-Za-z0-9])$
	}

	@Test
	public void test() {
		assertTrue(isFQDN("vcportalg.com"));
	}
	@Test
	public void test_fqdn1() {
		assertTrue(isFQDN("vc.com"));
	}
	@Test
	public void test_fqdnPortNo1() {
		assertTrue(isFQDN("vc.com:2222"));
	}
	@Test
	public void test_fqdnPortNo2() {
		assertTrue(isFQDN("my.splunk.server.com:5000"));
	}
	@Test
	public void test_fqdnPortNo3() {
		assertTrue(isFQDN("my.splunk.server.com:5000"));
	}
	@Test
	public void test_fqdnPortNo3Invalid() {
		assertFalse(isFQDN("my.splunk.server.com:500022"));
	}
	@Test
	public void test_fqdnPortNo3Invalid1() {
		assertFalse(isFQDN("my.splunk.server.com:65536"));
	}
	@Test
	public void test_fqdnPortNo3valid1() {
		assertTrue(isFQDN("my.splunk.server.com:65535"));
	}
	@Test
	public void test_fqdnPortNo3Invalid3() {
		assertFalse(isFQDN("my.splunk.server.com:"));
	}
	@Test
	public void test_fqdnInvalid1() {
		assertFalse(isFQDN("test"));
	}
	@Test
	public void test_fqdnPortNo4() {
		assertTrue(isFQDN("my.splunk.server.com:65535"));
	}
	@Test
	public void test_IP() {
		assertTrue(isFQDN("172.16.42.233"));
	}
	@Test
	public void test_IP1() {
		assertTrue(isFQDN("1.1.1.1"));
	}
	@Test
	public void test_IPv6() {
		assertTrue(isFQDN("FEDC:BA98:7654:3210:FEDC:BA98:7654:3210"));
	}
	@Test
	public void test_IPv6_1() {
		assertTrue(isFQDN("0:0:0:0:0:0:0:1"));
	}
	@Test
	public void test_IPv6_invalid() {
		assertFalse(isFQDN("FEDC:BA98:7654:3210:FEDC:BA98:7654:3210:AA31"));
	}
	@Test
	public void test_IPv6_valid_Zero_Compressed() {
		assertTrue(isFQDN("2001:db8:0:0:0::1"));
	}
	@Test
	public void test_IPv6_valid_Zero_Compressed_1() {
		assertTrue(isFQDN("2001:db8:0:0::1"));
	}
	
	@Test
	public void test_IPv6_valid_Zero_Compressed_2() {
		assertTrue(isFQDN("2001:db8:0::1"));
	}
	
	@Test
	public void test_IPv6_valid_Zero_Compressed_3() {
		assertTrue(isFQDN("2001:db8::1"));
	}

	@Test
	public void test_IPv6_valid_Zero_Compressed_4() {
		assertTrue(isFQDN("2001:db8:aaaa:bbbb:cccc:dddd::1"));
	}
	@Test
	public void test_IPv6_valid_Zero_Compressed_5() {
		assertTrue(isFQDN("2001:db8:aaaa:bbbb:cccc:dddd:0:1"));
	}
   
	@Test
	public void test_IPv6_valid_Zero_Compressed_6() {
		assertTrue(isFQDN("2001:db8:0:0:aaaa::1"));
	}
	@Test
	public void test_IPv6_valid_Zero_Compressed_7() {
		assertTrue(isFQDN("2001:db8::aaaa:0:0:1"));
	}
	@Test
	public void test_fqdn_SpecialCharacter() {
		assertFalse(isFQDN("Defe!@##_ sfsf.wrwrt.com"));
	}
	@Test
	public void test_fqdn_UnderscoreCharacter() {
		assertFalse(isFQDN("Defe_sfsf.wrwrt.com"));
	}
	@Test
	public void test_fqdn_number() {
		assertTrue(isFQDN("logserver.vidyo2"));
	}
	
	@Test
	public void test_fqdn_number_port() {
		assertTrue(isFQDN("logserver.vidyo2:2222"));
	}
	@Test
	public void test_fqdn_number_port1() {
		assertFalse(isFQDN("logserver.vidyo:2223332"));
	}
	
    
    

    
	
	
	 public  boolean isFQDN(String val)
 {
		try {
			Matcher matcher = fqdnPattern.matcher(val);
			if (matcher.matches()) {
				return true;
			} else {
				matcher = fqdnPortNo.matcher(val);
				if (matcher.matches()) {
					String portNoStr = val.split(":")[1];
					int portNo = Integer.valueOf(portNoStr);
					if (portNo > 65535)
						return false;
					else
						return true;
				} else {
					matcher = ipv4Pattern.matcher(val);
					if (matcher.matches()) {
						return true;
					} else {
						matcher = ipv6Pattern.matcher(val);
						if (matcher.matches()) {
							return true;
						} else {
							return false;
						}
					}

				}
			}
		} catch (Exception e) {
		}
		return false;

	}
}

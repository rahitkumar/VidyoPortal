/**
 * 
 */
package com.vidyo;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.util.UriUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.db.IRoomDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IRoomService;
import com.vidyo.service.RoomServiceImpl;

/**
 * @author ganesh
 *
 */
public class SimpleTest {

	@InjectMocks
	private IRoomService roomService = new RoomServiceImpl(); // Class to be
																// tested
	@Mock
	private IRoomDao dao; /// class to be mocked

	@BeforeMethod(alwaysRun = true)
	public void injectDoubles() {
		MockitoAnnotations.initMocks(this); // This could be pulled up into a
											// shared base class
	}
	
	/*@Test
	public void testURLEncoding() throws UnsupportedEncodingException {
		String q = "random word £500 bank $";
		String url = "http://example.com/query?q=" + URLEncoder.encode(q, "UTF-8");
		assertEquals(url, "http://example.com/query?q=random+word+%C2%A3500+bank+%24");
		String url1 = "http://example.com/query?q=" + UriUtils.encodeQueryParam(q, "UTF-8");
		assertEquals("http://example.com/query?q=random%20word%20%C2%A3500%20bank%20$", url1);
	}*/

	@Test(groups = { "roomService" })
	public void lockValidRoomTest() {
		int validRoomId = 10;
		when(dao.lockRoom(1, 10)).thenReturn(100);
		TenantContext.setTenantId(1);
		int returnVal = roomService.lockRoom(validRoomId);
		assertEquals(returnVal, 100);
	}

	@Test(groups = { "roomService" })
	public void lockInvalidRoomTest() {
		int invalidRoomId = -100;
		when(dao.lockRoom(1, -100)).thenReturn(0);
		TenantContext.setTenantId(1);
		int returnVal = roomService.lockRoom(invalidRoomId);
		String name = "ganesh$%$%%@#gd  ghfdhgf hgdgfdfhj%@#%%%$%))*&#535676";
		String[] names = name.split(" ");
		System.out.println("here ->" + Arrays.toString(names));
		System.out.println("fn->" + names[0].replaceAll("[^a-zA-Z0-9]", ""));
		System.out.println("ln->" + names[1].replaceAll("[^a-zA-Z0-9]", ""));
		
		assertEquals(returnVal, 0);
	}
	
	public void updateRoomWithValidRoomId() {
		
	}
	
	public void updateRoomWithInvalidRoomId() {
		
	}
	
	public void updateRoomWithValidRoomObj() {
		
	}
	
	public void updateRoomWithNullRoomObj() {
		
	}

}

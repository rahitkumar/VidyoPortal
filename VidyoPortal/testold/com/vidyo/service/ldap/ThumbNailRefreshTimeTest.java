package com.vidyo.service.ldap;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.vidyo.framework.security.authentication.VidyoUserDetails;

public class ThumbNailRefreshTimeTest {
	boolean thumbNailRefreshTimeNotExpried=false;
	//
	String[] returnedAttrs = {"hi","thumbNail"};
	
	Date current=null;
	Date lastRefreshDate=null;
	Map<String,String> mapAttr=new HashMap<>();
	@Before
	public void setUp() throws Exception {
		thumbNailRefreshTimeNotExpried=false;
				
		  current=new Date();
		  Calendar currentC=Calendar.getInstance();
		  currentC.set(2016, Calendar.MAY, 13, 13, 46, 40);
		  current=currentC.getTime();
		  Calendar cal=Calendar.getInstance();
		  cal.set(2016, Calendar.MAY, 12, 13, 47, 40);
		  lastRefreshDate=cal.getTime();
		  mapAttr.put("phone1","202");
		  mapAttr.put("phone2","203");
		  mapAttr.put("phone4","204");
		  mapAttr.put("Thumbnail Photo","204");
	}

	/**
	 * this test will test against if refresh time is expired and we need to retrieve thumbnail. So map size should be same as initial
	 */
	@Test
	public void testThumbNailTimeExpired() {
		 thumbNailRefreshTimeNotExpried=TimeUnit.MILLISECONDS.toMinutes(Calendar.getInstance().getTime().getTime()- lastRefreshDate.getTime()) < VidyoUserDetails.REFRESH_INTERVAL_MINUTES*60;
		 int size=mapAttr.size();
			//removing thumbnail related attribute from ldap query if thumbNailRefreshTimeExpried is not expired.
		 Iterator<Map.Entry<String,String>> mapIterator= mapAttr.entrySet().iterator();
		 while(mapIterator.hasNext()){
			 Map.Entry<String,String> entry = mapIterator.next();
					if(thumbNailRefreshTimeNotExpried && "Thumbnail Photo".equalsIgnoreCase(entry.getKey())){
						mapIterator.remove();
					}
					
			}
		assertTrue(mapAttr.size()==size);
	}
	/**
	 * this test will test against if refresh time is not expired and we dont need to retrieve thumbnail. So thumbnail attribute value will be removed after map iteration, so map size should be less than initial value
	 */
	@Test
	public void testThumbNailTimeNotExpired() {
		 Calendar cal=Calendar.getInstance();
		 cal.setTimeInMillis(new Date().getTime()-100000);//almost 2 mins less than current time
		 lastRefreshDate=cal.getTime();
		 thumbNailRefreshTimeNotExpried=TimeUnit.MILLISECONDS.toMinutes(Calendar.getInstance().getTime().getTime()- lastRefreshDate.getTime()) < VidyoUserDetails.REFRESH_INTERVAL_MINUTES*60;
		 int size=mapAttr.size();
			//removing thumbnail related attribute from ldap query if thumbNailRefreshTimeExpried is not expired.
		 Iterator<Map.Entry<String,String>> mapIterator= mapAttr.entrySet().iterator();
		 while(mapIterator.hasNext()){
			 Map.Entry<String,String> entry = mapIterator.next();
					if(thumbNailRefreshTimeNotExpried && "Thumbnail Photo".equalsIgnoreCase(entry.getKey())){
						mapIterator.remove();
					}
					
			}
		 mapAttr.values().toArray(returnedAttrs);
		 System.out.println(" attributes sending to ldap "+ Arrays.toString(returnedAttrs));
		assertTrue(mapAttr.size()<size);
	}

}

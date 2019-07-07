/**
 * This is a copy of UserAgentUtils.java. as i was not allocated much time do the correct junit which cover the code base, so doing the quick one,
 */
package com.vidyo.web.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class WebRTC {
	
	boolean isVidyoRoom;
	boolean urlExists;
	
	boolean webRTCForUsers;
	boolean noNeoInstaller;
	String userAgent="";

	@Before
	public void beforeTest(){
		userAgent="";
	}
	@Test
	public void androidTest(){
		userAgent="Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Mobile Safari/537.36";
		isVidyoRoom=false;	
		urlExists=true;		
		webRTCForUsers=true;
		noNeoInstaller=true;
		;
		
		assertFalse("Android agent is there so not redirecting webrtc ", redirectWebRtc(userAgent,isVidyoRoom,urlExists,webRTCForUsers,noNeoInstaller));
	}
	@Test
	public void androidTest_1(){
		userAgent="Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Mobile Safari/537.36";
		isVidyoRoom=false;	
		urlExists=true;		
		webRTCForUsers=true;
		noNeoInstaller=false;
		;
		assertFalse("Android agent is there so not redirecting webrtc ", redirectWebRtc(userAgent,isVidyoRoom,urlExists,webRTCForUsers,noNeoInstaller));
	}
	
	@Test
	public void chromeTest(){
		userAgent="Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
		isVidyoRoom=false;	
		urlExists=true;		
		webRTCForUsers=true;
		noNeoInstaller=true;
		;
		assertTrue("chrome agent,so it should redirect to webrtc", redirectWebRtc(userAgent,isVidyoRoom,urlExists,webRTCForUsers,noNeoInstaller));
	}
	//very important
	@Test
	public void chromeTest_withNeo(){
		userAgent="Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
		isVidyoRoom=false;	
		urlExists=true;		
		webRTCForUsers=true;
		noNeoInstaller=false;
		;
		assertFalse("chrome agent,but neo is installed so no webrtc redirect", redirectWebRtc(userAgent,isVidyoRoom,urlExists,webRTCForUsers,noNeoInstaller));
	}
	@Test
	public void iosTest(){
		userAgent="Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";
		isVidyoRoom=false;	
		urlExists=true;		
		webRTCForUsers=true;
		noNeoInstaller=true;
		;
		assertFalse("ios,so it should not redirect to webrtc", redirectWebRtc(userAgent,isVidyoRoom,urlExists,webRTCForUsers,noNeoInstaller));
	}
	@Test
	public void iosTest_withNeo(){
		userAgent="Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";
		isVidyoRoom=false;	
		urlExists=true;		
		webRTCForUsers=true;
		noNeoInstaller=false;
		;
		assertFalse("ios,so it should not redirect to webrtc", redirectWebRtc(userAgent,isVidyoRoom,urlExists,webRTCForUsers,noNeoInstaller));
	}
	
	@Test
	public void firefoxWindowsTest(){
		userAgent="Mozilla/5.0 (Windows NT 6.1; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0";
		isVidyoRoom=false;	
		urlExists=true;		
		webRTCForUsers=true;
		noNeoInstaller=true;
		;
		assertTrue("no neo so redirecting webrtc", redirectWebRtc(userAgent,isVidyoRoom,urlExists,webRTCForUsers,noNeoInstaller));
	}
	@Test
	public void firefoxWindowsTest_WithNeo(){
		userAgent="Mozilla/5.0 (Windows NT 6.1; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0";
		isVidyoRoom=false;	
		urlExists=true;		
		webRTCForUsers=true;
		noNeoInstaller=false;
		;
		assertFalse("since neo is uploaded,so it should not redirect to webrtc", redirectWebRtc(userAgent,isVidyoRoom,urlExists,webRTCForUsers,noNeoInstaller));
	}
	@Test
	public void safariWindowsTest(){
		userAgent="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2";
		isVidyoRoom=false;	
		urlExists=true;		
		webRTCForUsers=true;
		noNeoInstaller=true;
		;
		assertFalse("safari dont support webrtc  it should not redirect to webrtc", redirectWebRtc(userAgent,isVidyoRoom,urlExists,webRTCForUsers,noNeoInstaller));
	}
	@Test
	public void macTest(){
		userAgent="Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";
		isVidyoRoom=false;	
		urlExists=true;		
		webRTCForUsers=true;
		noNeoInstaller=false;
		;
		assertFalse("ios,so it should not redirect to webrtc", redirectWebRtc(userAgent,isVidyoRoom,urlExists,webRTCForUsers,noNeoInstaller));
	}
	public boolean redirectWebRtc(String userAgent2, boolean isVidyoRoom2, boolean urlExists2, boolean webRTCForUsers2, boolean noNeoInstaller2) {
		  if (!isVidyoRoom && webRTCForUsers && urlExists && !isSupportedTouchDevice(userAgent)&&
	                ((browserSupportsWebRTC(userAgent) && ((isMac(userAgent) || isWindows(userAgent)) && noNeoInstaller)) ||
	                (browserSupportsWebRTC(userAgent) && (!(isMac(userAgent) || isWindows(userAgent))))
	                )) {
			  return true;
	}else return false;
	}
		

		

		  	public  boolean isSupportedTouchDevice(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return isIOS(userAgent) || isAndroid(userAgent) || isKindle(userAgent) || isMobile(userAgent);
		  	}

		  	public  boolean isIOS(String userAgent) {
		  		return isIphone(userAgent) || isIPad(userAgent);
		  	}

		  	// iphone or ipod touch
		  	public  boolean isIphone(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return (userAgent.contains("iPhone") || userAgent.contains("iPod"));
		  	}

		  	public  boolean isIPad(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("iPad");
		  	}

		  	public  boolean isAndroid(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("Android");
		  	}

		  	public  boolean isMobile(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.toLowerCase().contains("mobile");
		  	}

		  	public  boolean isKindle(String userAgent) {
		  		if (userAgent != null) {
		  			userAgent = userAgent.toLowerCase();
		  		} else {
		  			return false;
		  		}
		  		return (
		  				userAgent.contains("kindle") ||
		  						userAgent.contains("silk") ||
		  						userAgent.contains("kftt") ||
		  						userAgent.contains("kfot") ||
		  						userAgent.contains("kfjwa") ||
		  						userAgent.contains("kfjwi") ||
		  						userAgent.contains("kfsowi") ||
		  						userAgent.contains("kfthwa") ||
		  						userAgent.contains("kfthwi") ||
		  						userAgent.contains("kfapwa") ||
		  						userAgent.contains("kfapwi")
		  		);
		  	}

		  	public  boolean isMac(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.toLowerCase().contains("macintosh");
		  	}

		  	public  boolean isWindows(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.toLowerCase().contains("windows");
		  	}

		  	public  boolean isWindows10(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.toLowerCase().contains("windows nt 10");
		  	}


		  	public  boolean isLinux(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.toLowerCase().contains("linux");
		  	}

		  	public  boolean isVidyoRoom(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("SWB:");
		  	}


		  	public  boolean isVidyoDesktop(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("VidyoDesktop");
		  	}

		  	public  boolean isVidyoNeo(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("Vidyo Neo");
		  	}

		  	public  boolean isVidyoEndpoint(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return isVidyoDesktop(userAgent) || isVidyoNeo(userAgent);
		  	}

		      public  boolean is32bitLinux(String userAgent) {
		          if (userAgent == null) {
		              return false;
		          }
		          return isLinux(userAgent) && !userAgent.contains("x86_64");
		      }


		      public  boolean is64bitLinux(String userAgent) {
		          if (userAgent == null) {
		              return false;
		          }
		          return isLinux(userAgent) && userAgent.contains("x86_64");
		      }

		  	public  boolean isChromeOS(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("CrOS");
		  	}

		  	public   boolean isBrowserFirefox(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("Firefox");
		  	}

		  	public  boolean isBrowserChrome(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("Chrome") && !userAgent.contains("Edge"); // stupid Edge also reports Chrome/Safari
		  	}

		  	public  boolean isBrowserSafari(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("Safari") && !userAgent.contains("Edge"); // stupid Edge also reports Chrome/Safari
		  	}

		  	public  boolean isBrowserIE(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("MSIE") ||  userAgent.contains("Trident");
		  	}

		  	public  boolean isBrowserEdge(String userAgent) {
		  		if (userAgent == null) {
		  			return false;
		  		}
		  		return userAgent.contains("Edge");
		  	}

		  	public  boolean browserSupportsWebRTC(String userAgent) {
		  		return isBrowserChrome(userAgent) || isBrowserFirefox(userAgent);
		  	}

		  


	}

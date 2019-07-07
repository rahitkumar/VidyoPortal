package com.vidyo.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgentUtils {
	protected static final Logger logger = LoggerFactory.getLogger(UserAgentUtils.class.getName());

	public static boolean isSupportedTouchDevice(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return isIOS(userAgent) || isAndroid(userAgent) || isKindle(userAgent) || isMobile(userAgent);
	}

	public static boolean isIOS(String userAgent) {
		return isIphone(userAgent) || isIPad(userAgent);
	}

	// iphone or ipod touch
	public static boolean isIphone(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return (userAgent.contains("iPhone") || userAgent.contains("iPod"));
	}

	public static boolean isIPad(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("iPad");
	}

	public static boolean isAndroid(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("Android");
	}

	public static boolean isMobile(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.toLowerCase().contains("mobile");
	}

	public static boolean isKindle(String userAgent) {
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

	public static boolean isMac(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.toLowerCase().contains("macintosh");
	}

	public static boolean isWindows(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.toLowerCase().contains("windows");
	}

	public static boolean isWindows10(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.toLowerCase().contains("windows nt 10");
	}


	public static boolean isLinux(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.toLowerCase().contains("linux");
	}

	public static boolean isVidyoRoom(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("SWB:");
	}


	public static boolean isVidyoDesktop(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("VidyoDesktop");
	}

	public static boolean isVidyoNeo(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("Vidyo Neo");
	}

	public static boolean isVidyoEndpoint(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return isVidyoDesktop(userAgent) || isVidyoNeo(userAgent) || isVidyoConnect(userAgent);
	}
	
	public static boolean isVidyoConnect(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("VidyoConnect");
	}	

    public static boolean is32bitLinux(String userAgent) {
        if (userAgent == null) {
            return false;
        }
        return isLinux(userAgent) && !userAgent.contains("x86_64");
    }


    public static boolean is64bitLinux(String userAgent) {
        if (userAgent == null) {
            return false;
        }
        return isLinux(userAgent) && userAgent.contains("x86_64");
    }

	public static boolean isChromeOS(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("CrOS");
	}

	public static boolean isBrowserFirefox(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("Firefox");
	}

	public static boolean isBrowserChrome(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("Chrome") && !userAgent.contains("Edge"); // stupid Edge also reports Chrome/Safari
	}

	public static boolean isBrowserSafari(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("Safari") && !userAgent.contains("Edge"); // stupid Edge also reports Chrome/Safari
	}

	public static boolean isBrowserIE(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("MSIE") ||  userAgent.contains("Trident");
	}

	public static boolean isBrowserEdge(String userAgent) {
		if (userAgent == null) {
			return false;
		}
		return userAgent.contains("Edge");
	}

	public static boolean browserSupportsWebRTC(String userAgent) {
		return UserAgentUtils.isBrowserChrome(userAgent) || UserAgentUtils.isBrowserFirefox(userAgent);
	}

	public static boolean edgeSupportsWebRTC(String providedVersion, String baseVersion){
		boolean flag = false;
		try	{
			float pVersion = Float.valueOf(providedVersion.trim()).floatValue();
			float bVersion = Float.valueOf(baseVersion.trim()).floatValue();
			flag = pVersion >= bVersion;
		}
		catch (Exception exception) {
			logger.error("Exception while parsing the String to float:: " + exception.getMessage());
		}
		return flag;
	}

	public static String getBrowserChromeVersion(String userAgent) {
		Pattern p = Pattern.compile("Chrome/(.+) ");
		Matcher m = p.matcher(userAgent);
		// if we find a match, get the group
		if (m.find()){
			return m.group(1);
		}
		return null;
	}

	public static boolean isChomeVersionGreaterThan(String userAgent, int version) {
		String chromeVersion = getBrowserChromeVersion(userAgent);
		if(StringUtils.isEmpty(chromeVersion)){
			return false;
		}
		String browserVersion = chromeVersion.substring(0, chromeVersion.indexOf("."));
		return (Integer.parseInt(browserVersion) >= version);
	}

}

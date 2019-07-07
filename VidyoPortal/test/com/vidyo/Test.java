package com.vidyo;

import java.util.HashMap;
import java.util.*;
import java.util.Map;

import com.vidyo.bo.Proxy;

public class Test {

	public static void main(String[] args) {
		List<Proxy> proxies = new ArrayList<Proxy>();
		Proxy proxyObj = new Proxy();
		proxyObj.setProxyID(0);
		proxyObj.setProxyName("test0");
		proxies.add(proxyObj);
		proxyObj = new Proxy();
		proxyObj.setProxyID(1);
		proxyObj.setProxyName("test1");
		proxies.add(proxyObj);
		Map<Integer, String> proxiesMap = new HashMap<Integer, String>();
		proxies.forEach(proxy -> proxiesMap.put(proxy.getProxyID(), proxy.getProxyName()));
		proxiesMap.get(0);
	}

}

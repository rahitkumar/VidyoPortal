package com.vidyo.service;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.vidyo.bo.ConfigLogFile;
import com.vidyo.bo.ConfigProperty;


public interface IRouterConfigService {

	public String getRequestParameter(String name, HttpServletRequest request);
	public String stripBadCharacters(String field);

	public String getRouterVersion();
	public String getRouterType();
	public boolean isStandaloneRouter();

	public List<ConfigProperty> getRouterConfigProperties();
	public ConfigProperty getRouterConfigProperty(String name, List<ConfigProperty> props);
	public String updateRouterConfigProperty(String name, String value);

	public List<ConfigLogFile> getRouterConfigLogFiles();

	public boolean restart();
	public boolean shutdown();

	public void writeAuditHistory(String username, String ip, String message);
    public void writeAuditHistory(String message);

}

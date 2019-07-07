package com.vidyo.service;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;

import com.vidyo.bo.ConfigLogFile;
import com.vidyo.bo.ConfigProperty;
import com.vidyo.bo.SHA1;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import org.apache.commons.io.FileUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouterConfigServiceImpl implements IRouterConfigService {

	protected final Logger logger = LoggerFactory.getLogger(RouterConfigServiceImpl.class.getName());

	private MessageSource messages;

	private String tmpDirectory = "/opt/vidyo/temp/tomcat/";

	public String getTmpDirectory() {
		return tmpDirectory;
	}

	public void setTmpDirectory(String tmpDirectory) {
		this.tmpDirectory = tmpDirectory;
	}

	public void setMessages(MessageSource messages) {
		this.messages = messages;
	}

	public RouterConfigServiceImpl() {
		String historyFolder = "/var/log/vidyo";
		File historyFolderFile = new File(historyFolder);

		if (!historyFolderFile.exists()) {
			historyFolderFile.mkdir();
		} else {
			try {
				File[] listOfFiles = new File(historyFolder).listFiles();
				long halfYear = 180l * 24 * 60 * 60 * 1000;                         //180 days
				for (int i = 0; i < listOfFiles.length; i++) {
					File f = listOfFiles[i];
					if(f.isFile()) {
						long fileAge = new Date().getTime() - f.lastModified();
						if(fileAge > halfYear) {
							f.delete();
						}
					}
				}
			}
			catch(Exception e) {
				// Do nothing, even failed to delete the history files
			}
		}

	}

	public String getRequestParameter(String name, HttpServletRequest request){
		String ret = request.getParameter(name);
		if (ret == null) {
			ret = "";
		}
		ret = ret.trim();
		return ret;
	}

	public String stripBadCharacters(String field){
		String ret = "";
		if (field == null) {
			field = "";
		}
		field = field.trim();
		for (int i=0; i < field.length();i++) {
			if(     (field.charAt(i) != '<')&&
					(field.charAt(i) != '>')&&
					(field.charAt(i) != '~')&&
					(field.charAt(i) != '#')&&
					(field.charAt(i) != '$')&&
					(field.charAt(i) != '%')&&
					(field.charAt(i) != '^')&&
					(field.charAt(i) != '/')&&
					(field.charAt(i) != '\\')&&
					(field.charAt(i) != '(')&&
					(field.charAt(i) != ')')&&
					(field.charAt(i) != '?')&&
					(field.charAt(i) != '"')){
				ret += field.charAt(i);
			}
		}

		return ret;
	}

	public List<ConfigProperty> getRouterConfigProperties(){
		List<ConfigProperty> ret = new ArrayList<ConfigProperty>();

		try {
			FileInputStream fis = new FileInputStream("/opt/vidyo/vidyorouter2/localvrconfig");
			InputStreamReader in = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(in);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				int pipeIndex = line.indexOf("=");
				if(pipeIndex > -1){
					ConfigProperty prop = new ConfigProperty();
					String name = line.substring(0,pipeIndex);
					String value = line.substring(pipeIndex+1, line.length());

					prop.setName(name);
					prop.setValue(value);
					ret.add(prop);
				}
			}

			br.close();

		} catch (IOException ex) {
			logger.error("IO Exception: " + ex.getMessage());
		}

		return ret;
	}



	public ConfigProperty getRouterConfigProperty(String name, List<ConfigProperty> props){
		ConfigProperty ret = new ConfigProperty();
		if(props == null) return ret;
		for (int i=0; i < props.size(); i++) {
			ConfigProperty prop = props.get(i);
			if(name.equalsIgnoreCase(prop.getName())){
				ret = prop;
			}
		}
		return ret;
	}

	public String updateRouterConfigProperty(String name, String value){
		String ret = "OK";

		List<ConfigProperty> props = this.getRouterConfigProperties();
		String accum = "";
		for(int i=0; i < props.size(); i++){
			ConfigProperty prop = props.get(i);
			if(name.equals(prop.getName())){
				accum += prop.getName() + "=" + value;
			} else {
				accum += prop.getName() + "=" + prop.getValue();
			}
			if(i < props.size()-1){
				accum += "\n";
			}
		}

		try {
			File file = new File(getTmpDirectory() + "localvrconfg.tmp");
			FileUtils.writeStringToFile(file, accum);

			ShellCapture capture = ShellExecutor.execute("sudo -n /opt/vidyo/bin/vidyo_file_utils.sh COPY_VR_CONFIG localvrconfg.tmp");
			if (capture.isSuccessExitCode()) {
				return ret;
			}
		} catch (IOException e) {
			logger.debug("IO Exception updateRouterConfigProperty(): " + e.getMessage());
		} catch (ShellExecutorException see) {
			logger.debug("Shell Exception updateRouterConfigProperty(): " + see.getMessage());
		}

		return messages.getMessage("super.config.router.update.property.message.error", null, LocaleContextHolder.getLocale());
	}

	public List<ConfigLogFile> getRouterConfigLogFiles(){
		List<ConfigLogFile> ret = new ArrayList<ConfigLogFile>();

		File dir = new File("/opt/vidyo/vidyorouter2");
		String[] children = dir.list();
		String filePath = "";

		if (children == null) {
			// Either dir does not exist or is not a directory
		} else {
			for (int i=0; i<children.length; i++) {
				// Get filename of file or directory
				String fName = children[i];
				if(fName.startsWith("wd.log") || fName.startsWith("vr2.log") || fName.startsWith("apache2")){
					File flog = new File("/opt/vidyo/vidyorouter2/" + fName);
					ConfigLogFile cfile = new ConfigLogFile();
					long length = flog.length();
					long lastM  = flog.lastModified();
					Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
					cal.setTimeInMillis(lastM);

					cfile.setFileName(fName);
					cfile.setLastModified(getDateFormatted(cal));
					cfile.setLastModifiedLong(lastM);
					cfile.setLength(length);
					ret.add(cfile);
				}

			}
		}

		Collections.sort(ret);

		return ret;
	}

	public String getDateFormatted(Calendar cal){
		String ret = "";

		ret = cal.get(Calendar.YEAR) + "-";

		int m = cal.get(Calendar.MONTH) + 1;
		String mm = "";
		if(m < 10){ mm = "0" + m;}
		else {mm = m + "";}
		ret = ret + mm + "-";

		int d  = cal.get(Calendar.DAY_OF_MONTH);
		String dd = "";
		if(d < 10){ dd = "0" + d;}
		else {dd = d + "";}
		ret = ret + dd + " ";

		int h = cal.get(Calendar.HOUR_OF_DAY);
		String hh = "";
		if(h < 10){ hh = "0" + h;}
		else {hh = h + "";}
		ret = ret + hh + ":";

		m = cal.get(Calendar.MINUTE);
		if(m < 10){ mm = "0" + m;}
		else {mm = m + "";}
		ret = ret + mm + ":";

		int s = cal.get(Calendar.SECOND);
		String ss = "";
		if(s < 10){ ss = "0" + s;}
		else {ss = s + "";}
		ret = ret + ss + " GMT";

		return ret;
	}

	public String getRouterVersion(){

		File versionFile = new File("/opt/vidyo/VC2_VERSION");
		String fContent = "";

		try {
			FileInputStream fstream = new FileInputStream(versionFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null)   {
				fContent = strLine;
			}
			in.close();
		} catch (IOException e) {
			logger.debug("Exception while getRouterVersion(): " + e.getMessage());
		}

		return fContent.trim();
	}


	public boolean restart() {
		logger.debug("system reboot request");
		String command = "sudo -n /opt/vidyo/bin/vidyo_server.sh REBOOT_SYSTEM";
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture.isErrorExitCode()) {
				this.writeAuditHistory("Reboot failure");
			} else {
				this.writeAuditHistory("Reboot success");
				return true;
			}
		} catch (ShellExecutorException e) {
			this.writeAuditHistory("Reboot failure");
			logger.debug("Exception while restart: " + e.getMessage());
		}
		return false;
	}

	public boolean shutdown() {
		logger.debug("system shutdown request");
		String command = "sudo -n /opt/vidyo/bin/vidyo_server.sh SHUTDOWN_SYSTEM";
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			if (capture.isErrorExitCode()) {
				this.writeAuditHistory("Shutdown failure");
			} else {
				this.writeAuditHistory("Shutdown success");
				return true;
			}
		} catch (ShellExecutorException e) {
			this.writeAuditHistory("Shutdown failure");
			logger.debug("Exception while shutdown: " + e.getMessage());
		}
		return false;
	}

	public String getRouterType(){
		String vidyoRouterType = "";
		if (isLinux64Bit()) {
			vidyoRouterType = "64-bit";
		} else {
			vidyoRouterType = "32-bit";
		}
		return vidyoRouterType;
	}


	private boolean isLinux64Bit(){
		boolean ret = false;
		String command = "uname -m";
		try {
			ShellCapture capture = ShellExecutor.execute(command);
			String s = capture.getStdOut();
			logger.debug(s);
			if(s.contains("x86_64")){
				ret = true;
			}
		} catch (ShellExecutorException e) {
			logger.debug("Exception while isLinux64Bit: " + e.getMessage());
		}
		return ret;
	}


	public boolean isStandaloneRouter() {
		File vrDir = new File("/opt/vidyo/vidyorouter2");
		File vmDir = new File("/opt/vidyo/vm");
		return vrDir.exists() && !vmDir.exists();
	}

	public void writeAuditHistory(String username, String ip, String message) {
		String historyFolder = "/var/log/vidyo";
		String historyFilename = historyFolder+"/vr_history.";
		Date now = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String fn = historyFilename+sdf.format(now)+".log";
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(fn, true));
			out.write(sdf.format(now)+" | "+ username +" | "+ ip +" | "+ message + "\n");
		} catch (IOException e) {
			logger.error("IOException: " + e.getMessage());
		} finally {
			if (out != null) {
				try { out.close(); } catch (IOException ioe) { logger.error("IOException: " + ioe.getMessage());}
			}
		}
	}

	public void writeAuditHistory(String message) {
		String username= "?";
		String ip= "?";

		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			Authentication authentication = securityContext.getAuthentication();
			if (authentication != null) {
				username = authentication.getName();
			}
		}

		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (servletRequestAttributes != null) {
			HttpServletRequest request = servletRequestAttributes.getRequest();
			ip = request.getRemoteAddr();
		}

		writeAuditHistory(username, ip, message);
	}

}

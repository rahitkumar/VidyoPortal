package com.vidyo.framework.executors;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ShellCapture {
	private int statusCode = -1;
	private List<String> output = new ArrayList<String>();
	private List<String> error = new ArrayList<String>();

	public int getExitCode() {
		return statusCode;
	}

	public void setExitCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public  List<String> getStdOutLines() {
		return output;
	}

	public void setStdOutLines(List<String> output) {
		this.output = output;
	}
	
	public String getStdOut() {
		return getStdOut("\n");
	}
	
	public String getStdOut(String delimiter) {
		return join(output, delimiter);
	}
	
	public  List<String> getStdErrLines() {
		return error;
	}

	public void setStdErrLines(List<String> error) {
		this.error = error;
	}
	
	public String getStdErr() {
		return getStdErr("\n");
	}
	
	public String getStdErr(String delimiter) {
		return join(error, delimiter);
	}

	private String join(List<String> stringList, String delimiter) {
		if (delimiter == null) {
			delimiter = "\n";
		}
		if (stringList.size() > 0) {
			return StringUtils.join(stringList.toArray(), delimiter);
		}
		return "";
	}

	public boolean isSuccessExitCode() {
		return getExitCode() == 0;
	}

	public boolean isErrorExitCode() {
		return !isSuccessExitCode();
	}

	public boolean isStdOutOutput() {
		return getStdOutLines().size()==0;
	}

	public boolean isStdErrOutput() {
		return getStdErrLines().size()==0;
	}
}

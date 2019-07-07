package com.vidyo.framework.executors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamGobbler extends Thread {
	protected final Logger logger = LoggerFactory.getLogger(StreamGobbler.class.getName());

	public static enum StreamType {
		STDOUT, STDERR
	};

	private InputStream is;
	private StreamType type;
	private List<String> lines = new ArrayList<String>();

	public List<String> getOutputContent() {
		return lines;
	}

	public StreamGobbler(InputStream is, StreamType type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
				logger.debug(type + ">" + line);
			}
		} catch (IOException ioe) {
			logger.error("IOException", ioe);
			ioe.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
				}
			}
			;
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
			;
		}
	}
}

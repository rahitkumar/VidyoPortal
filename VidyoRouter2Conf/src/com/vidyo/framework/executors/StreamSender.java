package com.vidyo.framework.executors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamSender extends Thread {
    protected final Logger logger = LoggerFactory.getLogger(StreamSender.class.getName());

    public static enum StreamType {
        STDIN
    };

    private OutputStream os;
    private StreamType type;
    private List<String> lines = new ArrayList<String>();

    public void setInputContent(List<String> lines) {
        this.lines = lines;
    }

    public StreamSender(OutputStream os, StreamType type) {
        this.os = os;
        this.type = type;
    }

    public void run() {
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        try {
            osr = new OutputStreamWriter(os);
            bw = new BufferedWriter(osr);
            for (String line : lines) {
                bw.write(line);
                bw.write("\n");
                bw.flush();
                logger.debug(type + ">" + line);
            }
            bw.flush();
            bw.close();
        } catch (IOException ioe) {
            logger.error("IOException", ioe);
            ioe.printStackTrace();
        } finally {
            if (osr != null) {
                try {
                    osr.close();
                } catch (IOException e) {
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

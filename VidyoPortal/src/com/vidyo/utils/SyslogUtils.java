package com.vidyo.utils;


import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import org.apache.commons.lang.StringUtils;
import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.message.structured.StructuredSyslogMessage;

import java.util.HashMap;
import java.util.Map;

public class SyslogUtils {

    private static SyslogIF syslog = Syslog.getInstance("unix_syslog"); // this better be thread safe

    public static void auditLog(Map<String, String> data, String message) {
        log(data, "audit", message);
    }

    /**
     * The structured data, the type of the log, and the message in the log if any
     * @param data
     * @param type
     * @param message
     */
    public static void log(Map<String, String> data, String type, String message) {
        Map outer = new HashMap();
        outer.put(type, data);
        StructuredSyslogMessage syslogMesage
                = new StructuredSyslogMessage("VidyoPortal", outer, message);
        syslog.info(syslogMesage);
        syslog.flush();

    }

    /**
     * an "expensive" version that calls the system logger command, but has no dependencies
     * @param data
     * @param type
     * @param message
     */
    public static void loggerLog(Map<String, String> data, String type, String message) {
        StringBuffer msg = new StringBuffer("VidyoPortal [");
        msg.append(type);
        String value;
        for (String key : data.keySet()) {
            value = data.get(key);
            msg.append(" ").append(key).append("=");
            if (value == null) {
                value="";
            }
            msg.append("\"").append(value.replace("\\", "\\\\").replace("\"", "\\\"")).append("\"");
        }
        msg.append("]");
        if (!StringUtils.isBlank(message)) {
            msg.append(" ").append(message);
        }
        try {
            ShellCapture capture = ShellExecutor.execute("logger " + msg.toString());
        } catch (ShellExecutorException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Map<String,String> data = new HashMap<String, String>();
        data.put("user", "super");
        data.put("action", "login");
        SyslogUtils.log(data, "audit", "audit log");

        SyslogUtils.log(data, "audit", "audit log");
    }
}

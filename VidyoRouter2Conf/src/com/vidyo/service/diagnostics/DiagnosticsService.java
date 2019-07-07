package com.vidyo.service.diagnostics;

import com.vidyo.bo.DiagnosticReport;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiagnosticsService implements IDiagnosticsService {

    public static final String DIAGNOSTICS_SCRIPT = "/opt/vidyo/app/diagnostic_tool/run_diagnostics.sh";

    @Override
    public boolean runDiagnostics() {
        try {
            String[] cmd = new String[] {"/bin/bash", "-c", "sudo -n " + DIAGNOSTICS_SCRIPT + " > /dev/null 2>&1 &"}; // background
            ShellCapture capture = ShellExecutor.execute(cmd);
            return capture.isSuccessExitCode();
        } catch (ShellExecutorException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<DiagnosticReport> getDiagnosticReports() {
        String[] cmd = new String[] {"/bin/bash", "-c", "cd /opt/vidyo/logs/diagnostics && ls -t net_diagnostic*.txt | head -5"};

        List<DiagnosticReport> reports = new ArrayList<DiagnosticReport>();
        List<String> fileNames = new ArrayList<String>();

        try {
            ShellCapture capture = ShellExecutor.execute(cmd);
            fileNames =  capture.getStdOutLines();
        } catch (ShellExecutorException e) {
            e.printStackTrace();
        }

        if (fileNames != null & fileNames.size() > 0) {
            for (String fileName : fileNames) {
                File file = new File("/opt/vidyo/logs/diagnostics/" + fileName);
                String dateStr = fileName.substring(15,30);
                dateStr = dateStr.replace("T", " ");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hhmmss");
                Date d  = new Date();
                try {
                 d = sdf.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                DiagnosticReport report = new DiagnosticReport(fileName, sdf2.format(d));
                reports.add(report);
            }
        }

        return reports;
    }

    @Override
    public String getDiagnosticReport(String reportFileName) {
        if (reportFileName == null) {
            return "";
        }

        reportFileName = reportFileName.trim();

        if (reportFileName.contains("..")) {
            return "";
        }
        if (reportFileName.contains("/")) {
            return "";
        }

        if (reportFileName.length() == 34 &&
                reportFileName.startsWith("net_diagnostic_") &&
                reportFileName.endsWith(".txt")) {

            try {
                File report = new File("/opt/vidyo/logs/diagnostics/" + reportFileName);
                return FileUtils.readFileToString(report);
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        return "";
    }


}

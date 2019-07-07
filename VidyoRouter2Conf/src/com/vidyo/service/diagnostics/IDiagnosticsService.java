package com.vidyo.service.diagnostics;

import com.vidyo.bo.DiagnosticReport;

import java.util.List;

public interface IDiagnosticsService {
    public boolean runDiagnostics();
    public List<DiagnosticReport> getDiagnosticReports();
    public String getDiagnosticReport(String reportFileName);
}

package com.vidyo.service.installation;

import java.io.IOException;
import java.util.List;

import com.vidyo.bo.InstallationReport;

public interface InstallationService {
	
	List<InstallationReport> getInstallationReportList() throws IOException;
	String getInstalltionLogPath();
	String getInstalltionLogDatedPath();
	List<InstallationReport> getInstalledPatches() throws IOException;

}

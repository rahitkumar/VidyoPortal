package com.vidyo.service.installation;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.InstallationReport;
import org.springframework.stereotype.Component;

@Component
public class InstallationServiceImpl implements InstallationService {

	protected static final Logger logger = LoggerFactory.getLogger(InstallationServiceImpl.class);
	
	private static final String LOG_FILE_DIRECTORY_DATED = "/opt/vidyo/logs/install";
	private static final String LOG_FILE_DIRECTORY = "/opt/vidyo/logs";
	private static final String LOG_FILE_PATTERN = "install*.log";
	
	private static final String INSTALLED_PATCHES_DIR = "/opt/vidyo/vidyo_updates";
	
	@Override
	public List<InstallationReport> getInstallationReportList() throws IOException {
		if(logger.isDebugEnabled()) {
			logger.debug("Entering getInstallationReportList");
		}
		List<InstallationReport> retVal = new ArrayList<>();
		
		Path path = FileSystems.getDefault().getPath(LOG_FILE_DIRECTORY);
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, LOG_FILE_PATTERN)){

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
			Date lastModified;
			for(Path p : ds) {
				BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);
				if(attrs.isRegularFile()) {
					lastModified= new Date(attrs.lastModifiedTime().toMillis());
					InstallationReport ir = new InstallationReport(p.getFileName().toString(), sdf.format(lastModified));
					
					retVal.add(ir);
				}
			}
		} catch (IOException e) {
			logger.error("IOException: " + e.getMessage());
		}

		path = FileSystems.getDefault().getPath(LOG_FILE_DIRECTORY_DATED);
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, LOG_FILE_PATTERN)){

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
			Date lastModified;
			for(Path p : ds) {
				BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);
				if(attrs.isRegularFile()) {
					lastModified= new Date(attrs.lastModifiedTime().toMillis());
					InstallationReport ir = new InstallationReport(p.getFileName().toString(), sdf.format(lastModified));

					retVal.add(ir);
				}
			}
		} catch (IOException e) {
			logger.error("IOException: " + e.getMessage());
		}
		
		Collections.sort(retVal);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Exiting getInstallationReportList");
		}

		return retVal;
	}

	@Override
	public String getInstalltionLogPath() {
		return LOG_FILE_DIRECTORY;
	}

	@Override
	public String getInstalltionLogDatedPath() {
		return LOG_FILE_DIRECTORY_DATED;
	}

	@Override
	public List<InstallationReport> getInstalledPatches() throws IOException {
		if(logger.isDebugEnabled()) {
			logger.debug("Entering getInstalledPatches");
		}
		
		List<InstallationReport> installationReports = new ArrayList<>();
		
		Path path = FileSystems.getDefault().getPath(INSTALLED_PATCHES_DIR);
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, "*")){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
			Date lastModified;
			for(Path p : ds) {
				BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);

				if(attrs.isRegularFile() && attrs.size() == 0) {
					lastModified= new Date(attrs.lastModifiedTime().toMillis());
					InstallationReport ir = new InstallationReport(p.getFileName().toString(), sdf.format(lastModified));
					
					installationReports.add(ir);
				}
			}			
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}
		
		Collections.sort(installationReports);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Entering getInstalledPatches");
		}
		return installationReports;
	}

}

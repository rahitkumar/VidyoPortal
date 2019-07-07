package com.vidyo.framework.system;

import com.vidyo.service.LicensingService;
import com.vidyo.service.cache.CacheAdministrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

/**
 * This class is only used by super app. It starts a thread to watch for a file "/home/supertc/CLEAR_CACHES"
 * and if seen, clears caches.
 *
 */
public class ClearCachesFileWatcher implements Runnable {
    protected final Logger logger = LoggerFactory.getLogger(ClearCachesFileWatcher.class.getName());

    private String clearCachesWatchDirectory = "/home/tomcat";
    private String clearCachesWatchFileName = "CLEAR_CACHES";
    private String licenseCacheFilePath = "/opt/vidyo/portal2/license/license.txt";


    private CacheAdministrationService cacheAdministrationService;
    private LicensingService licensingService;

    public void setCacheAdministrationService(CacheAdministrationService cacheAdministrationService) {
        this.cacheAdministrationService = cacheAdministrationService;
    }

    public String getClearCachesWatchDirectory() {
        return clearCachesWatchDirectory;
    }

    public void setClearCachesWatchDirectory(String clearCachesWatchDirectory) {
        this.clearCachesWatchDirectory = clearCachesWatchDirectory;
    }

    public String getClearCachesWatchFileName() {
        return clearCachesWatchFileName;
    }

    public void setClearCachesWatchFileName(String clearCachesWatchFileName) {
        this.clearCachesWatchFileName = clearCachesWatchFileName;
    }

    public String getClearCachesWatchFullFilePath() {
        return getClearCachesWatchDirectory() + "/" + getClearCachesWatchFileName();
    }

    public String getLicenseCacheFilePath() {
        return licenseCacheFilePath;
    }

    public void setLicenseCacheFilePath(String path) {
        this.licenseCacheFilePath = path;
    }

    public void setLicensingService(LicensingService licensingService) {
        this.licensingService = licensingService;
    }

    @Override
    public void run() {

        File existingClearCachesFile = new File(getClearCachesWatchFullFilePath());
        if (existingClearCachesFile.exists()) {
            logger.warn("Found pre-existing " + getClearCachesWatchFullFilePath() + " file.");
            this.cacheAdministrationService.clearCaches();
            if (!existingClearCachesFile.delete()) {
                logger.error("Failed to delete pre-existing CLEAR_CACHES file.");
            }
        }

        Path dir = Paths.get(getClearCachesWatchDirectory());
        WatchEvent.Kind<?>[] events = {StandardWatchEventKinds.ENTRY_CREATE};
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            dir.register(watcher, events);

            try {
                while (!Thread.currentThread().isInterrupted()) {

                    WatchKey watchKey = watcher.take();
                    if (watchKey.isValid()) {
                        for (WatchEvent<?> event : watchKey.pollEvents()) {
                            WatchEvent.Kind kind = event.kind();
                            if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                                Path filename = ev.context();
                                if (filename != null && getClearCachesWatchFileName().equals(filename.toString())) {
                                    // delete license cache file
                                    try {
                                        Files.deleteIfExists(Paths.get(getLicenseCacheFilePath()));
                                    } catch (IOException ioe) {
                                        logger.error("Error deleting license cache file: " + ioe.getMessage());
                                    }

                                    // delete ehcaches
                                    logger.warn("Clearing all app caches (detected " + getClearCachesWatchFullFilePath() + ")");
                                    this.cacheAdministrationService.clearCaches();

                                    // fill license cache
                                    this.licensingService.getSystemLicenseDetails();

                                    // delete watch file
                                    try {
                                        Files.deleteIfExists(Paths.get(getClearCachesWatchFullFilePath()));
                                    } catch (IOException ioe) {
                                        logger.error("Error clear caches watch file: " + ioe.getMessage());
                                    }
                                }
                            }
                        }
                    }

                    if (!watchKey.reset()) {
                        logger.error("Invalid watch key encountered, exiting. Cannot find: " + getClearCachesWatchDirectory());
                        break;
                    }
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } finally {
                watcher.close();
            }
        } catch (IOException ioe) {
            logger.error("IOException in ClearCachesFileWatcher: " + ioe.getMessage());
        }

    }

}

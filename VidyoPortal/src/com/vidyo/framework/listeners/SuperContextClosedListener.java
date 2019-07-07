package com.vidyo.framework.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class SuperContextClosedListener implements ApplicationListener<ContextClosedEvent> {

    private final Logger logger = LoggerFactory.getLogger(SuperContextClosedListener.class);

    private Thread clearCachesFileWatcherThread;

    public void setClearCachesFileWatcherThread(Thread clearCachesFileWatcherThread) {
        this.clearCachesFileWatcherThread = clearCachesFileWatcherThread;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("Stopping ClearCachesFileWatcherThread");
        clearCachesFileWatcherThread.interrupt();
    }
}

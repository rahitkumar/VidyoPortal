package com.vidyo.service.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class CacheAdministrationServiceImpl implements CacheAdministrationService {

    protected final Logger logger = LoggerFactory.getLogger(CacheAdministrationServiceImpl.class.getName());

    private CacheManager cacheManager;

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void clearCaches() {
        logger.warn("Clearing all caches");
        String[] cacheNames = cacheManager.getCacheNames();
        for (String cacheName : Arrays.asList(cacheNames)) {
            cacheManager.getCache(cacheName).removeAll();
            logger.warn("  Cleared cache: " + cacheName);
        }
    }

    @Override
    public void clearCache(String cacheName) {
        if (cacheName == null) {
            return;
        }
        List<String> caches = Arrays.asList(cacheManager.getCacheNames());
        if (!caches.contains(cacheName)) {
            logger.error("Unknown cache: " + cacheName);
        } else {
            Cache cache = cacheManager.getCache(cacheName);
            logger.debug("Clearing cache: " + cache);
            cache.removeAll();
        }
    }

    @Override
    public void updateCacheByName(String cacheName, Object key, Object value) {
        logger.debug("Updating the cache " + cacheName + " with value ::" + value);
        Cache cache = cacheManager.getCache(cacheName);
        cache.put(new Element(key,value));
    }

    @Override
    public Object getValueFromCache(String cacheName, Object key) {
        logger.debug("Getting the value " + cacheName + " for key ::" + key);
        Cache cache = cacheManager.getCache(cacheName);
        Element element = cache.get(key);
        return (element != null) ? element.getObjectValue(): null;
    }
}

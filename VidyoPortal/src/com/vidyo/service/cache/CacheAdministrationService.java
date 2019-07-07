package com.vidyo.service.cache;

public interface CacheAdministrationService {

    public void clearCaches();

    public void clearCache(String cacheName);

    public void updateCacheByName(String cacheName, Object key,  Object value);

    public Object getValueFromCache(String cacheName, Object key);
}

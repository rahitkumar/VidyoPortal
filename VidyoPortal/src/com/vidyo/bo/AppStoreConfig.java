package com.vidyo.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by pkumar on 11/05/17.
 *
 * Class to contain all the config entries for extenral Urls.
 */
@Component
public class AppStoreConfig {

    private final String appleStoreNeomobile = "itms-apps://itunes.apple.com/us/app/vidyo-neo/id1103823278?ls=1&amp;mt=8" ;
    private final   String googleStoreNeomobile = "https://play.google.com/store/apps/details?id=com.vidyo.neomobile";

    protected static final Logger logger = LoggerFactory.getLogger(AppStoreConfig.class.getName());

    public String getAppleStoreNeomobile() {
        return appleStoreNeomobile;
    }

    public String getGoogleStoreNeomobile() {
        return googleStoreNeomobile;
    }

}
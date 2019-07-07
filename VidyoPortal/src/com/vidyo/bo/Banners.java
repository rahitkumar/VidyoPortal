package com.vidyo.bo;

import java.io.Serializable;

public class Banners implements Serializable {
    boolean showLoginBanner;
    boolean showWelcomeBanner;
    String welcomeBanner;
    String loginBanner;

    public boolean getShowLoginBanner() {
        return showLoginBanner;
    }

    public void setShowLoginBanner(boolean value) {
        this.showLoginBanner = value;
    }

    public boolean getShowWelcomeBanner() {
        return showWelcomeBanner;
    }

    public void setShowWelcomeBanner(boolean value) {
        this.showWelcomeBanner = value;
    }

    public String getWelcomeBanner() {
        return welcomeBanner;
    }

    public void setWelcomeBanner(String value) {
        this.welcomeBanner = value;
    }

    public String getLoginBanner() {
        return loginBanner;
    }

    public void setLoginBanner(String value) {
        this.loginBanner = value;
    }
}

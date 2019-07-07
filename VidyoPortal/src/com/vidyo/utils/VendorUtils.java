package com.vidyo.utils;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;

public class VendorUtils {
    private static Boolean DISA_VENDOR = null;
    private static Boolean ENABLE_BETA_FEATURES = null;
    private static Boolean ROOMS_LOCKED_BY_DEFAULT = null;
    private static Boolean VIDYO_CLOUD = null;
    private static String VENDOR_ID = null;
    private static Boolean FORCE_NEO_FLOW = null;
    private static Boolean FORCE_NEO_VIDYOWEB = null;
    private static Boolean MODERATED_CONFERENCE = null;

    /**
     * Returns whether this machine is a DISA build.
     * Only correct if DISA file not changed after startup
     * (restarting portal will cause the DISA file to be re-read).
     *
     * @return true if DISA machine / false if not
     */
    public static boolean isDISA() {
        // only read once! restart the webapp to read it again!
        if (DISA_VENDOR == null) {
            File disaVendorFlag = new File("/opt/vidyo/Vendor/DISA");
            if (disaVendorFlag.exists()) {
                DISA_VENDOR = Boolean.TRUE;
            } else {
                DISA_VENDOR = Boolean.FALSE;
            }
        }
        return DISA_VENDOR.booleanValue();
    }

    /**
     * Returns whether this portal should display beta features.
     * Portal restart required to have this value re-read.
     *
     * @return true if beta features should be available to portal / false if not
     */
    public static boolean isBetaFeatureEnabled() {
        // only read once! restart the webapp to read it again!
        if (ENABLE_BETA_FEATURES == null) {
            File betaFeaturesMarkerFile = new File("/opt/vidyo/Vendor/ENABLE_BETA_FEATURES");
            if (betaFeaturesMarkerFile.exists()) {
                ENABLE_BETA_FEATURES = Boolean.TRUE;
            } else {
                ENABLE_BETA_FEATURES = Boolean.FALSE;
            }
        }
        return ENABLE_BETA_FEATURES.booleanValue();
    }

    /**
     * Returns the vendor id for this machine.
     * May return null if there was an error reading the vendor id file.
     * Only correct if vendor id file is not changed after startup
     * (restarting portal will cause the vendor id file to be re-read).
     *
     * @return id of vendor / null if vendor id could not be determined
     */
    public static String getVendorId() {
        // only read once! restart the webapp to read it again!
        if (VENDOR_ID == null) {
            File vendorFile = new File("/opt/vidyo/Vendor/vendor_id");
            if (vendorFile.exists()) {
                try {
                    VENDOR_ID = FileUtils.readFileToString(vendorFile);
                    if (!StringUtils.isBlank(VENDOR_ID)) {
                        VENDOR_ID = VENDOR_ID.trim();
                    }
                } catch (IOException e) {
                    // vendor_id remains null, so we try again next time
                    e.printStackTrace();
                }
            }
        }
        return VENDOR_ID;
    }

    /**
     * Returns whether this portal should display lock all rooms by default.
     * Portal restart required to have this value re-read.
     *
     * @return true if rooms are kept locked by default, false if not
     */
    public static boolean isRoomsLockedByDefault() {
        // only read once! restart the webapp to read it again!
        if (ROOMS_LOCKED_BY_DEFAULT == null) {
            File roomsLockedByDefault = new File("/opt/vidyo/Vendor/ROOMS_LOCKED_BY_DEFAULT");
            if (roomsLockedByDefault.exists()) {
                ROOMS_LOCKED_BY_DEFAULT = Boolean.TRUE;
            } else {
                ROOMS_LOCKED_BY_DEFAULT = Boolean.FALSE;
            }
        }
        return ROOMS_LOCKED_BY_DEFAULT.booleanValue();
    }

    public static boolean isVidyoCloud() {
        // only read once! restart the webapp to read it again!
        if (VIDYO_CLOUD == null) {
            File vidyoCloudFlag = new File("/opt/vidyo/Vendor/VIDYO_CLOUD");
            if (vidyoCloudFlag.exists()) {
                VIDYO_CLOUD = Boolean.TRUE;
            } else {
                VIDYO_CLOUD = Boolean.FALSE;
            }
        }
        return VIDYO_CLOUD.booleanValue();
    }

    /**
     * Added for use-case when a customer has no installer, but want to use Neo room-link handling flow
     * instead of UVD flow.
     *
     */
    public static boolean isForceNeoFlow() {
        // only read once! restart the webapp to read it again!
        if (FORCE_NEO_FLOW == null) {
            File forceNeoFlowFlag = new File("/opt/vidyo/Vendor/FORCE_NEO_FLOW");
            if (forceNeoFlowFlag.exists()) {
                FORCE_NEO_FLOW = Boolean.TRUE;
            } else {
                FORCE_NEO_FLOW = Boolean.FALSE;
            }
        }
        return FORCE_NEO_FLOW.booleanValue();
    }

    public static boolean isForceNeoVidyoWeb() {
        // only read once! restart the webapp to read it again!
        if (FORCE_NEO_VIDYOWEB == null) {
            File forceVidyoNeoWebFlag = new File("/opt/vidyo/Vendor/FORCE_NEO_VIDYOWEB");
            if (forceVidyoNeoWebFlag.exists()) {
                FORCE_NEO_VIDYOWEB = Boolean.TRUE;
            } else {
                FORCE_NEO_VIDYOWEB = Boolean.FALSE;
            }
        }
        return FORCE_NEO_VIDYOWEB.booleanValue();
    }

    /**
     * Returns whether this portal has moderated conference [control tile shows
     * in the endpoint] feature enabled or not. The flag will be planted by
     * .vidyo
     *
     * @return true if ModeratedConference is allowed, false if not
     */
    public static boolean isModeratedConferenceAllowed() {
        if (MODERATED_CONFERENCE == null) {
            File moderatedConfFlag = new File("/opt/vidyo/Vendor/MODERATED_CONFERENCE");
            if (moderatedConfFlag.exists()) {
                MODERATED_CONFERENCE = Boolean.TRUE;
            } else {
                MODERATED_CONFERENCE = Boolean.FALSE;
            }
        }
        return MODERATED_CONFERENCE.booleanValue();
    }

    // for testing
    public static void main(String[] args) {
        System.out.println("----------------");
        System.out.println("isDISA   [" + VendorUtils.isDISA() + "]");
        System.out.println("vendorId [" + VendorUtils.getVendorId() + "]");
        System.out.println("betaFeatures [" + VendorUtils.isBetaFeatureEnabled() + "]");
        System.out.println("vidyoCloud [" + VendorUtils.isVidyoCloud() + "]");
        System.out.println("----------------");
    }


}

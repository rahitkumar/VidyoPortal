package com.vidyo.utils;


import java.io.File;

public class VendorUtils {
    private static Boolean DISA_VENDOR = null;
    private static Boolean FACEBOOK_VENDOR = null;
	private static Boolean ENABLE_BETA_FEATURES = null;
	private static Boolean ROOMS_LOCKED_BY_DEFAULT = null;
    private static String VENDOR_ID = null;

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


}

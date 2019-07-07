package com.vidyo.framework.security.authentication;


import com.vidyo.framework.security.utils.SHA1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 *  Should be replaced with more modern password encoding algorithms, or at least continue SHA-1 but add salt for it.
 *  Preserved for legacy reason, upgrade path should be carefully planned.  Anyone who would like to delete - please
 *  refer to tickets VPTL-9384 and VPTL-9388 and ensure that scenarios from those tickets remains working.
 */
@Deprecated
public class CustomSHAPasswordEncoder implements PasswordEncoder {

    protected static final Logger logger = LoggerFactory.getLogger(CustomSHAPasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return SHA1.enc(rawPassword.toString());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("error while  trying to obtain password hash, reason: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (null == rawPassword || null == encodedPassword) {
            return false;
        } else {
            try {
                String calculatedHash = SHA1.enc(rawPassword.toString());
                if (calculatedHash.equalsIgnoreCase(encodedPassword)) {
                    return true;
                } else {
                    return false;
                }
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException  e) {
                logger.error("error while  trying to obtain password hash, reason: " + e.getMessage(), e);
                return false;
            }
        }
    }
}

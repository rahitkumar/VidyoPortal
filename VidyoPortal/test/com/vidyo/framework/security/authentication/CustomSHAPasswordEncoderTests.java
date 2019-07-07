package com.vidyo.framework.security.authentication;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CustomSHAPasswordEncoderTests {

    @Test
    public void testEncode() {
        String originalHash = "40bd001563085fc35165329ea1ff5c5ecbdbbeef";
        CustomSHAPasswordEncoder e = new CustomSHAPasswordEncoder();
        assertTrue(e.matches("123", originalHash));
    }
}

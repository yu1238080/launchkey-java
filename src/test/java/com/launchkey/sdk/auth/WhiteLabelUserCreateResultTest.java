package com.launchkey.sdk.auth;

import org.junit.Test;

import static org.junit.Assert.*;

public class WhiteLabelUserCreateResultTest {

    @Test
    public void testGetQrCodeUrl() throws Exception {
        String expected = "expected";
        WhiteLabelUserCreateResult result = new WhiteLabelUserCreateResult(expected, null);
        assertEquals(expected, result.getQrCodeUrl());
    }

    @Test
    public void testGetCode() throws Exception {
        String expected = "expected";
        WhiteLabelUserCreateResult result = new WhiteLabelUserCreateResult(null, expected);
        assertEquals(expected, result.getCode());
    }
}
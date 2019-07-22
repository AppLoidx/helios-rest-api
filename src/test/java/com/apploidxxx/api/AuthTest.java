package com.apploidxxx.api;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Arthur Kupriyanov
 */
public class AuthTest {

    @Test
    public void authorize() {
        Auth auth = new Auth();
        System.out.println(auth.authorize("Arthur2", "123"));
    }
}
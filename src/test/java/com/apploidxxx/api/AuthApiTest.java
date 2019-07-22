package com.apploidxxx.api;

import org.junit.Test;

/**
 * @author Arthur Kupriyanov
 */
public class AuthApiTest {

    @Test
    public void authorize() {
        AuthApi auth = new AuthApi();
        System.out.println(auth.authorize("Arthur2", "123"));
    }
}
package com.apploidxxx.entity;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * @author Arthur Kupriyanov
 */
public class Tokens {
    public final String token;
    @JsonAlias("refresh_token")
    public final String refreshToken;
    public final User user;

    public Tokens(String token, String refreshToken, User user){
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
    }
}

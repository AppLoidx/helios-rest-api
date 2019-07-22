package com.apploidxxx.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

/**
 * @author Arthur Kupriyanov
 */
public class Tokens {

    @JsonAlias("token")
    @JsonbProperty("access_token")
    public final String token;

    @JsonAlias("refresh_token")
    @JsonbProperty("refresh_token")
    public final String refreshToken;

    @JsonIgnore
    @JsonbTransient
    public final User user;

    public Tokens(String token, String refreshToken, User user){
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
    }
}

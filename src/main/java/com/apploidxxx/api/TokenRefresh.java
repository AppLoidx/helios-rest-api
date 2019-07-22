package com.apploidxxx.api;

import com.apploidxxx.entity.Tokens;
import com.apploidxxx.entity.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

/**
 * @author Arthur Kupriyanov
 */
public class TokenRefresh {
    public static Tokens generateToken(@Valid@NotNull@QueryParam("login") String login,
                                       @Valid@NotNull@QueryParam("password") String password){
        return new Tokens("123123", "ccqweFs2", new User());
    }
}

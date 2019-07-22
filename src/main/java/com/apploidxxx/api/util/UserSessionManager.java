package com.apploidxxx.api.util;

import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.entity.User;

/**
 * @author Arthur Kupriyanov
 */
public class UserSessionManager {
    public static User getUser(String token) throws InvalidTokenException {
        User user = UserSession.getUser(token);
        if (user == null) {
            throw new InvalidTokenException();
        } else {
            return user;
        }
    }
}

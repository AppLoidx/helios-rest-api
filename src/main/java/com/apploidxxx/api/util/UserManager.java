package com.apploidxxx.api.util;

import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.user.UserService;

/**
 * @author Arthur Kupriyanov
 */
public class UserManager {
    public static User getUser(String token) throws InvalidTokenException {
        User user = UserSession.getUser(token);
        if (user == null) {
            throw new InvalidTokenException();
        } else {
            return user;
        }
    }

    public static User getUserByName(String name) throws InvalidTokenException {
        User user = UserService.findByName(name);
        if (user == null){
            throw new InvalidTokenException();
        } else {
            return user;
        }
    }
}

package com.apploidxxx.api.util;

import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.exceptions.UserNotFoundException;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.user.UserService;

/**
 * Обертка над {@link UserService}
 *
 * @author Arthur Kupriyanov
 */
public class UserManager {

    /**
     * Находит пользователя по access_token
     * @param token access_token
     * @return {@link User}
     * @throws InvalidTokenException пользователь по токену (и/или токен не валидный)
     */
    public static User getUser(String token) throws InvalidTokenException {
        User user = UserSessionManager.getUser(token);
        if (user == null) {
            throw new InvalidTokenException();
        } else {
            return user;
        }
    }

    /**
     * Находит пользователя по имени (короткой, username(nickname))
     * @param name кортокое имя пользователя
     * @return {@link User}
     * @throws UserNotFoundException пользователь с таким именем не найден
     */
    public static User getUserByName(String name) throws UserNotFoundException {
        User user = UserService.findByName(name);
        if (user == null){
            throw new UserNotFoundException();
        } else {
            return user;
        }
    }
}

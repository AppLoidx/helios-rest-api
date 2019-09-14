package com.apploidxxx.entity.dao.oauth;

import com.apploidxxx.entity.AuthorizationCode;

/**
 * @author Arthur Kupriyanov
 */
public class AuthorizationCodeService {
    private static final AuthorizationCodeDAO authorizationCodeDAO = new AuthorizationCodeDAO();
    public static void save(AuthorizationCode authorizationCode){
        authorizationCodeDAO.save(authorizationCode);
    }

    public static void delete(AuthorizationCode authorizationCode){
        authorizationCodeDAO.delete(authorizationCode);
    }

    public static void update(AuthorizationCode authorizationCode){
        authorizationCodeDAO.update(authorizationCode);
    }

    public static AuthorizationCode findByCode(String code){
        return authorizationCodeDAO.findByCode(code);
    }
}

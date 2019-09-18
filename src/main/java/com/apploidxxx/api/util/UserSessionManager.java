package com.apploidxxx.api.util;

import com.apploidxxx.entity.Session;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.user.SessionService;

import javax.ws.rs.core.Response;

/**
 * Обертка над {@link SessionService}
 *
 * @author Arthur Kupriyanov
 */
class UserSessionManager {

    /**
     * Находит пользователя по id сессии
     * @param sessionId id сессии
     * @return {@link User}
     */
    public static User getUser(String sessionId){
        SessionService ss = new SessionService();
        Session userSession = ss.findSession(sessionId);
        if (userSession==null){
            return null;
        } else {
            return userSession.getUser();
        }
    }

    public static Response refreshToken(String refreshToken){
        return Response.ok("{tokens: \"\"}").build();
    }
}

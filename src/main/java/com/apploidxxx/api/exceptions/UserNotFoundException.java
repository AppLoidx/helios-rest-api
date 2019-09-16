package com.apploidxxx.api.exceptions;

import com.apploidxxx.api.model.ErrorMessage;

import javax.ws.rs.core.Response;

/**
 * Исключение выбрасываемое когда не найден пользователь
 *
 * @author Arthur Kupriyanov
 */
public class UserNotFoundException extends ResponsibleException {
    @Override
    public Response getResponse() {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage("user_not_found", "queried username not found"))
                .build();
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public String getErrorDescription() {
        return null;
    }
}

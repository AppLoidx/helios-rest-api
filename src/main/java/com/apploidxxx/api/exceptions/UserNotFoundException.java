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
                .entity(new ErrorMessage(getErrorMessage(), getErrorDescription()))
                .build();
    }

    @Override
    public String getErrorMessage() {
        return "user_not_found";
    }

    @Override
    public String getErrorDescription() {
        return "queried username not found";
    }
}

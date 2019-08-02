package com.apploidxxx.api.exceptions;

import com.apploidxxx.api.model.ErrorMessage;

import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
public class InvalidTokenException extends ResponsibleException{

    public Response getResponse(){
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorMessage(getErrorMessage(), getErrorDescription()))
                .build();
    }

    @Override
    public String getErrorMessage() {
        return "invalid_token";
    }

    @Override
    public String getErrorDescription() {
        return "Your token is invalid. Take authorization and refresh tokens";
    }

}

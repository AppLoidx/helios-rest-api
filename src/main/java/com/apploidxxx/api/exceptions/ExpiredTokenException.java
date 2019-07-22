package com.apploidxxx.api.exceptions;

import com.apploidxxx.api.model.ErrorMessage;

import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
public class ExpiredTokenException extends Exception implements ResponsibleExceptionImpl {

    @Override
    public Response getResponse() {
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorMessage(getErrorMessage(), getErrorDescription()))
                .build();
    }

    @Override
    public String getErrorMessage() {
        return "expired_token";
    }

    @Override
    public String getErrorDescription() {
        return "your token expired. Take new token via refresh_token";
    }
}

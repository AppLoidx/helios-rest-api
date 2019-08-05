package com.apploidxxx.api.util;

import com.apploidxxx.api.model.ErrorMessage;

import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
public class ErrorResponseFactory {
    public static Response getInvalidParamErrorResponse(String description){
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage("invalid_param", description))
                .build();
    }

    public static Response getForbiddenErrorResponse(String description){
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(new ErrorMessage("insufficient_rights", description))
                .build();
    }

    public static Response getForbiddenErrorResponse(){
        return getForbiddenErrorResponse("You don't have enough rights");
    }

}

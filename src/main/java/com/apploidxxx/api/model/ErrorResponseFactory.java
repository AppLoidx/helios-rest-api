package com.apploidxxx.api.model;

import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
public class ErrorResponseFactory {
    public static Response getInvalidParamErrorResponse(String description){
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage("invalid_param", description)).build();
    }
}
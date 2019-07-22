package com.apploidxxx.api.exceptions;


import com.apploidxxx.api.model.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonAlias;

import javax.json.bind.annotation.JsonbProperty;
import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
public class SessionNotFoundException extends Exception implements ResponsibleExceptionImpl {
    public Response getResponse(){
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(getErrorMessage(), getErrorDescription()))
                .build();
    }

    @Override
    public String getErrorMessage() {
        return "invalid_session";
    }

    @Override
    public String getErrorDescription() {
        return "Session not found";
    }


}

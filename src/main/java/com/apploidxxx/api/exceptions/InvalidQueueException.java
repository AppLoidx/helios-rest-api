package com.apploidxxx.api.exceptions;

import com.apploidxxx.api.model.ErrorMessage;

import javax.ws.rs.core.Response;

/**
 * Ошибка выбрасываемая когда не найдена очередь
 *
 * @author Arthur Kupriyanov
 */
public class InvalidQueueException extends ResponsibleException{

    @Override
    public Response getResponse() {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage(getErrorMessage(), getErrorDescription()))
                .build();
    }

    @Override
    public String getErrorMessage() {
        return "invalid_queue";
    }

    @Override
    public String getErrorDescription() {
        return "queue not found";
    }
}

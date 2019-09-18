package com.apploidxxx.api.util;

import com.apploidxxx.api.model.ErrorMessage;

import javax.ws.rs.core.Response;

/**
 * Класс реализующий паттерн Фабрика. Генерирует {@link Response} для ошибок
 *
 * В качестве entity в Response принимается {@link ErrorMessage}
 *
 * @author Arthur Kupriyanov
 */
public abstract class ErrorResponseFactory {

    /**
     * Возвращает Response со статусом BAD_REQUEST (400)
     * @param description описание ошибки invalid_param
     * @return Response
     */
    public static Response getInvalidParamErrorResponse(String description){
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage("invalid_param", description))
                .build();
    }

    /**
     * Возвращает Response со статусом FORBIDDEN (403)
     * @param description описание ошибки insufficient_rights
     * @return Response
     */
    public static Response getForbiddenErrorResponse(String description){
        return Response
                .status(Response.Status.FORBIDDEN)
                .entity(new ErrorMessage("insufficient_rights", description))
                .build();
    }

    /**
     * Возвращает Response со статусом FORBIDDEN (403)
     * @return Response
     */
    public static Response getForbiddenErrorResponse(){
        return getForbiddenErrorResponse("You don't have enough rights");
    }

}

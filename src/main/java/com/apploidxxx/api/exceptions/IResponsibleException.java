package com.apploidxxx.api.exceptions;


import javax.ws.rs.core.Response;

/**
 * Интерфейс исключений способных возвращать обеъкт {@link Response}
 *
 * @see com.apploidxxx.api.model.ErrorMessage
 * @author Arthur Kupriyanov
 */
public interface IResponsibleException {

    /**
     * Возвращает ответ, который можно сразу отправить в ответ пользователю.
     * Обычно включает в себя также объект {@link com.apploidxxx.api.model.ErrorMessage}
     * @return Response
     */
    Response getResponse();

    /**
     * @return идентификатор ошибки
     */
    String getErrorMessage();

    /**
     *
     * @return описание ошибки
     */
    String getErrorDescription();
}

package com.apploidxxx.api.exceptions;


import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
public interface IResponsibleException {
    Response getResponse();


    String getErrorMessage();
    String getErrorDescription();
}

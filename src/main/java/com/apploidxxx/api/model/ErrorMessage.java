package com.apploidxxx.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import javax.json.bind.annotation.JsonbProperty;

/**
 * @author Arthur Kupriyanov
 */
public class ErrorMessage {

    public ErrorMessage(String errorMessage, String errorDescription) {
        this.errorMessage = errorMessage;
        this.errorDescription = errorDescription;
    }

    @JsonAlias("error")
    @JsonbProperty("error")
    public final String errorMessage;


    @JsonAlias("error_description")
    @JsonbProperty("error_description")
    public final String errorDescription;

}

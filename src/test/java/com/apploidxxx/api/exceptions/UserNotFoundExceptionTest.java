package com.apploidxxx.api.exceptions;

import com.apploidxxx.api.model.ErrorMessage;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Arthur Kupriyanov
 */
public class UserNotFoundExceptionTest {
    @Test
    public void getResponse() {
        try {
            throw new InvalidTokenException();
        } catch (ResponsibleException e){
            Response response = e.getResponse();
            assertEquals(response.getStatus(), 401);
            assertTrue(response.hasEntity());
            assertTrue(response.getEntity() instanceof ErrorMessage);
            ErrorMessage errMsg = (ErrorMessage) response.getEntity();
            assertEquals(e.getErrorMessage(), errMsg.errorMessage);
            assertEquals(e.getErrorDescription(), errMsg.errorDescription);
        }
    }
}
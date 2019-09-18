package com.apploidxxx.api.exceptions;

import com.apploidxxx.api.model.ErrorMessage;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Arthur Kupriyanov
 */
public class InvalidQueueExceptionTest {

    @Test
    public void getResponse() {
        try {
            throw new InvalidQueueException();
        } catch (ResponsibleException e){
            Response response = e.getResponse();
            assertEquals(response.getStatus(), 404);
            assertTrue(response.hasEntity());
            assertTrue(response.getEntity() instanceof ErrorMessage);
            ErrorMessage errMsg = (ErrorMessage) response.getEntity();
            assertEquals(errMsg.errorMessage, "invalid_queue");
            assertEquals(errMsg.errorDescription, "Queue not found");
        }
    }

}
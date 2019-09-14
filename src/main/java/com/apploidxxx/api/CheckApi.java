package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidQueueException;
import com.apploidxxx.api.exceptions.UserNotFoundException;
import com.apploidxxx.api.model.Check;
import com.apploidxxx.api.util.ErrorResponseFactory;
import com.apploidxxx.api.util.QueueManager;
import com.apploidxxx.api.util.UserManager;
import com.apploidxxx.entity.ContactDetails;
import com.apploidxxx.entity.dao.queue.QueueService;
import com.apploidxxx.entity.dao.user.ContactDetailsService;
import com.apploidxxx.entity.queue.Queue;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/check")
@Produces(MediaType.APPLICATION_JSON)
public class CheckApi {
    @GET
    public Response check(@Valid@NotNull@QueryParam("check") String check,
                          @QueryParam("username") String username,
                          @QueryParam("queue_name") String queueName,
                          @QueryParam("email") String email){

        switch (check){
            case "user_exist": return checkUserExist(username);
            case "queue_exist": return checkQueueExist(queueName);
            case "email_exist" : return checkEmailExist(email);
            case "queue_match": return  queueMatch(queueName);
            case "queue_private": return queuePrivate(queueName);
            default: return ErrorResponseFactory.getInvalidParamErrorResponse("invalid check param");
        }

    }

    private Response checkUserExist(String username){
        if (username == null) return ErrorResponseFactory.getInvalidParamErrorResponse("invalid username param");
        try {
            UserManager.getUserByName(username);
        } catch (UserNotFoundException e) {
            return Response.ok(new Check(false)).build();
        }

        return Response.ok(new Check(true)).build();
    }

    private Response checkQueueExist(String queueName){
        if (queueName == null) return ErrorResponseFactory.getInvalidParamErrorResponse("invalid queue_name param");
        try {
            QueueManager.getQueue(queueName);
        } catch (InvalidQueueException e) {
            return Response.ok(new Check(false)).build();
        }
        return Response.ok(new Check(true)).build();
    }

    private Response queueMatch(String queueName){
        if (queueName == null) return ErrorResponseFactory.getInvalidParamErrorResponse("invalid queue_name param");

        List<Queue> queueList = QueueService.findAllQueues();
        List<String[]> queueNames = new ArrayList<>();
        for (Queue q : queueList) {
            try {
                if (q.getName().matches("(" + queueName + ").*")
                        || q.getFullname().matches("(" + queueName + ").*")) {
                    queueNames.add(new String[]{q.getName(), q.getFullname()});
                }
            } catch (PatternSyntaxException ignored){

            }
        }

        return Response.ok(queueNames).build();
    }

    private Response queuePrivate(String queueName){
        if (queueName == null) return ErrorResponseFactory.getInvalidParamErrorResponse("invalid queue_name param");
        try {
            Queue q = QueueManager.getQueue(queueName);
            if (q.getPassword() == null){

                return Response.ok(new Check(true)).build();
            } else {

                return Response.ok(new Check(false)).build();
            }
        } catch (InvalidQueueException e) {
            return e.getResponse();
        }
    }

    private Response checkEmailExist(String email){
        try {
            ContactDetails c = ContactDetailsService.findByEmail(email);
            if (c != null){
                return Response.ok(new Check(true)).build();
            }
        } catch (UserNotFoundException ignored) { }
        return Response.ok(new Check(false)).build();
    }
}

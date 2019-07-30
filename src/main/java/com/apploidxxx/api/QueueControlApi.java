package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.model.ErrorMessage;
import com.apploidxxx.api.model.ErrorResponseFactory;
import com.apploidxxx.api.util.UserManager;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.queue.QueueService;
import com.apploidxxx.entity.queue.Queue;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/queue/{queueId}")
@Produces(MediaType.APPLICATION_JSON)
public class QueueControlApi {

    @PathParam("queueId")
    private String queueId;
    private Queue queue;
    private User user;

    @PUT
    public Response action(@Valid@NotNull@QueryParam("action") String action,
                           @Valid@NotNull@QueryParam("access_token") String accessToken,
                           @PathParam("queueId") String queueId,
                           @QueryParam("type") String newType,
                           @QueryParam("admin") String newAdmin){

        try {
            this.user = UserManager.getUser(accessToken);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }

        if ((this.queue = QueueService.findQueue(queueId)) == null){
         return Response
                 .status(Response.Status.NOT_FOUND)
                 .entity(new ErrorMessage("queue_not_found", "Queue with name " + queueId + " not found"))
                 .build();
        }

        if (!this.queue.getSuperUsers().contains(this.user)) return Response.status(Response.Status.FORBIDDEN).build();

        action = action.toLowerCase();
        switch (action){
            case "shuffle": return shuffle();
            case "setType": return setType(newType);
            case "setAdmin": return setAdmin(newAdmin);
            default:
                return ErrorResponseFactory.getInvalidParamErrorResponse("Invalid action param. Please, check allowed actions");
        }
    }

    private Response shuffle(){
        this.queue.shuffle();
        QueueService.updateQueue(this.queue);
        return Response.ok(this.queue).build();
    }

    private Response setType(String newType){
        if (newType == null) return ErrorResponseFactory.getInvalidParamErrorResponse("You should add a type param");
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
    private Response setAdmin(String newAdmin){
        if (newAdmin == null) return ErrorResponseFactory.getInvalidParamErrorResponse("You should add a type param");

        User newAdminUser;

        try {
            newAdminUser = UserManager.getUserByName(newAdmin);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }

        this.queue.addSuperUser(newAdminUser);
        QueueService.updateQueue(this.queue);
        return Response.ok().build();
    }


}

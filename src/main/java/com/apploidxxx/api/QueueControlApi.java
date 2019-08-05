package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.exceptions.UserNotFoundException;
import com.apploidxxx.api.model.ErrorMessage;
import com.apploidxxx.api.util.ErrorResponseFactory;
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
            case "settype": return setType(newType);
            case "setadmin": return setAdmin(newAdmin);
            default:
                return ErrorResponseFactory.getInvalidParamErrorResponse("Invalid action param. Please, check allowed actions");
        }
    }

    private Response shuffle(){
        try {
            this.queue.shuffle();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.ok(this.queue).build();
    }

    private Response setType(String newType){
        if (newType == null) return ErrorResponseFactory.getInvalidParamErrorResponse("You should add a type param");

        if (this.queue.setGenerationType(newType)){
            QueueService.updateQueue(this.queue);
            return Response.ok().build();
        } else {
            return ErrorResponseFactory.getInvalidParamErrorResponse("Unknown type: " + newType + ". Please, check your request");
        }
    }
    private Response setAdmin(String newAdmin){
        if (newAdmin == null) return ErrorResponseFactory.getInvalidParamErrorResponse("You should add a type param");

        User newAdminUser;

        try { newAdminUser = UserManager.getUserByName(newAdmin); }
        catch (UserNotFoundException e) { return e.getResponse(); }

        this.queue.addSuperUser(newAdminUser);
        QueueService.updateQueue(this.queue);
        return Response.ok().build();
    }


}

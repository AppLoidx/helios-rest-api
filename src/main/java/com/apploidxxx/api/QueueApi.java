package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidQueueException;
import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.util.QueueManager;
import com.apploidxxx.api.util.UserSessionManager;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.queue.QueueService;
import com.apploidxxx.entity.dao.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/queue")
@Produces(MediaType.APPLICATION_JSON)
public class QueueApi {


    @GET
    public Response getQueue(@Valid@NotNull@QueryParam("queue_name") String queueName){

        try {
            return Response.ok(QueueManager.getQueue(queueName)).build();
        } catch (InvalidQueueException e) {
            return e.getResponse();
        }
    }

    @PUT
    public Response joinQueue(@NotNull@QueryParam("queue_name") String queueName,
                            @NotNull@QueryParam("access_token") String token){

        User user;
        try {
            user = UserSessionManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }
        QueueService qs = new QueueService();
        com.apploidxxx.entity.Queue q = qs.findQueue(queueName);
        if (q==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (q.getMembers().contains(user)){
            return Response.status(Response.Status.BAD_REQUEST).entity("You are already in queue").build();
        } else {
            q.addUser(user);
            qs.updateQueue(q);
            new UserService().updateUser(user);

            return Response.ok().build();
        }

    }

    @POST
    public Response createQueue(@Valid@NotNull@QueryParam("queue_name") String queueName,
                              @Valid@NotNull@QueryParam("access_token") String token,
                              @Valid@NotNull@QueryParam("fullname") String fullname){

        User user;
        try {
            user = UserSessionManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }

        QueueService qs = new QueueService();

        com.apploidxxx.entity.Queue q = new com.apploidxxx.entity.Queue(queueName, fullname==null?queueName:fullname);
        q.addSuperUser(user);
        try {
            qs.saveQueue(q);
            return Response.ok().build();
        }catch (Exception e){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteQueue(@NotNull@QueryParam("queue_name") String queueName,
                              @Valid@QueryParam("user_name") String userName,
                              @Valid@QueryParam("access_token") String token){

        User user;
        try {
            user = UserSessionManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }
        if (userName!=null){
            return deleteUser(userName, queueName, user);
        } else {
            return deleteQueue(queueName, user);
        }
    }

    private Response deleteUser(String username, String queueName, User user){
        if (user ==null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        QueueService qs = new QueueService();
        com.apploidxxx.entity.Queue q = qs.findQueue(queueName);
        if (q.getSuperUsers().contains(user)){
            User delUser = new UserService().findByName(username);
            if (delUser == null){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            q.getMembers().remove(delUser);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

    }
    private Response deleteQueue(String queueName, User user){
        QueueService qs = new QueueService();
        com.apploidxxx.entity.Queue q = qs.findQueue(queueName);

        if (q!=null){
            if (q.getSuperUsers().contains(user)){
                qs.deleteQueue(q);
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidQueueException;
import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.model.ErrorMessage;
import com.apploidxxx.api.util.QueueManager;
import com.apploidxxx.api.util.UserSessionManager;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.queue.QueueService;
import com.apploidxxx.entity.dao.user.UserService;
import com.apploidxxx.entity.queue.Queue;

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

    /**
     *
     * @param queueName имя очереди
     * @return Очередь в формате JSON или NOT_FOUND
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueue(   @Valid@NotNull@QueryParam("queue_name") String queueName){

        try {
            return Response.ok(QueueManager.getQueue(queueName)).build();
        } catch (InvalidQueueException e) {
            return e.getResponse();
        }
    }

    /**
     *
     * @param queueName имя очереди
     * @param token access_token
     * @return 200 - успешно вошел в очередь, иначе BAD_REQUEST или NOT_FOUND
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinQueue(  @NotNull@QueryParam("queue_name") String queueName,
                                @NotNull@QueryParam("access_token") String token){

        User user;
        try {
            user = UserSessionManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }
        QueueService qs = new QueueService();
        Queue q = qs.findQueue(queueName);
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

    /**
     *
     * @param queueName имя очереди для короткой ссылки
     * @param token access_token
     * @param fullname полное имя очереди
     * @param password пароль очереди
     * @param generationType тип генерации
     * @return 200 - усли успешно, иначе INTERNAL_SERVER_ERROR, либо NOT_FOUND
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createQueue(@Valid@NotNull@QueryParam("queue_name") String queueName,
                                @Valid@NotNull@QueryParam("access_token") String token,
                                @Valid@NotNull@QueryParam("fullname") String fullname,
                                @QueryParam("password") String password,
                                @QueryParam("generation") String generationType){

        User user;
        try {
            user = UserSessionManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }

        QueueService qs = new QueueService();

        Queue q = new Queue(queueName, fullname==null?queueName:fullname);
        q.addSuperUser(user);
        if (password != null) q.setPassword(password);
        if (generationType != null) q.setGenerationType(generationType);

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

    /**
     *
     * @param queueName имя очереди
     * @param userName нужно указать, если необходимо удлалить пользователя
     * @param target USER - удалить пользователя, QUEUE - удалить очередь (регистронезависим)
     * @param token access_token
     * @return OK, UNAUTHORIZED, NOT_FOUND, NOT_ACCEPTABLE
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@Valid@NotNull@QueryParam("queue_name") String queueName,
                                @QueryParam("username") String userName,
                                @Valid@NotNull@QueryParam("target") String target,
                                @Valid@NotNull@QueryParam("access_token") String token){
        target = target.toUpperCase();
        User user;
        try {
            user = UserSessionManager.getUser(token);
        } catch (InvalidTokenException e) {
            return e.getResponse();
        }
        if (!target.matches("(USER)|(QUEUE)")){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage("invalid_target", "Unknown target value")).build();
        } else if (target.equals("USER") && userName == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage("invalid_param", "You have to declare username")).build();
        }

        if (userName!=null && target.equals("USER")){
            return deleteUser(userName, queueName, user);
        } else if (target.equals("QUEUE")){
            return deleteQueue(queueName, user);
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private Response deleteUser(String username, String queueName, User user){
        if (user ==null){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        QueueService qs = new QueueService();
        Queue q = qs.findQueue(queueName);
        if (q.getSuperUsers().contains(user)){
            User delUser = new UserService().findByName(username);
            if (delUser == null){
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorMessage("user_not_found", "User with name " + username + " not found"))
                        .build();
            }
            q.getMembers().remove(delUser);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

    }

    private Response deleteQueue(String queueName, User user){
        QueueService qs = new QueueService();
        Queue q = qs.findQueue(queueName);

        if (q!=null){
            if (q.getSuperUsers().contains(user)){
                qs.deleteQueue(q);
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("queue_not_found", "Queue with name " + queueName + " not found"))
                    .build();
        }
    }
}

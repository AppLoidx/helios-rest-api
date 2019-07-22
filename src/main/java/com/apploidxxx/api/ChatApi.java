package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.InvalidQueueException;
import com.apploidxxx.api.exceptions.InvalidTokenException;
import com.apploidxxx.api.util.QueueManager;
import com.apploidxxx.api.util.UserSessionManager;
import com.apploidxxx.entity.Chat;
import com.apploidxxx.entity.Message;
import com.apploidxxx.entity.Queue;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.chat.ChatService;
import com.apploidxxx.entity.dao.queue.QueueService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/chat")
@Produces(MediaType.APPLICATION_JSON)
public class ChatApi {
    @GET
    @Path("/{queueName}")
    public Response getMessages(@PathParam("queueName") String queueName,
                                    @Valid@NotNull@QueryParam("lastMsgId") int lastMsgId){
        QueueService qs = new QueueService();
        Queue queue  = qs.findQueue(queueName);
        if (queue==null){
            return null;
        }

        Set<Message> messages = queue.getChat().getMessages();
        Set<Message> response = new LinkedHashSet<>();
        for (Message m : messages){
            if (m.getId() > lastMsgId){
                response.add(m);
            }
        }

        return Response.ok(response).build();
    }

    @PUT
    @Path("/{queueName}")
    public Response addMessage(@PathParam("queueName") String queueName,
                             @Valid@NotNull @QueryParam("message") String message,
                             @Valid@NotNull@QueryParam("token") String token){

        User user;
        Queue q;

        try { user = UserSessionManager.getUser(token); } catch (InvalidTokenException e) { return e.getResponse(); }
        try { q = QueueManager.getQueue(queueName); } catch (InvalidQueueException e) { return e.getResponse(); }

        Chat chat = q.getChat();
        if (message.equals(""))
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("invalid message")
                    .build();

        chat.newMessage(user, message);
        new ChatService().updateChat(chat);
        return Response.ok().build();

    }
}

package com.apploidxxx.api;

import com.apploidxxx.api.exceptions.ResponsibleException;
import com.apploidxxx.api.model.ErrorMessage;
import com.apploidxxx.api.util.QueueManager;
import com.apploidxxx.api.util.UserManager;
import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.queue.QueueService;
import com.apploidxxx.entity.queue.Queue;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author Arthur Kupriyanov
 */
@Path("/api/swap")
public class SwapApi {

    // TODO: Add priority selection !!!

    @POST
    public Response requestSwap(@Valid@NotNull@QueryParam("access_token") String accessToken,
                                @Valid@NotNull@QueryParam("target") String targetUsername,
                                @Valid@NotNull@QueryParam("queue_name") String queueName){
        User user;
        User targetUser;
        Queue queue;
        try {
            user = UserManager.getUser(accessToken);
            targetUser = UserManager.getUserByName(targetUsername);
            queue = QueueManager.getQueue(queueName);
        } catch (ResponsibleException e) {
            return e.getResponse();
        }

        if (!queue.getMembers().contains(user) || !queue.getMembers().contains(targetUser)){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage("user_not_found", "User not found in requested queue")).build();
        }

        boolean isSwapped = queue.getSwapContainer().addSwapRequest(user , targetUser);
        QueueService.updateQueue(queue);

        if (isSwapped) return Response.ok().build();
        else return Response.status(Response.Status.ACCEPTED).build();
    }
}

package com.apploidxxx.api.model;

import com.apploidxxx.entity.User;
import com.apploidxxx.entity.queue.Queue;
import com.apploidxxx.entity.queue.SwapContainer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.util.*;

/**
 * @author Arthur Kupriyanov
 */
@Data
public class UserInfo implements Serializable {
    private  User user;
    private  List<String[]> queues;
    @JsonProperty("queues_member")
    @JsonbProperty("queues_member")
    private  List<String[]> queuesMember;

    private List<Map<String, String>> swapRequests;

    public UserInfo(User user){
        this.user = user;
        this.swapRequests = new ArrayList<>();
        initQueues(user);
    }

    private void initQueues(User user){
        Set<String[]> AllList = new HashSet<>();
        Set<String[]> memberList = new HashSet<>();
        Set<Queue> memberSet = new HashSet<>(user.getQueueMember());
        Set<Queue> superSet = new HashSet<>(user.getQueueSuper());
        for (Queue q: memberSet
        ) {
            memberList.add(new String[]{q.getName(), q.getFullname()});
            AllList.add(new String[]{q.getName(), q.getFullname()});

            // adding list of swap requests
            addSwapRequest(q, user);
        }
        superSet.removeAll(memberSet);
        for (Queue q: superSet
             ) {
            AllList.add(new String[]{q.getName(), q.getFullname()});
        }
        this.queues = new ArrayList<>(AllList);
        this.queuesMember = new ArrayList<>(memberList);
    }

    private void addSwapRequest(Queue queue, User user){
        SwapContainer sc = queue.getSwapContainer();
        User target = sc.getSwapRequest(user);
        if (target != null) {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("queue_name", queue.getName());
            hashMap.put("queue_fullname", queue.getFullname());
            hashMap.put("target_username", target.getUsername());
            hashMap.put("target_firstname", target.getFirstName());
            hashMap.put("target_lastname", target.getLastName());
            swapRequests.add(hashMap);
        }
    }

    public User getUser() {
        return user;
    }

    public List<String[]> getQueues() {
        return queues;
    }
}

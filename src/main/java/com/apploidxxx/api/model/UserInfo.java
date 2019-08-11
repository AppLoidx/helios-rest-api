package com.apploidxxx.api.model;

import com.apploidxxx.entity.User;
import com.apploidxxx.entity.queue.Queue;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public UserInfo(User user){
        this.user = user;
        setQueues(user);
    }

    private void setQueues(User user){
        Set<String[]> AllList = new HashSet<>();
        Set<String[]> memberList = new HashSet<>();
        Set<Queue> memberSet = new HashSet<>(user.getQueueMember());
        Set<Queue> superSet = new HashSet<>(user.getQueueSuper());
        for (Queue q: memberSet
        ) {
            memberList.add(new String[]{q.getName(), q.getFullname()});
            AllList.add(new String[]{q.getName(), q.getFullname()});
        }
        superSet.removeAll(memberSet);
        for (Queue q: superSet
             ) {
            AllList.add(new String[]{q.getName(), q.getFullname()});
        }
        this.queues = new ArrayList<>(AllList);
        this.queuesMember = new ArrayList<>(memberList);
    }

    public User getUser() {
        return user;
    }

    public List<String[]> getQueues() {
        return queues;
    }
}

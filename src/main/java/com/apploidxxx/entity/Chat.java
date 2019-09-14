package com.apploidxxx.entity;

import com.apploidxxx.entity.dao.chat.MessageService;
import com.apploidxxx.entity.queue.Queue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Arthur Kupriyanov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Chat {
    @JsonbTransient
    @JsonIgnore
    @Id
    @GeneratedValue
    private long id;

    public Chat(Queue queue){
        this.queue = queue;
    }

    @OneToOne(mappedBy = "chat")
    @JsonbTransient
    @JsonIgnore
    private Queue queue;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Message> messages;

    public synchronized void newMessage(User user, String message){
        Message msg = new Message(user, message, this);
        MessageService.saveMessage(msg);

        if (messages==null){ messages = new LinkedHashSet<>();}

        messages.add(msg);

    }

}

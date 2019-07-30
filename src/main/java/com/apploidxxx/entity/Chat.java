package com.apploidxxx.entity;

import com.apploidxxx.entity.dao.chat.MessageService;
import com.apploidxxx.entity.queue.Queue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    @Id
    @GeneratedValue
    private long id;

    public Chat(Queue queue){
        this.queue = queue;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "chat")
    private Queue queue;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Message> messages;

    public synchronized void newMessage(User user, String message){
        if (queue.getMembers().contains(user) || queue.getSuperUsers().contains(user)){
            Message msg = new Message(user, message, this);
            MessageService ms= new MessageService();
            ms.saveMessage(msg);

            if (message==null){ messages = new LinkedHashSet<>();}

            messages.add(msg);

        }
    }

}

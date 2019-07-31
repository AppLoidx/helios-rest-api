package com.apploidxxx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 * @author Arthur Kupriyanov
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {

    public Message(User user, String message, Chat chat){
        this.user = user;
        this.message = message;
        this.chat = chat;
    }

    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @JsonbTransient
    @ManyToOne(cascade = CascadeType.ALL)
    private Chat chat;

    @Column
    private String message;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

}

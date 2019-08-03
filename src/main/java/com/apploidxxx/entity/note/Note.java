package com.apploidxxx.entity.note;

import com.apploidxxx.entity.UserData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Arthur Kupriyanov
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Note {
    @Id
    @GeneratedValue
    private Long id;

    public Note(UserData author, UserData target, NoteType type, String content){
        this.author = author;
        this.target = target;
        this.type = type;
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private UserData author;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserData target;

    private String content;
    private NoteType type;
}

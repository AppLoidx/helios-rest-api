package com.apploidxxx.entity.note;

import com.apploidxxx.entity.UserData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;

/**
 * @author Arthur Kupriyanov
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Note {
    @JsonProperty("note_id")
    @JsonbProperty("note_id")
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

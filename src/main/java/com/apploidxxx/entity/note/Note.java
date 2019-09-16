package com.apploidxxx.entity.note;

import com.apploidxxx.entity.UserData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;

/**
 *
 * Записи преподавателей
 *
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

    /**
     * Автор заметки
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private UserData author;

    /**
     * Адресат заметки
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private UserData target;

    /**
     * Содержимое заметки
     */
    private String content;

    /**
     * Тип заметки.
     *
     * Приватный - виден только преподавателю
     * Публичный - виден и преподавателю и самому адресату
     */
    private NoteType type;
}

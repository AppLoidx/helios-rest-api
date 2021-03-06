package com.apploidxxx.api.model;

import com.apploidxxx.entity.note.Note;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * POJO объект для заметок преподавателей
 *
 * @author Arthur Kupriyanov
 */
@NoArgsConstructor
public class NoteModel {
    @JsonbProperty("note_id")
    @JsonProperty("note_id")
    public Long noteId;
    public String author;
    public String target;
    public String content;

    public NoteModel(Note note){
        this.author = note.getAuthor().getUser().getUsername();
        this.target = note.getTarget().getUser().getUsername();
        this.content = note.getContent();
        this.noteId = note.getId();
    }

    public static NoteModel getModel(Note note){
        return new NoteModel(note);
    }

}

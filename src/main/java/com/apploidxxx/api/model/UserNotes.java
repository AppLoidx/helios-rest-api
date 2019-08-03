package com.apploidxxx.api.model;

import com.apploidxxx.entity.note.Note;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author Arthur Kupriyanov
 */
@Setter
@AllArgsConstructor
public class UserNotes {
    private Set<Note> notes;
}

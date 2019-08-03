package com.apploidxxx.entity;

import com.apploidxxx.entity.note.Note;
import com.apploidxxx.entity.note.NoteType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Arthur Kupriyanov
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserData implements Serializable {
    public UserData(User user){
        this.user = user;
        notes = new HashSet<>();
        writtenNotes = new HashSet<>();
    }

    @Id
    @OneToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "target")
    private Set<Note> notes;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "author")
    private Set<Note> writtenNotes;

    public void addNote(Note note){
        notes.add(note);
    }
    public void removeNote(Note note){
        notes.remove(note);
    }
    public void addWrittenNote(Note note) {writtenNotes.add(note);}
    public void removeWrittenNote(Note note) {writtenNotes.remove(note);}
    public Set<Note> getPublicNotes(){
        return notes.stream().filter(n -> n.getType() == NoteType.PUBLIC).collect(Collectors.toSet());
    }

    public Set<Note> getPrivateNotes(){
        return notes.stream().filter(n -> n.getType() == NoteType.PUBLIC).collect(Collectors.toSet());
    }

}

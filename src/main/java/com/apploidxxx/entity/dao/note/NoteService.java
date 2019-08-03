package com.apploidxxx.entity.dao.note;

import com.apploidxxx.entity.note.Note;

/**
 * @author Arthur Kupriyanov
 */
public class NoteService {
    private static NoteDAO noteDAO = new NoteDAO();

    public static void update (Note note) {
        noteDAO.update(note);
    }
    public static void save (Note note) {
        noteDAO.save(note);
    }
    public static void delete (Note note){
        noteDAO.delete(note);
    }
    public static Note findById (Long id){
        return noteDAO.findById(id);
    }
}

package com.apploidxxx.entity.dao.note;

import com.apploidxxx.ds.HibernateSessionFactoryUtil;
import com.apploidxxx.entity.dao.DAOBasicOperations;
import com.apploidxxx.entity.note.Note;

/**
 * @author Arthur Kupriyanov
 */
class NoteDAO {
    private DAOBasicOperations<Note> basicOperations = new DAOBasicOperations<>();

    public void update(Note note){ basicOperations.update(note);}
    public void save(Note note) { basicOperations.save(note);}
    public void delete(Note note) { basicOperations.delete(note);}
    public Note findById(Long id){
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Note.class, id);
    }

}

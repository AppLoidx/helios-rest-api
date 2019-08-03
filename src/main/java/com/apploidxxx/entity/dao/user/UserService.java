package com.apploidxxx.entity.dao.user;

import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.note.NoteService;
import com.apploidxxx.entity.dao.queue.QueueService;
import com.apploidxxx.entity.queue.Queue;

import java.util.List;

/**
 * @author Arthur Kupriyanov
 */
public class UserService {
    private static UsersDAO usersDao = new UsersDAO();

    public UserService() {
    }

    public static User findUser(Long id) {
        return usersDao.findById(id);
    }

    public static void saveUser(User user) {
        usersDao.save(user);
    }

    public static void deleteUser(User user) {
        for (Queue q : user.getQueueMember()){
            q.deleteUser(user);
            QueueService.updateQueue(q);
        }

        for (Queue q : user.getQueueSuper()){
            q.deleteSuperUser(user);
            if (q.getSuperUsers().isEmpty()) QueueService.deleteQueue(q);
            else QueueService.updateQueue(q);
        }

        System.out.println(user.getUserdata().getWrittenNotes());
        System.out.println(user.getUserdata().getNotes());
        user.getUserdata().getWrittenNotes().forEach(NoteService::delete);
        user.getUserdata().getNotes().forEach(NoteService::delete);

        usersDao.delete(user);
    }

    public static void updateUser(User user) {
        usersDao.update(user);
    }

    public static List<User> findAllUsers() {
        return usersDao.findAll();
    }

    public static User findByName(String username){
        return usersDao.findByName(username);
    }

}

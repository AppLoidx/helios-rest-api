package com.apploidxxx.entity.dao.user;

import com.apploidxxx.entity.User;
import com.apploidxxx.entity.dao.queue.QueueService;
import com.apploidxxx.entity.queue.Queue;

import java.util.List;

/**
 * @author Arthur Kupriyanov
 */
public class UserService {
    private UsersDAO usersDao = new UsersDAO();

    public UserService() {
    }

    public User findUser(Long id) {
        return usersDao.findById(id);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        QueueService sq = new QueueService();
        for (Queue q : user.getQueueMember()){
            q.deleteUser(user);
            sq.updateQueue(q);
        }

        for (Queue q : user.getQueueSuper()){
            q.deleteSuperUser(user);
            sq.updateQueue(q);
        }

        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }

    public User findByName(String username){
        return usersDao.findByName(username);
    }

}

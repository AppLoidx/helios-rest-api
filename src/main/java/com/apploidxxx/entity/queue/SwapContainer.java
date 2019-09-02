package com.apploidxxx.entity.queue;

import com.apploidxxx.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Arthur Kupriyanov
 */
@Entity
public class SwapContainer {

    public SwapContainer(){}
    public SwapContainer(Queue queue){
        this.queue = queue;
    }

    @Id
    @GeneratedValue
    Long id;

    @OneToOne
    private Queue queue;

    @ElementCollection(targetClass=User.class, fetch = FetchType.EAGER)
    @MapKeyColumn(name="userMap")
    private Map<User , User> swapMap;

    public void addSwapRequest(User user, User target){
        swapMap.put(user, target);
        if (user.equals(swapMap.get(target))){
            queue.swap(user, target);
        }
    }

    /**
     * Возвращает цель запроса смены мест
     * @param user заказчик (запрашивающий)
     * @return пользователь (цель)
     */
    public User getSwapRequest(User user){
        return swapMap.get(user);
    }

    /**
     * Возвращает список пользователей запросивших смену мест
     * @param user цель
     * @return список пользователей
     */
    public List<User> getUserRequets(User user){
        List<User> users = new ArrayList<>();
        for (User u : swapMap.keySet()){
            if (swapMap.get(u).equals(user)){
                users.add(u);
            }
        }

        return users;
    }
}
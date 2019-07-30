package com.apploidxxx.entity.dao.queue;

import com.apploidxxx.entity.queue.Queue;

import java.util.List;

/**
 * @author Arthur Kupriyanov
 */
public class QueueService {
    private  static QueueDAO queueDAO = new QueueDAO();

    public QueueService() {
    }

    public static Queue findQueue(String queueName) {
        return queueDAO.findById(queueName);
    }

    public static void saveQueue(Queue queue) {
        queueDAO .save(queue);
    }

    public static void deleteQueue(Queue queue) {
        queueDAO .delete(queue);
    }

    public static void updateQueue(Queue queue) {
        queueDAO .update(queue);
    }

    public static List<Queue> findAllQueues() {
        return queueDAO .findAll();
    }
}

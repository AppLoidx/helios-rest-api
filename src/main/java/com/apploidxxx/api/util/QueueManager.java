package com.apploidxxx.api.util;

import com.apploidxxx.api.exceptions.InvalidQueueException;
import com.apploidxxx.entity.queue.Queue;
import com.apploidxxx.entity.dao.queue.QueueService;

/**
 * @author Arthur Kupriyanov
 */
public class QueueManager {

    public  static Queue getQueue(String queueName) throws InvalidQueueException {
        QueueService qs = new QueueService();
        Queue q = qs.findQueue(queueName);

        if (q == null){
            throw new InvalidQueueException();
        } else {
            return q;
        }
    }
}

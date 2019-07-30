package com.apploidxxx.api.util;

import com.apploidxxx.api.exceptions.InvalidQueueException;
import com.apploidxxx.entity.dao.queue.QueueService;
import com.apploidxxx.entity.queue.Queue;

/**
 * @author Arthur Kupriyanov
 */
public class QueueManager {

    public  static Queue getQueue(String queueName) throws InvalidQueueException {
        Queue q = QueueService.findQueue(queueName);

        if (q == null){
            throw new InvalidQueueException();
        } else {
            return q;
        }
    }
}

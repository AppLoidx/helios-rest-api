package com.apploidxxx.entity.queue;

import com.apploidxxx.entity.User;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Arthur Kupriyanov
 */
public class QueueTest {

    @Test
    public void swap() {
        Queue queue = new Queue("name", "name");
        User user1 = new User("u1", "123123123", "", "", "");
        User user2 = new User("u2", "123123123", "", "", "");

        user1.setId(100L);
        user2.setId(200L);
        queue.addUser(user1);
        queue.addUser(user2);

        List<Long> seqOld = new LinkedList<>(queue.getQueueSequence());

        queue.swap(user1, user2);
        List<Long> seqNew = new LinkedList<>(queue.getQueueSequence());

        assertEquals(seqOld.get(0), seqNew.get(1)); // assertion error
        assertEquals(seqOld.get(1), seqNew.get(0));

        queue.swap(user2, user1);
        List<Long> seqPOS = new LinkedList<>(queue.getQueueSequence());
        assertEquals(seqPOS.get(0), seqNew.get(1));
        assertEquals(seqPOS.get(1), seqNew.get(0));
    }
}
package com.apploidxxx.entity.dao.chat;

import com.apploidxxx.entity.Message;

/**
 * @author Arthur Kupriyanov
 */
public class MessageService {
    private static MessageDAO dao = new MessageDAO();
    public static void saveMessage(Message m){
        dao.save(m);
    }
}

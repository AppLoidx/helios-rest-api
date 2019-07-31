package com.apploidxxx.entity.dao.chat;

import com.apploidxxx.entity.Chat;

/**
 * @author Arthur Kupriyanov
 */
public class ChatService {
    private static ChatDAO chatDAO = new ChatDAO();
    public static void updateChat(Chat c){
        chatDAO.update(c);
    }
}

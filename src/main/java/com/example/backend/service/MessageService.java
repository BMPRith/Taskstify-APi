package com.example.backend.service;

import com.example.backend.model.entity.Message;
import com.example.backend.model.request.MessageRequest;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessages();
    Message insertMessages(MessageRequest messageRequest, Integer userId, String name);
    Message getMessageById(Integer messageId);
}

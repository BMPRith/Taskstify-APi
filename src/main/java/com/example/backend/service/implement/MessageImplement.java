package com.example.backend.service.implement;

import com.example.backend.model.entity.Message;
import com.example.backend.model.request.MessageRequest;
import com.example.backend.repository.MessageRepository;
import com.example.backend.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageImplement implements MessageService {
    private final MessageRepository messageRepository;

    public MessageImplement(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.getAllMessages();
    }

    @Override
    public Message insertMessages(MessageRequest messageRequest) {
        return messageRepository.insertMessageByUser(messageRequest);
    }

    @Override
    public Message getMessageById(Integer messageId) {
        return messageRepository.getMessageById(messageId);
    }
}

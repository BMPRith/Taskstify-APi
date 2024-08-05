package com.example.backend.controller;

import com.example.backend.model.entity.Message;
import com.example.backend.model.entity.Task;
import com.example.backend.model.entity.User;
import com.example.backend.model.request.MessageRequest;
import com.example.backend.model.request.TaskRequest;
import com.example.backend.model.response.MessageResponse;
import com.example.backend.model.response.TaskResponse;
import com.example.backend.model.response.UserResponse;
import com.example.backend.service.implement.MessageImplement;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/taskstify")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    private final MessageImplement messageImplement;
    public MessageController(MessageImplement messageImplement) {
        this.messageImplement = messageImplement;
    }

    @GetMapping("/messages/all")
    public ResponseEntity<MessageResponse<List<Message>>> getAllUsers(){
        MessageResponse<List<Message>> response = MessageResponse.<List<Message>>builder()
                .payload(messageImplement.getAllMessages())
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<MessageResponse<Message>> getMessageByID(@PathVariable("id") Integer messageId){
        MessageResponse<Message> response = MessageResponse.<Message>builder()
                .payload(messageImplement.getMessageById(messageId))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageResponse<Message>> insertMessage(@RequestBody MessageRequest messageRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getCredentials();
        String name = authentication.getName();
        MessageResponse<Message> response = MessageResponse.<Message>builder()
                .payload(messageImplement.insertMessages(messageRequest, userId, name))
                .date(new Timestamp(System.currentTimeMillis()))
                .success("true")
                .build();
        return ResponseEntity.ok(response);
    }
}

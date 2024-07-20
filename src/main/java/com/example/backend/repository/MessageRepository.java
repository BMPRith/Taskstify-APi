package com.example.backend.repository;

import com.example.backend.model.entity.Message;
import com.example.backend.model.request.MessageRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageRepository {
    @Results(id = "Mapper", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "message", column = "messages")
    })
    @Select("SELECT * FROM messages")
    List<Message> getAllMessages();
    @Select("SELECT * FROM messages WHERE id = #{id}")
    @ResultMap("Mapper")
    Message getMessageById(Integer id);
    @Select("INSERT INTO messages (email, messages) VALUES (#{request.email}, #{request.message}) RETURNING id, email, messages")
    @ResultMap("Mapper")
    Message insertMessageByUser(@Param("request") MessageRequest messageRequest);
}

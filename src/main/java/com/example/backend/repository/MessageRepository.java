package com.example.backend.repository;

import com.example.backend.model.entity.Message;
import com.example.backend.model.request.MessageRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageRepository {
    @Results(id = "Mapper", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "message", column = "messages"),
            @Result(property = "rating", column = "rating"),
            @Result(property = "name", column = "name"),
    })
    @Select("SELECT * FROM messages WHERE rating >= 4")
    List<Message> getAllMessages();
    @Select("SELECT * FROM messages WHERE id = #{id}")
    @ResultMap("Mapper")
    Message getMessageById(Integer id);
    @Select("INSERT INTO messages (messages, rating, user_id, name) VALUES (#{request.message}, #{request.rating}, #{userId}, #{name})")
    @ResultMap("Mapper")
    Message insertMessageByUser(@Param("request") MessageRequest messageRequest, Integer userId, String name);
}

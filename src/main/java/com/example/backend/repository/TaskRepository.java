package com.example.backend.repository;

import com.example.backend.model.entity.Task;
import com.example.backend.model.request.TaskRequest;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface TaskRepository {
    @Results(id = "Mapper", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "date", column = "date"),
            @Result(property = "status", column = "status"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "userId", column = "user_id")
    })
    @Select("SELECT * FROM")
    List<Task> getAllTasks();
    @Select("SELECT * FROM tasks WHERE id = #{taskId}")
    @ResultMap("Mapper")
    Task getTaskByID(Integer taskId);
    @Select("SELECT COUNT(*) FROM categories WHERE id = #{categoryId} AND user_id = #{userId}")
    int checkExistedCategoryForCurrentUser(@Param("categoryId") Integer categoryId, @Param("userId") Integer userId);

    @Select("INSERT INTO tasks (name, description, date, status, category_id, user_id) VALUES (#{request.name}, #{request.description}, #{request.date}, #{request.status}, #{request.categoryId}, #{userId}) RETURNING id, name, description, date, status, user_id, category_id")
    @ResultMap("Mapper")
    Task insertTaskForCurrentUser(@Param("request")TaskRequest taskRequest, Integer userId);
    @Select("UPDATE tasks SET name = #{request.name}, description = #{request.description}, date = #{date}, status = #{request.status}, category_id = #{request.categoryId} WHERE id = #{taskId} AND user_id = #{userId} RETURNING id, name, description, date, status, user_id, category_id")
    @ResultMap("Mapper")
    Task updateTaskForCurrentUser(@Param("request")TaskRequest taskRequest, Timestamp date, Integer taskId, Integer userId);
    @Select("DELETE FROM tasks WHERE id = #{taskId} AND user_id = #{userId} RETURNING id, name, description, date, status, user_id, category_id")
    @ResultMap("Mapper")
    Task deleteTaskForCurrentUser(Integer taskId, Integer userId);
    @Select("SELECT * FROM tasks WHERE user_id = #{userId}")
    @ResultMap("Mapper")
    List<Task> getAllTasksForCurrentUser(Integer userId);
    @Select("SELECT * FROM tasks WHERE id = #{taskId} AND user_id = #{userId}")
    @ResultMap("Mapper")
    Task getTaskByIDForCurrentUser(Integer taskId, Integer userId);

    @Select("SELECT * FROM tasks WHERE category_id = #{categoryId} AND user_id = #{userId}")
    @ResultMap("Mapper")
    List<Task> getTasksByCategory(Integer categoryId, Integer userId);
}

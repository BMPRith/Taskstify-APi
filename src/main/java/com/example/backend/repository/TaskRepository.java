package com.example.backend.repository;

import com.example.backend.model.entity.Task;
import com.example.backend.model.request.TaskRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TaskRepository {
    @Select("SELECT * FROM tasks LIMIT ${size} OFFSET #{size} * (#{page} -1)")
    List<Task> getAllTasks(Integer page, Integer size);
    @Select("SELECT * FROM tasks WHERE id = #{taskId}")
//    @Result(one = @One(select = "com.example.backend.repository.CategoryRepository.getCategoryByID"))
    Task getTaskByID(Integer taskId);
    @Select("INSERT INTO tasks (name, description, date, status, category_id) VALUES (#{request.name}, #{request.description}, #{request.date}, #{request.status}, #{request.categoryId})")
    Task insertTask(@Param("request")TaskRequest taskRequest);
    @Select("UPDATE tasks SET name = #{request.name} WHERE id = {taskId}")
    Task updateTask(@Param("request")TaskRequest taskRequest, Integer taskId);
    @Select("DELETE FROM tasks WHERE id = #{taskId}")
    Task deleteTask(Integer taskId);
    @Select("SELECT * FROM tasks LIMIT ${size} OFFSET #{size} * (#{page} -1)")
    List<Task> getAllTasksUser(Integer page, Integer size);
    @Select("SELECT * FROM tasks WHERE id = #{taskId}")
    Task getTaskByIDUser(Integer taskId);
    @Select("SELECT * FROM task_tb WHERE status = #{request}")
    List<Task> filterTaskByStatus(@Param("request") String taskStatus);

}

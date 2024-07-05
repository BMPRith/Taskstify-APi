package com.example.backend.service;

import com.example.backend.model.entity.Task;
import com.example.backend.model.request.TaskRequest;

import java.sql.Timestamp;
import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskByID(Integer taskId);
    Task insertTaskForCurrentUser(TaskRequest taskRequest, Integer userId);
    Task deleteTaskForCurrentUser(Integer taskId, Integer userId);
    Task updateTaskForCurrentUser(TaskRequest taskRequest, Timestamp date, Integer taskId, Integer userId);
    List<Task> getAllTasksForCurrentUser(Integer userId);
    Task getTaskByIDForCurrentUser(Integer taskId, Integer userId);
    List<Task> getTasksByCategory(Integer categoryId, Integer userId);
}

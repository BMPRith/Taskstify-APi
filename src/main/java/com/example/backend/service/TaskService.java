package com.example.backend.service;

import com.example.backend.model.entity.Task;
import com.example.backend.model.request.TaskRequest;

import java.sql.Timestamp;
import java.util.List;

public interface TaskService {
    List<Task> getAllTasks(Integer page, Integer size);
    Task getTaskByID(Integer taskId);
    Task insertTaskForCurrentUser(TaskRequest taskRequest, Timestamp date, Integer userId);
    Task deleteTaskForCurrentUser(Integer taskId, Integer userId);
    Task updateTaskForCurrentUser(TaskRequest taskRequest, Timestamp date, Integer taskId, Integer userId);
    List<Task> getAllTasksForCurrentUser(Integer page, Integer size, Integer userId);
    Task getTaskByIDForCurrentUser(Integer taskId, Integer userId);
}

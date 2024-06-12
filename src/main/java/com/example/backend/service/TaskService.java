package com.example.backend.service;

import com.example.backend.model.entity.Task;
import com.example.backend.model.request.TaskRequest;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks(Integer page, Integer size);
    Task getTaskByID(Integer taskId);
    Task insertTask(TaskRequest taskRequest);
    Task deleteTask(Integer taskId);
    Task updateTask(TaskRequest taskRequest, Integer taskId);
    List<Task> getAllTasksUser(Integer page, Integer size);
    Task getTaskByIDUser(Integer taskId);
}

package com.example.backend.service.implement;

import com.example.backend.model.entity.Task;
import com.example.backend.model.request.TaskRequest;
import com.example.backend.repository.TaskRepository;
import com.example.backend.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskImplement implements TaskService {
    private final TaskRepository taskRepository;

    public TaskImplement(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks(Integer page, Integer size) {
        return taskRepository.getAllTasks(page, size);
    }

    @Override
    public Task getTaskByID(Integer taskId) {
        return taskRepository.getTaskByID(taskId);
    }

    @Override
    public Task insertTask(TaskRequest taskRequest) {
        return taskRepository.insertTask(taskRequest);
    }

    @Override
    public Task deleteTask(Integer taskId) {
        return taskRepository.deleteTask(taskId);
    }

    @Override
    public Task updateTask(TaskRequest taskRequest, Integer taskId) {
        return taskRepository.updateTask(taskRequest, taskId);
    }

    @Override
    public List<Task> getAllTasksUser(Integer page, Integer size) {
        return taskRepository.getAllTasksUser(page, size);
    }

    @Override
    public Task getTaskByIDUser(Integer taskId) {
        return taskRepository.getTaskByIDUser(taskId);
    }
}

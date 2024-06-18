package com.example.backend.service.implement;

import com.example.backend.exception.BlankFieldHandler;
import com.example.backend.exception.CategoryNotExisted;
import com.example.backend.exception.StatusHandler;
import com.example.backend.exception.TaskNotFound;
import com.example.backend.model.entity.Task;
import com.example.backend.model.request.TaskRequest;
import com.example.backend.repository.TaskRepository;
import com.example.backend.service.TaskService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
        Task search = taskRepository.getTaskByID(taskId);
        if (search!=null) {
            return search;
        } else throw new TaskNotFound(taskId);
    }

    @Override
    public Task insertTaskForCurrentUser(TaskRequest taskRequest, Timestamp date, Integer userId) {
        int categoryCount = taskRepository.checkExistedCategoryForCurrentUser(taskRequest.getCategoryId(), userId);
        if (categoryCount <= 0) {
            throw new CategoryNotExisted(categoryCount);
        }else if (taskRequest.getName().isBlank() || taskRequest.getStatus().isBlank() || taskRequest.getDescription().isBlank() || taskRequest.getCategoryId() == null) {
            throw new BlankFieldHandler("Field Cannot Be Blank");
        } else if (!"not_yet".equals(taskRequest.getStatus()) &&
                !"is_completed".equals(taskRequest.getStatus()) &&
                !"in_progress".equals(taskRequest.getStatus())) {
            throw new StatusHandler("Status Field Accepts Only: (not_yet, is_completed, in_progress)");
        } else {
            return taskRepository.insertTaskForCurrentUser(taskRequest, date, userId);
        }
    }

    @Override
    public Task deleteTaskForCurrentUser(Integer taskId, Integer userId) {
        Task delete = taskRepository.deleteTaskForCurrentUser(taskId, userId);
        if (delete!=null) {
            return delete;
        } else throw new TaskNotFound(taskId);
    }

    @Override
    public Task updateTaskForCurrentUser(TaskRequest taskRequest,Timestamp date, Integer taskId, Integer userId) {
        Task update = taskRepository.updateTaskForCurrentUser(taskRequest, date, taskId, userId);
        int categoryCount = taskRepository.checkExistedCategoryForCurrentUser(taskRequest.getCategoryId(), userId);
        if(update == null){
            throw new TaskNotFound(taskId);
        } else if (categoryCount <= 0) {
            throw new CategoryNotExisted(categoryCount);
        }else if (taskRequest.getName().isBlank() || taskRequest.getStatus().isBlank() || taskRequest.getDescription().isBlank() || taskRequest.getCategoryId() == null) {
            throw new BlankFieldHandler("Field Cannot Be Blank");
        } else if (!"not_yet".equals(taskRequest.getStatus()) &&
                !"is_completed".equals(taskRequest.getStatus()) &&
                !"in_progress".equals(taskRequest.getStatus())) {
            throw new StatusHandler("Status Field Accepts Only: (not_yet, is_completed, in_progress)");
        } else {
            return taskRepository.updateTaskForCurrentUser(taskRequest, date, taskId, userId);
        }
    }

    @Override
    public List<Task> getAllTasksForCurrentUser(Integer page, Integer size, Integer userId) {
        return taskRepository.getAllTasksForCurrentUser(page, size, userId);
    }

    @Override
    public Task getTaskByIDForCurrentUser(Integer taskId, Integer userId) {
        Task search = taskRepository.getTaskByIDForCurrentUser(taskId, userId);
        if (search!=null) {
            return search;
        } else throw new TaskNotFound(taskId);
    }
}

package com.mxr.to_do.services;

import com.mxr.to_do.dtos.TaskRequest;
import com.mxr.to_do.dtos.TaskResponse;
import com.mxr.to_do.dtos.CompletedTaskResponse;
import com.mxr.to_do.entities.Task;
import com.mxr.to_do.entities.CompletedTask;
import com.mxr.to_do.entities.User;
import com.mxr.to_do.repositories.TaskRepository;
import com.mxr.to_do.repositories.CompletedTaskRepository;
import com.mxr.to_do.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final CompletedTaskRepository completedTaskRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(TaskRequest taskRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .priority(taskRequest.getPriority())
                .isComplete(false)
                .user(user)
                .build();

        Task savedTask = taskRepository.save(task);

        return convertToTaskResponse(savedTask);
    }

    public List<TaskResponse> getActiveTasks(Long userId) {
        return taskRepository.findByUserIdAndIsCompleteFalse(userId).stream()
                .map(this::convertToTaskResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse updateTask(Long taskId, TaskRequest taskRequest, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        boolean oldCompleteStatus = task.isComplete();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());
        task.setComplete(taskRequest.isComplete());

        Task updatedTask = taskRepository.save(task);

        checkAndMigrateTask(updatedTask, oldCompleteStatus);

        return convertToTaskResponse(updatedTask);
    }

    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        taskRepository.delete(task);
    }

    public TaskResponse completeTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        task.setComplete(true);
        Task updatedTask = taskRepository.save(task);

        migrateTaskToCompleted(updatedTask);

        return convertToTaskResponse(updatedTask);
    }

    public TaskResponse uncompleteTask(Long taskId, Long userId) {
        CompletedTask completedTask = completedTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Completed task not found"));

        if (!completedTask.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        Task task = Task.builder()
                .title(completedTask.getTitle())
                .description(completedTask.getDescription())
                .priority(completedTask.getPriority())
                .isComplete(false)
                .user(completedTask.getUser())
                .build();

        Task savedTask = taskRepository.save(task);
        completedTaskRepository.delete(completedTask);

        return convertToTaskResponse(savedTask);
    }

    public List<CompletedTaskResponse> getCompletedTasks(Long userId) {
        return completedTaskRepository.findByUserId(userId).stream()
                .map(this::convertToCompletedTaskResponse)
                .collect(Collectors.toList());
    }

    public void deleteCompletedTask(Long taskId, Long userId) {
        CompletedTask completedTask = completedTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Completed task not found"));

        if (!completedTask.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        completedTaskRepository.delete(completedTask);
    }

    private void checkAndMigrateTask(Task task, boolean oldCompleteStatus) {
        boolean newCompleteStatus = Boolean.TRUE.equals(task.isComplete());

        if (!oldCompleteStatus && newCompleteStatus) {
            migrateTaskToCompleted(task);
        } else if (oldCompleteStatus && !newCompleteStatus) {
            migrateTaskToActive(task);
        }
    }

    private void migrateTaskToCompleted(Task task) {
        CompletedTask completedTask = CompletedTask.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .originalTaskId(task.getId())
                .user(task.getUser())
                .build();

        completedTaskRepository.save(completedTask);
        taskRepository.delete(task);
    }

    private void migrateTaskToActive(Task task) {
    }

    private TaskResponse convertToTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.isComplete());
    }

    private CompletedTaskResponse convertToCompletedTaskResponse(CompletedTask completedTask) {
        return new CompletedTaskResponse(
                completedTask.getId(),
                completedTask.getTitle(),
                completedTask.getDescription(),
                completedTask.getPriority(),
                completedTask.getOriginalTaskId());
    }
}

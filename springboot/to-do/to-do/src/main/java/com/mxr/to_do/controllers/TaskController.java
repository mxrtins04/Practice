package com.mxr.to_do.controllers;

import com.mxr.to_do.dtos.TaskRequest;
import com.mxr.to_do.dtos.TaskResponse;
import com.mxr.to_do.dtos.CompletedTaskResponse;
import com.mxr.to_do.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    
    private final TaskService taskService;
    
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest,
                                                 @RequestHeader("X-User-Id") Long userId) {
        TaskResponse response = taskService.createTask(taskRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getActiveTasks(@RequestHeader("X-User-Id") Long userId) {
        List<TaskResponse> tasks = taskService.getActiveTasks(userId);
        return ResponseEntity.ok(tasks);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                 @Valid @RequestBody TaskRequest taskRequest,
                                                 @RequestHeader("X-User-Id") Long userId) {
        TaskResponse response = taskService.updateTask(id, taskRequest, userId);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id,
                                         @RequestHeader("X-User-Id") Long userId) {
        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable Long id,
                                                   @RequestHeader("X-User-Id") Long userId) {
        TaskResponse response = taskService.completeTask(id, userId);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/uncomplete")
    public ResponseEntity<TaskResponse> uncompleteTask(@PathVariable Long id,
                                                     @RequestHeader("X-User-Id") Long userId) {
        TaskResponse response = taskService.uncompleteTask(id, userId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/completed")
    public ResponseEntity<List<CompletedTaskResponse>> getCompletedTasks(@RequestHeader("X-User-Id") Long userId) {
        List<CompletedTaskResponse> tasks = taskService.getCompletedTasks(userId);
        return ResponseEntity.ok(tasks);
    }
    
    @DeleteMapping("/completed/{id}")
    public ResponseEntity<Void> deleteCompletedTask(@PathVariable Long id,
                                                 @RequestHeader("X-User-Id") Long userId) {
        taskService.deleteCompletedTask(id, userId);
        return ResponseEntity.noContent().build();
    }
}

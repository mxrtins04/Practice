package com.mxr.to_do.services;

import com.mxr.to_do.dtos.TaskRequest;
import com.mxr.to_do.dtos.TaskResponse;
import com.mxr.to_do.dtos.CompletedTaskResponse;
import com.mxr.to_do.entities.Task;
import com.mxr.to_do.entities.CompletedTask;
import com.mxr.to_do.entities.User;
import com.mxr.to_do.enums.Priority;
import com.mxr.to_do.repositories.TaskRepository;
import com.mxr.to_do.repositories.CompletedTaskRepository;
import com.mxr.to_do.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CompletedTaskRepository completedTaskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private Task task;
    private CompletedTask completedTask;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .build();

        task = Task.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .priority(Priority.MEDIUM)
                .isComplete(false)
                .user(user)
                .build();

        completedTask = CompletedTask.builder()
                .id(1L)
                .title("Test Task")
                .description("Test Description")
                .priority(Priority.MEDIUM)
                .originalTaskId(1L)
                .user(user)
                .build();

        taskRequest = new TaskRequest();
        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Test Description");
        taskRequest.setPriority(Priority.MEDIUM);
        taskRequest.setComplete(false);
    }

    @Test
    void createTask_ShouldReturnTaskResponse_WhenUserExists() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = taskService.createTask(taskRequest, 1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Test Task", response.title());
        assertEquals("Test Description", response.description());
        assertEquals(Priority.MEDIUM, response.priority());
        assertFalse(response.isComplete());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.createTask(taskRequest, 1L));
        assertEquals("User not found", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void getActiveTasks_ShouldReturnTaskList_WhenTasksExist() {
        List<Task> tasks = List.of(task);
        when(taskRepository.findByUserIdAndIsCompleteFalse(anyLong())).thenReturn(tasks);

        List<TaskResponse> responses = taskService.getActiveTasks(1L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Test Task", responses.get(0).title());
        verify(taskRepository).findByUserIdAndIsCompleteFalse(1L);
    }

    @Test
    void getActiveTasks_ShouldReturnEmptyList_WhenNoTasksExist() {
        when(taskRepository.findByUserIdAndIsCompleteFalse(anyLong())).thenReturn(List.of());

        List<TaskResponse> responses = taskService.getActiveTasks(1L);

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask_WhenUserOwnsTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = taskService.updateTask(1L, taskRequest, 1L);

        assertNotNull(response);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void updateTask_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.updateTask(1L, taskRequest, 1L));
        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTask_ShouldThrowException_WhenUserDoesNotOwnTask() {
        User otherUser = User.builder().id(2L).build();
        task.setUser(otherUser);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.updateTask(1L, taskRequest, 1L));
        assertEquals("Access denied", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_ShouldDeleteTask_WhenUserOwnsTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        taskService.deleteTask(1L, 1L);

        verify(taskRepository).delete(task);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.deleteTask(1L, 1L));
        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    void deleteTask_ShouldThrowException_WhenUserDoesNotOwnTask() {
        User otherUser = User.builder().id(2L).build();
        task.setUser(otherUser);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.deleteTask(1L, 1L));
        assertEquals("Access denied", exception.getMessage());
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    void completeTask_ShouldMigrateTask_WhenUserOwnsTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = taskService.completeTask(1L, 1L);

        assertNotNull(response);
        verify(taskRepository).save(task);
        verify(completedTaskRepository).save(any(CompletedTask.class));
        verify(taskRepository).delete(task);
    }

    @Test
    void completeTask_ShouldThrowException_WhenTaskNotFound() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.completeTask(1L, 1L));
        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void completeTask_ShouldThrowException_WhenUserDoesNotOwnTask() {
        User otherUser = User.builder().id(2L).build();
        task.setUser(otherUser);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.completeTask(1L, 1L));
        assertEquals("Access denied", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void uncompleteTask_ShouldReturnTask_WhenUserOwnsCompletedTask() {
        when(completedTaskRepository.findById(anyLong())).thenReturn(Optional.of(completedTask));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = taskService.uncompleteTask(1L, 1L);

        assertNotNull(response);
        verify(taskRepository).save(any(Task.class));
        verify(completedTaskRepository).delete(completedTask);
    }

    @Test
    void uncompleteTask_ShouldThrowException_WhenCompletedTaskNotFound() {
        when(completedTaskRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.uncompleteTask(1L, 1L));
        assertEquals("Completed task not found", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void uncompleteTask_ShouldThrowException_WhenUserDoesNotOwnCompletedTask() {
        User otherUser = User.builder().id(2L).build();
        completedTask.setUser(otherUser);
        when(completedTaskRepository.findById(anyLong())).thenReturn(Optional.of(completedTask));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> taskService.uncompleteTask(1L, 1L));
        assertEquals("Access denied", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void getCompletedTasks_ShouldReturnCompletedTaskList_WhenTasksExist() {
        List<CompletedTask> completedTasks = List.of(completedTask);
        when(completedTaskRepository.findByUserId(anyLong())).thenReturn(completedTasks);

        List<CompletedTaskResponse> responses = taskService.getCompletedTasks(1L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Test Task", responses.get(0).title());
        verify(completedTaskRepository).findByUserId(1L);
    }

    @Test
    void getCompletedTasks_ShouldReturnEmptyList_WhenNoTasksExist() {
        when(completedTaskRepository.findByUserId(anyLong())).thenReturn(List.of());

        List<CompletedTaskResponse> responses = taskService.getCompletedTasks(1L);

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    @Test
    void deleteCompletedTask_ShouldDeleteTask_WhenUserOwnsTask() {
        when(completedTaskRepository.findById(anyLong())).thenReturn(Optional.of(completedTask));

        taskService.deleteCompletedTask(1L, 1L);

        verify(completedTaskRepository).delete(completedTask);
    }

    @Test
    void deleteCompletedTask_ShouldThrowException_WhenTaskNotFound() {
        when(completedTaskRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.deleteCompletedTask(1L, 1L));
        assertEquals("Completed task not found", exception.getMessage());
        verify(completedTaskRepository, never()).delete(any(CompletedTask.class));
    }

    @Test
    void deleteCompletedTask_ShouldThrowException_WhenUserDoesNotOwnTask() {
        User otherUser = User.builder().id(2L).build();
        completedTask.setUser(otherUser);
        when(completedTaskRepository.findById(anyLong())).thenReturn(Optional.of(completedTask));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> taskService.deleteCompletedTask(1L, 1L));
        assertEquals("Access denied", exception.getMessage());
        verify(completedTaskRepository, never()).delete(any(CompletedTask.class));
    }

    @Test
    void updateTask_ShouldMigrateToCompleted_WhenTaskIsMarkedComplete() {
        taskRequest.setComplete(true);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskService.updateTask(1L, taskRequest, 1L);

        verify(completedTaskRepository).save(any(CompletedTask.class));
        verify(taskRepository).delete(task);
    }

    @Test
    void updateTask_ShouldNotMigrate_WhenTaskStatusUnchanged() {
        task.setComplete(false);
        taskRequest.setComplete(false);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskService.updateTask(1L, taskRequest, 1L);

        verify(completedTaskRepository, never()).save(any(CompletedTask.class));
        verify(taskRepository, never()).delete(task);
    }

    @Test
    void createTask_ShouldSetIsCompleteToFalse() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            assertFalse(savedTask.isComplete());
            return task;
        });

        taskService.createTask(taskRequest, 1L);

        verify(taskRepository).save(argThat(savedTask -> !savedTask.isComplete()));
    }

    @Test
    void getActiveTasks_ShouldHandleMultipleTasks() {
        Task task2 = Task.builder()
                .id(2L)
                .title("Task 2")
                .description("Description 2")
                .priority(Priority.HIGH)
                .isComplete(false)
                .user(user)
                .build();

        List<Task> tasks = List.of(task, task2);
        when(taskRepository.findByUserIdAndIsCompleteFalse(anyLong())).thenReturn(tasks);

        List<TaskResponse> responses = taskService.getActiveTasks(1L);

        assertEquals(2, responses.size());
        assertEquals("Test Task", responses.get(0).title());
        assertEquals("Task 2", responses.get(1).title());
    }

    @Test
    void getCompletedTasks_ShouldHandleMultipleTasks() {
        CompletedTask completedTask2 = CompletedTask.builder()
                .id(2L)
                .title("Completed Task 2")
                .description("Description 2")
                .priority(Priority.LOW)
                .originalTaskId(2L)
                .user(user)
                .build();

        List<CompletedTask> completedTasks = List.of(completedTask, completedTask2);
        when(completedTaskRepository.findByUserId(anyLong())).thenReturn(completedTasks);

        List<CompletedTaskResponse> responses = taskService.getCompletedTasks(1L);

        assertEquals(2, responses.size());
        assertEquals("Test Task", responses.get(0).title());
        assertEquals("Completed Task 2", responses.get(1).title());
    }
}

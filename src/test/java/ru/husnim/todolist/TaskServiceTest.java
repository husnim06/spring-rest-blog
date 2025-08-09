package ru.husnim.todolist;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import ru.husnim.todolist.dto.TaskDTO;
import ru.husnim.todolist.model.Task;
import static ru.husnim.todolist.model.Task.Priority;
import ru.husnim.todolist.repository.TaskRepository;
import ru.husnim.todolist.service.TaskService;

public class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task("Test Task", "Description", false, Priority.MEDIUM);
        task.setId(1L);
    }

    @Test
    public void testGetAllTasks() {
        when(repository.findAll()).thenReturn(Arrays.asList(task));

        List<Task> tasks = service.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetTaskById() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = service.getTaskById(1L);

        assertEquals("Test Task", foundTask.getTitle());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testGetTaskByIdNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.getTaskById(2L);
        });

        assertEquals("Задача не найдена", exception.getMessage());
    }
    
    @Test
    public void testGetTaskByTitle() {
        when(repository.findByTitle("Test Task")).thenReturn(Optional.of(task));

        Task foundTask = service.getTaskByTitle("Test Task");

        assertEquals(1L, foundTask.getId());
        verify(repository, times(1)).findByTitle("Test Task");
    }
    
    @Test
    public void testGetTaskByTitleNotFound() {
        when(repository.findByTitle("Test Task NotFound")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.getTaskByTitle("Test Task NotFound");
        });

        assertEquals("Задача не найдена", exception.getMessage());
    }
    
    @Test
    public void testGetTaskByStatus() {
        when(repository.findByCompleted(true)).thenReturn(Arrays.asList(task));

        List<Task> completedTasks = service.getTasksByStatus(true);

        assertEquals(1, completedTasks.size());
        assertEquals("Test Task", completedTasks.get(0).getTitle());
        verify(repository, times(1)).findByCompleted(true);
    }
    
    @Test
    public void testGetTaskByStatusNotFound() {
        when(repository.findByCompleted(false)).thenReturn(Arrays.asList());

        List<Task> notFoundTask = service.getTasksByStatus(false);
        
        assertEquals(0, notFoundTask.size());
        verify(repository, times(1)).findByCompleted(false);
    }

    @Test
    public void testGetTaskByPriority() {
        when(repository.findByPriority(Priority.MEDIUM)).thenReturn(Arrays.asList(task));

        List<Task> completedTasks = service.getTasksByPriority(Priority.MEDIUM);

        assertEquals(1, completedTasks.size());
        assertEquals("Test Task", completedTasks.get(0).getTitle());
        verify(repository, times(1)).findByPriority(Priority.MEDIUM);
    }
    
    @Test
    public void testGetTaskByPriorityNotFound() {
        when(repository.findByPriority(Priority.LOW)).thenReturn(Arrays.asList());

        List<Task> notFoundTask = service.getTasksByPriority(Priority.LOW);
        
        assertEquals(0, notFoundTask.size());
        verify(repository, times(1)).findByPriority(Priority.LOW);
    }
    
    @Test
    public void testCreateTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("New Task");
        taskDTO.setDescription("New Description");
        taskDTO.setCompleted(false);

        when(repository.save(any(Task.class))).thenReturn(task);

        Task createdTask = service.createTask(taskDTO);

        assertEquals("Test Task", createdTask.getTitle());
        verify(repository, times(1)).save(any(Task.class));
    }
    
    @Test
    public void testCreateTaskWithExistingTitle() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Existing Task");
        taskDTO.setDescription("Description");
        taskDTO.setCompleted(false);

        when(repository.findByTitle("Existing Task")).thenReturn(Optional.of(new Task("Existing Task", "Some description", false, Priority.MEDIUM)));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.createTask(taskDTO);
        });

        assertEquals("Задача с таким заголовком уже существует", exception.getMessage());
        verify(repository, times(1)).findByTitle("Existing Task");
    }

    @Test
    public void testUpdateTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Updated Task");
        taskDTO.setDescription("Updated Description");
        taskDTO.setCompleted(true);

        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(repository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = service.updateTask(1L, taskDTO);

        assertEquals("Updated Task", updatedTask.getTitle());
        assertTrue(updatedTask.getCompleted());
        verify(repository, times(1)).save(any(Task.class));
    }
    
    @Test
    public void testUpdateTaskNotFound() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Updated Task");
        taskDTO.setDescription("Updated Description");
        taskDTO.setCompleted(true);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.updateTask(1L, taskDTO);
        });

        assertEquals("Задача не найдена", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }


    @Test
    public void testDeleteTask() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        assertDoesNotThrow(() -> service.deleteTask(1L));
        verify(repository, times(1)).delete(task);
    }
    
    @Test
    public void testDeleteTaskNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.deleteTask(1L);
        });

        assertEquals("Задача не найдена", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }

}

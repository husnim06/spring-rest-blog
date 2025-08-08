package ru.husnim.todolist.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.husnim.todolist.dto.TaskDTO;
import ru.husnim.todolist.model.Task;
import ru.husnim.todolist.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService service;

    @GetMapping
    public List<Task> getAllTasks() {
        logger.info("Получение всех задач");
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        logger.info("Получение задачи по id: {}", id);
        return service.getTaskById(id);
    }

    @GetMapping("/search/{title}")
    public Task getTaskByTitle(@PathVariable String title) {
        logger.info("Получение задачи по заголовку: {}", title);
        return service.getTaskByTitle(title);
    }
    
    @GetMapping("/filter")
    public List<Task> getTaskByTitle(@RequestParam boolean completed) {
        logger.info("Получение задачи со статусом: {}", completed);
        return service.getTasksByStatus(completed);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        logger.info("Создание задачи с заголовком: {}", taskDTO.getTitle());
        Task createdTask = service.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,@Valid @RequestBody TaskDTO taskDTO) {
        logger.info("Изменение задачи с заголовком: {}", taskDTO.getTitle());
        return service.updateTask(id, taskDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        logger.info("Удаление задачи с id: {}", id);
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}

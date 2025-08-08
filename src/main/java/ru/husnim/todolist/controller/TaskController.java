package ru.husnim.todolist.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping
    public Task createTask(@RequestBody TaskDTO taskDTO) {
        logger.info("Создание задачи с заголовком: {}", taskDTO.getTitle());
        return service.createTask(taskDTO);
    }

    @PutMapping
    public Task updateTask(@RequestBody Long id, TaskDTO taskDTO) {
        logger.info("Изменение задачи с заголовком: {}", taskDTO.getTitle());
        return service.updateTask(id, taskDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        logger.info("Удаление задачи с id: {}", id);
        service.deleteTask(id);
    }

}

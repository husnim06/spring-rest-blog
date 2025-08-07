package ru.husnim.todolist.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.husnim.todolist.dto.TaskDTO;
import ru.husnim.todolist.model.Task;
import ru.husnim.todolist.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    @GetMapping("/search/{title}")
    public Task getTaskByTitle(@PathVariable String title) {
        return service.getTaskByTitle(title);
    }

    @PostMapping
    public Task createTask(TaskDTO taskDTO) {
        return service.createTask(taskDTO);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, TaskDTO taskDTO) {
        return service.updateTask(id, taskDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
    }

}

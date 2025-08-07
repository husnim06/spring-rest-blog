package ru.husnim.todolist.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.husnim.todolist.dto.TaskDTO;
import ru.husnim.todolist.model.Task;
import ru.husnim.todolist.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));
    }

    public Task getTaskByTitle(String title) {
        Task task = repository.findByTitle(title);
        if (task == null) {
            throw new RuntimeException("Запись не найдена");
        }
        return task;
    }

    public Task createTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        return repository.save(task);
    }

    public Task updateTask(Long id, TaskDTO taskDTO) {
        Task task = repository.findById(id).orElseThrow(() -> new RuntimeException("Запись не найдена"));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        return repository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        repository.delete(task);
    }

}

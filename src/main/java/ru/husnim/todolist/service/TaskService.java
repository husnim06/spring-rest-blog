package ru.husnim.todolist.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.husnim.todolist.dto.TaskDTO;
import ru.husnim.todolist.model.Task;
import ru.husnim.todolist.model.Task.Priority;
import ru.husnim.todolist.repository.TaskRepository;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        logger.debug("Получение всех задач из репозитория");
        return repository.findAll();
    }

    public Task getTaskById(Long id) {
        try {
            logger.debug("Получение задачи с id из репозитория: {}", id);
            return repository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));
        } catch (Exception e) {
            logger.error("Ошибка при получении задачи с ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public Task getTaskByTitle(String title) {
        try {
            logger.debug("Получение задачи с заголовком из репозитория: {}", title);
            return repository.findByTitle(title).orElseThrow(() -> new RuntimeException("Задача не найдена"));
        } catch (Exception e) {
            logger.error("Ошибка при получении задачи с заголовком {}: {}", title, e.getMessage());
            throw e;
        }
    }

    public List<Task> getTasksByStatus(boolean completed) {
        try {
            logger.debug("Получение задачи с статусом из репозитория: {}", completed);
            return repository.findByCompleted(completed);
        } catch (Exception e) {
            logger.error("Ошибка при получении задачи со статусом {}: {}", completed, e.getMessage());
            throw e;
        }
    }

    public List<Task> getTasksByPriority(Priority priority) {
        try {
            logger.debug("Получение задачи с приоритетом из репозитория: {}", priority);
            return repository.findByPriority(priority);
        } catch (Exception e) {
            logger.error("Ошибка при получении задачи с приоритетом {}: {}", priority, e.getMessage());
            throw e;
        }
    }

    public Task createTask(TaskDTO taskDTO) {
        logger.debug("Создание новой задачи с заголовком: {}", taskDTO.getTitle());
        if (repository.findByTitle(taskDTO.getTitle()).isPresent()) {
            throw new RuntimeException("Задача с таким заголовком уже существует");
        }
        Task task = new Task(
                taskDTO.getTitle(), 
                taskDTO.getDescription(), 
                taskDTO.getCompleted(), 
                taskDTO.getPriority());
        return repository.save(task);
    }

    public Task updateTask(Long id, TaskDTO taskDTO) {
        try {
            logger.debug("Изменение задачи с заголовком: {}", taskDTO.getTitle());
            Task task = repository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setCompleted(taskDTO.getCompleted());
            task.setPriority(taskDTO.getPriority());
            return repository.save(task);
        } catch (Exception e) {
            logger.error("Ошибка при изменении задачи с заголовком {}: {}", taskDTO.getTitle(), e.getMessage());
            throw e;
        }
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        logger.debug("Удаление задачи с заголовком: {}", task.getTitle());
        repository.delete(task);
    }

}

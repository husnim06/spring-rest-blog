package ru.husnim.todolist.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;
import ru.husnim.todolist.model.Task;

public class TaskDTO {

    @NotNull
    @Size(min = 1, message = "Задача не может быть пустой")
    private String title;
    
    private String description;
    private boolean completed;
    
    @Enumerated(EnumType.STRING)
    private Task.Priority priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public Task.Priority getPriority() {
        return priority;
    }

    public void setPriority(Task.Priority priority) {
        this.priority = priority;
    }

}

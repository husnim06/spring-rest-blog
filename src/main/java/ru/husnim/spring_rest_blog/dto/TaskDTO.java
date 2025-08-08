package ru.husnim.spring_rest_blog.dto;

import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

public class TaskDTO {

    @NotNull
    @Size(min = 1, message = "Задача не может быть пустой")
    private String title;
    private String description;
    private boolean completed;

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

}

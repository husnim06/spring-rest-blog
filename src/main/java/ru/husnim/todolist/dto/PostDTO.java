package ru.husnim.todolist.dto;

import org.antlr.v4.runtime.misc.NotNull;

public class PostDTO {

    @NotNull
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

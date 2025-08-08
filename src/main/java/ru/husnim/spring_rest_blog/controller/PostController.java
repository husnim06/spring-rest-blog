package ru.husnim.spring_rest_blog.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.husnim.spring_rest_blog.dto.PostDTO;
import ru.husnim.spring_rest_blog.model.Post;
import ru.husnim.spring_rest_blog.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService service;

    @GetMapping
    public List<Post> getAllPosts() {
        logger.info("Получение всех записей");
        return service.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        logger.info("Получение записи по id: {}", id);
        return service.getPostById(id);
    }

    @GetMapping("/search/{title}")
    public Post getPostByTitle(@PathVariable String title) {
        logger.info("Получение записи по заголовку: {}", title);
        return service.getPostByTitle(title);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDTO postDTO) {
        logger.info("Создание записи с заголовком: {}", postDTO.getTitle());
        Post createdPost = service.createPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @Valid PostDTO postDTO) {
        logger.info("Изменение записи с заголовком: {}", postDTO.getTitle());
        return service.updatePost(id, postDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        logger.info("Удаление записи с id: {}", id);
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}

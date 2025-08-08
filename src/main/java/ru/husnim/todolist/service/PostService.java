package ru.husnim.todolist.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.husnim.todolist.dto.PostDTO;
import ru.husnim.todolist.model.Post;
import ru.husnim.todolist.repository.PostRepository;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository repository;

    public List<Post> getAllPosts() {
        logger.debug("Получение всех записей из репозитория");
        return repository.findAll();
    }

    public Post getPostById(Long id) {
        try {
            logger.debug("Получение записи с id из репозитория: {}", id);
            return repository.findById(id).orElseThrow(() -> new RuntimeException("Запись не найдена"));
        } catch (Exception e) {
            logger.error("Ошибка при получении записи с ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public Post getPostByTitle(String title) {
        try {
            logger.debug("Получение записи с заголовком из репозитория: {}", title);
            return repository.findByTitle(title).orElseThrow(() -> new RuntimeException("Запись не найдена"));
        } catch (Exception e) {
            logger.error("Ошибка при получении записи с заголовком {}: {}", title, e.getMessage());
            throw e;
        }
    }

    public Post createPost(PostDTO postDTO) {
        logger.debug("Создание новой записи с заголовком: {}", postDTO.getTitle());
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        return repository.save(post);
    }

    public Post updatePost(Long id, PostDTO postDTO) {
        try {
            logger.debug("Изменение записи с заголовком: {}", postDTO.getTitle());
            Post post = repository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            return repository.save(post);
        } catch (Exception e) {
            logger.error("Ошибка при изменении записи с заголовком {}: {}", postDTO.getTitle(), e.getMessage());
            throw e;
        }
    }

    public void deletePost(Long id) {
        Post post = getPostById(id);
        logger.debug("Удаление записи с заголовком: {}", post.getTitle());
        repository.delete(post);
    }

}

package ru.husnim.spring_rest_blog.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.husnim.spring_rest_blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByTitle(String title);

}

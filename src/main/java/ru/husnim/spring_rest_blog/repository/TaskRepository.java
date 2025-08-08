package ru.husnim.spring_rest_blog.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.husnim.spring_rest_blog.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);

}

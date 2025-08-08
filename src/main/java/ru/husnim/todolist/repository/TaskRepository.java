package ru.husnim.todolist.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.husnim.todolist.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);

}

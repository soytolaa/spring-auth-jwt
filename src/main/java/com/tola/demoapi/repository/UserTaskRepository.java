package com.tola.demoapi.repository;

import com.tola.demoapi.model.entities.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
    Optional<UserTask> findByUserUserIdAndTaskId(Long userId, Long taskId);

    List<UserTask> findByTaskId(Long taskId);
}

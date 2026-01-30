package com.tola.demoapi.repository;

import com.tola.demoapi.model.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
   Optional<List<Task>> findByProjectId(Long projectId);
}

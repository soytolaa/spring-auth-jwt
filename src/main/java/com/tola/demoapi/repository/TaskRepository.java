package com.tola.demoapi.repository;

import com.tola.demoapi.model.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
   List<Task> findByProjectId(Long projectId);

   @Query("""
    SELECT t FROM Task t
    LEFT JOIN FETCH t.userTasks ut
    LEFT JOIN FETCH ut.user
    WHERE t.project.id = :projectId
""")
   List<Task> findAllByProjectIdWithUsers(@Param("projectId") Long projectId);
}

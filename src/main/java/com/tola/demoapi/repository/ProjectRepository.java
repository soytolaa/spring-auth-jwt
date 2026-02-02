package com.tola.demoapi.repository;

import com.tola.demoapi.model.entities.Project;
import com.tola.demoapi.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByCreatedBy(User createdBy);
}

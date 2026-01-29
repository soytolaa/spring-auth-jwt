package com.tola.demoapi.repository;

import com.tola.demoapi.model.entities.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, UUID> {
    List<UserProject> findByUserUserId(UUID userId);
}


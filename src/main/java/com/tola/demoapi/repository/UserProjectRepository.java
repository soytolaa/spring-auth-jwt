package com.tola.demoapi.repository;

import com.tola.demoapi.model.entities.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    List<UserProject> findByUserUserId(Long userId);

    Optional<List<UserProject>> findByProjectId(Long projectId);

    Optional<UserProject> findByUserUserIdAndProjectId(Long userId, Long projectId);


    boolean existsByUserUserIdAndProjectId(Long userId, Long projectId);
}

package com.tola.demoapi.repository;

import com.tola.demoapi.model.entities.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    List<UserProject> findByProjectId(Long projectId);

    Optional<UserProject> findByProjectIdAndUserUserId(Long projectId, Long userId);

    boolean existsByUserUserIdAndProjectId(Long userId, Long projectId);

    @Query(value = "SELECT COUNT(*) FROM UserProject t WHERE t.project.id = :projectId")
    Integer countMember(@Param("projectId") Long projectId);

}

package com.tola.demoapi.repository;

import com.tola.demoapi.model.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByCode(UUID code);

    @Query("""
    SELECT DISTINCT p
    FROM Project p
    JOIN p.userProjects up
    WHERE up.user.id = :userId and p.isActive=true ORDER BY p.updatedAt desc 
""")
    List<Project> findDistinctProjectsByUserId(Long userId);
}

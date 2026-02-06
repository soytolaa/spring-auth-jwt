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

    @Query("select t from Project t inner join UserProject u on t.id = u.project.id where u.user.userId = :userId")
    List<Project> findAllByUserId(@Param("userId") Long userId);

//    List<Project> findByUserProjectsUserUserId(Long userId);
}

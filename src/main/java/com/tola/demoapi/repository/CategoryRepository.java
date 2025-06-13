package com.tola.demoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tola.demoapi.model.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

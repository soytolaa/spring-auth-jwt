package com.tola.demoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tola.demoapi.model.entities.Stock;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

}

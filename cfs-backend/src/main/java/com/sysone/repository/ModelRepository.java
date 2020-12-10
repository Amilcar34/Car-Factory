package com.sysone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sysone.entity.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long>{
  
}

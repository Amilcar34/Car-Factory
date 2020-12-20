package com.sysone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sysone.entity.Optional;
@Repository
public interface OptionalRepository extends JpaRepository<Optional, Long> {
  
}

package com.sysone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sysone.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{

}

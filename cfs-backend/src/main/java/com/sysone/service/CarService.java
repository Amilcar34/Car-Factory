package com.sysone.service;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sysone.contracts.StatsCar;
import com.sysone.entity.Car;

@Service
public interface CarService{

  BigDecimal calculateCost(Long idModel, Set<Long> idsOptionals);

  Car create(long idModel, Set<Long> idsOptionals);

  Car update(long id, long idModel, Set<Long> idsOptionals);

  boolean delete(long id);

  StatsCar stats();

}

package com.sysone.service;

import java.math.BigDecimal;
import java.util.Set;

import com.sysone.entity.Optional;

public interface OptionalService {

  BigDecimal sumCost(Set<Optional> optionals);

  Set<Optional> getByIds(Set<Long> idsOptionals);
  
}

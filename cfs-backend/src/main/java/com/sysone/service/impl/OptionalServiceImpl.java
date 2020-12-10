package com.sysone.service.impl;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysone.entity.Optional;
import com.sysone.repository.OptionalRepository;
import com.sysone.service.OptionalService;

@Service
public class OptionalServiceImpl implements OptionalService {
  
  @Autowired
  OptionalRepository optionalRepository;
  
  public BigDecimal sumCost(Set<Optional> optionals){
	return optionals.stream().map(optional -> optional.getCost()).reduce(BigDecimal::add).get();
  }
  
  public Set<Optional> getByIds(Set<Long> idsOptionals){
	return idsOptionals.stream().map(id -> optionalRepository.getOne(id)).collect(Collectors.toSet());
  }

}

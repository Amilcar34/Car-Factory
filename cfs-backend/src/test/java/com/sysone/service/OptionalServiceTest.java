package com.sysone.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.sysone.SysoneApplication;
import com.sysone.entity.Optional;

@SpringBootTest
@ContextConfiguration(classes = SysoneApplication.class)
public class OptionalServiceTest {
  
  @Autowired
  OptionalService optionalService;
  
  @Test
  @Transactional
  public void getByIdsTest(){
	
	Optional slidingRoof = new Optional(1L, "tc", "Techo corredizo", new BigDecimal("12000.00"));
	Optional airConditioning = new Optional(4L, "ab", "Airbag", new BigDecimal("7000.00"));
	Set<Optional> optionals = new HashSet(Arrays.asList(slidingRoof, airConditioning));
	Set<Optional> optionalsResponse = optionalService.getByIds(new HashSet(Arrays.asList(1L, 4L)));
	
	assertTrue(optionals.containsAll(optionalsResponse));
	assertTrue(optionalsResponse.containsAll(optionals));
	assertEquals(optionalsResponse, optionals);
  }
  
  @Test
  public void sumCostTest() {
	
	BigDecimal costAirConditioning = new BigDecimal(20000);
	BigDecimal costAlloyWheels = new BigDecimal(12000);
	Optional airConditioning = new Optional(2L, "aa", "Aire acondicionado", costAirConditioning);
	Optional alloyWheels = new Optional(5L, "ll", "Llantas de aleación", costAlloyWheels);
	BigDecimal optionalsResponse = optionalService.sumCost(new HashSet(Arrays.asList(airConditioning, alloyWheels)));
	
	assertEquals(costAirConditioning.add(costAlloyWheels), optionalsResponse);

  }
}

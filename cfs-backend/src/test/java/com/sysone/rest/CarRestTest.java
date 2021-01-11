package com.sysone.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.sysone.SysoneApplication;
import com.sysone.contracts.StatsCar;
import com.sysone.contracts.StatsModel;
import com.sysone.contracts.StatsOptional;
import com.sysone.dto.CarDto;
import com.sysone.entity.Car;
import com.sysone.entity.Model;
import com.sysone.entity.Optional;
import com.sysone.service.CarService;

@SpringBootTest
@ContextConfiguration(classes = SysoneApplication.class)
public class CarRestTest {
  
  @Autowired
  CarRest carRest;
  @MockBean
  CarService carService;
  
  @Test
  void calculate_cost_test(){
	
	BigDecimal costSedan = new BigDecimal(230000);
	BigDecimal costSlidingRoof = new BigDecimal(12000);
	BigDecimal costAirConditioning = new BigDecimal(20000);
	CarDto carDto = new CarDto(1L, new HashSet<>(Arrays.asList(3L, 4L)));
	
	Mockito.when(carService.calculateCost(carDto.getModel(), carDto.getOptionals())).thenReturn(costSedan.add(costSlidingRoof).add(costAirConditioning));
	
	ResponseEntity<BigDecimal> entity = carRest.calculateCost(carDto);
	
	assertEquals(costSedan.add(costSlidingRoof).add(costAirConditioning), entity.getBody());
	assertEquals(HttpStatus.OK, entity.getStatusCode());
  }
  
  @Test
  void create_car(){
	// Set up
	BigDecimal costSedan = new BigDecimal(230000);
	Set<Long> idsOptionals = new HashSet<>(Arrays.asList(1L, 4L));
	BigDecimal costSlidingRoof = new BigDecimal(12000);
	BigDecimal costAirbag = new BigDecimal(20000);
	Optional slidingRoof = new Optional(1L, "tc", "Techo corredizo", costSlidingRoof);
	Optional airConditioning = new Optional(4L, "ab", "Airbag", costAirbag);
	Set<Optional> setOptionals = new HashSet<>(Arrays.asList(slidingRoof, airConditioning));
	CarDto carDto = new CarDto(1L, idsOptionals);
	BigDecimal total_amount = costSedan.add(costSlidingRoof).add(costAirbag);
	Car car_return = new Car(1L, new Model(1L, "Sedán ", costSedan), setOptionals, total_amount);
	Mockito.when(carService.create(carDto.getModel(), carDto.getOptionals())).thenReturn(car_return);
	// Execute
	ResponseEntity<Car> httpEntity = carRest.create(carDto);
	// Verify
	assertNotNull(httpEntity.getBody().getId());
	assertEquals(total_amount, httpEntity.getBody().getPrice());
	assertEquals(HttpStatus.CREATED, httpEntity.getStatusCode());
  }
  
  @Test
  void update_car(){
	// Set up
	BigDecimal costSedan = new BigDecimal(230000);
	Set<Long> idsOptionals = new HashSet<>(Arrays.asList(3L, 2L))	;
	CarDto carDto = new CarDto(2L, idsOptionals);
	BigDecimal costSlidingRoof = new BigDecimal(12000);
	BigDecimal costAirbag = new BigDecimal(20000);
	Optional slidingRoof = new Optional(1L, "tc", "Techo corredizo", costSlidingRoof);
	Optional airConditioning = new Optional(4L, "ab", "Airbag", costAirbag);
	Set<Optional> setOptionals = Stream.of(slidingRoof, airConditioning).collect(Collectors.toSet());
	BigDecimal total_amount = costSedan.add(costSlidingRoof).add(costAirbag);
	Car car_return = new Car(1L, new Model(1L, "Sedán ", costSedan), setOptionals, total_amount);
	Mockito.when(carService.update(1L, carDto.getModel(), carDto.getOptionals())).thenReturn(car_return);
	// Execute
	ResponseEntity<Car> httpEntity = carRest.update(1L, carDto);
	// Verify
	assertNotNull(httpEntity.getBody().getId());
	assertEquals(costSlidingRoof.add(costAirbag).add(costSedan), httpEntity.getBody().getPrice());
	assertEquals(HttpStatus.OK, httpEntity.getStatusCode());
  }
  
  @Test
  void delete_car(){
	
	assertNull(carRest.delete(1L).getBody());
  }
  
}

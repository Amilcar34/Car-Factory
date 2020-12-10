package com.sysone.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.sysone.SysoneApplication;
import com.sysone.entity.Car;
import com.sysone.entity.Model;
import com.sysone.entity.Optional;
import com.sysone.repository.CarRepository;
import com.sysone.repository.ModelRepository;

@SpringBootTest
@ContextConfiguration(classes = SysoneApplication.class)
public class CarServiceTest {
  
  @Autowired
  CarService carService;
  @Autowired
  CarRepository carRepository;
  @MockBean
  ModelRepository modelRepository;
  @MockBean
  OptionalService optionalService;
  
  @Before
  public void cnfigure_on_set_up(){
	carRepository.deleteAll();
  }
  
  @Test
  public void calculate_cost_test(){
	
	BigDecimal costSedan = new BigDecimal(230000);
	BigDecimal costSlidingRoof = new BigDecimal(12000);
	BigDecimal costAirConditioning = new BigDecimal(20000);
	Optional slidingRoof = new Optional(1L, "tc", "Techo corredizo", costSlidingRoof);
	Optional airConditioning = new Optional(2L, "aa", "Aire acondicionado", costAirConditioning);
	
	Mockito.when(modelRepository.getOne(Mockito.any())).thenReturn(new Model(1L, "Sedán ", costSedan));
	Mockito.when(optionalService.getByIds(Mockito.any())).thenReturn(Set.of(slidingRoof, airConditioning));
	Mockito.when(optionalService.sumCost(Mockito.any())).thenReturn(costSlidingRoof.add(costAirConditioning));
	
	BigDecimal response = carService.calculateCost(1L, Set.of(3L, 4L));
	
	assertEquals(costSedan.add(costSlidingRoof).add(costAirConditioning), response);
  }
  
  @Test
  public void create_car(){
	// Set up
	BigDecimal costFamily = new BigDecimal(245000);
	Set<Long> idsOptionals = Set.of(1L, 4L);
	BigDecimal costSlidingRoof = new BigDecimal(12000);
	BigDecimal costAirbag = new BigDecimal(20000);
	Optional slidingRoof = new Optional(1L, "tc", "Techo corredizo", costSlidingRoof);
	Optional airConditioning = new Optional(4L, "ab", "Airbag", costAirbag);
	Set<Optional> setOptionals = Set.of(slidingRoof, airConditioning);
	
	Mockito.when(modelRepository.getOne(Mockito.any())).thenReturn(new Model(2L, "Familiar", costFamily));
	Mockito.when(optionalService.getByIds(idsOptionals)).thenReturn(setOptionals);
	Mockito.when(optionalService.sumCost(setOptionals)).thenReturn(costSlidingRoof.add(costAirbag));
	// Execute
	Car response = carService.create(2L, idsOptionals);
	// Verify
	assertNotNull(response.getId());
	assertEquals(costSlidingRoof.add(costAirbag).add(costFamily), response.getPrice());
  }
  
  @Test
  public void update_car(){
	
	BigDecimal costSedan = new BigDecimal(230000);
	Set<Long> idsOptionals = Set.of(3L, 2L);
	BigDecimal costSlidingRoof = new BigDecimal(12000);
	BigDecimal costAirbag = new BigDecimal(20000);
	Optional slidingRoof = new Optional(1L, "tc", "Techo corredizo", costSlidingRoof);
	Optional airConditioning = new Optional(4L, "ab", "Airbag", costAirbag);
	Set<Optional> setOptionals = Stream.of(slidingRoof, airConditioning).collect(Collectors.toSet());
	
	Mockito.when(modelRepository.getOne(Mockito.any())).thenReturn(new Model(1L, "Sedán ", costSedan));
	Mockito.when(optionalService.getByIds(idsOptionals)).thenReturn(setOptionals);
	Mockito.when(optionalService.sumCost(setOptionals)).thenReturn(costSlidingRoof.add(costAirbag));
	
	carRepository.save(new Car(1L, new Model(2L, "Familiar", new BigDecimal(245000)), setOptionals, new BigDecimal(277000)));
	Car response = carService.update(1L, 2L, idsOptionals);
	
	assertNotNull(response.getId());
	assertEquals(Long.valueOf(1), response.getId());
	assertEquals(costSlidingRoof.add(costAirbag).add(costSedan), response.getPrice());
  }
  
  @Test
  public void delete_car_not_exist(){
	
	Long id = Long.valueOf(10L);
	Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	  carService.delete(id);
	});
	String actualMessage = exception.getMessage();
	
	assertEquals(actualMessage, "No existe un Auto con este id:" + id);
  }
  
  @Test
  public void delete_car(){
	carRepository.save(new Car(1L, new Model(2L, "Familiar", new BigDecimal(245000)), null, new BigDecimal(277000)));
	assertTrue(carService.delete(1L));
  }
  
}

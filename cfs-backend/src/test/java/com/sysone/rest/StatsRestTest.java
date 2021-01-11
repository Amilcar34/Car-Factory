package com.sysone.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.sysone.SysoneApplication;
import com.sysone.contracts.StatsCar;
import com.sysone.contracts.StatsModel;
import com.sysone.contracts.StatsOptional;
import com.sysone.entity.Model;
import com.sysone.service.StatsService;

@SpringBootTest
@ContextConfiguration(classes = SysoneApplication.class)
public class StatsRestTest {
  
  @MockBean
  StatsService statsService;
  
 
  @Test
  void stats_car(){
	// Set up
	Model modelSedan = new Model(1L, "sedan", new BigDecimal(230000));
	StatsModel statsModel = new StatsModel() {
	  public Integer getPercent(){
		return 100;
	  }
	  
	  public String getModel(){
		return modelSedan.getName();
	  }
	  
	  public Long getCount(){
		return Long.valueOf(1);
	  }
	};
	StatsOptional optional1 = new StatsOptional() {
	  public Integer getPercent(){
		return 50;
	  }
	  
	  public String getOptional(){
		return "tc";
	  }
	  
	  public Long getCount(){
		return Long.valueOf(1);
	  }
	};
	StatsOptional optional2 = new StatsOptional() {
	  public Integer getPercent(){
		return 50;
	  }
	  
	  public String getOptional(){
		return "ab";
	  }
	  
	  public Long getCount(){
		return Long.valueOf(1);
	  }
	};
	StatsCar statsCarMock = new StatsCar();
	statsCarMock.setCars(new HashSet<>(Arrays.asList(statsModel)));
	statsCarMock.setCount_car(Long.valueOf(1));
	statsCarMock.setOptionals(new HashSet<>(Arrays.asList(optional1, optional2)));
	Mockito.when(statsService.stats()).thenReturn(statsCarMock);
	// Execute
	StatsCar statsCar = statsService.stats();
	// Verify
	assertNotNull(statsCar);
	assertEquals(statsCarMock.getCars(), statsCar.getCars());
	assertEquals(statsCarMock.getCount_car(), statsCar.getCount_car());
	assertEquals(statsCarMock.getOptionals(), statsCar.getOptionals());
  }
}

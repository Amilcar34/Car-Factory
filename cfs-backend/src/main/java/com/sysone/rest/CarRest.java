package com.sysone.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysone.dto.CarDto;
import com.sysone.entity.Car;
import com.sysone.service.CarService;

@RestController
@RequestMapping("/cars")
public class CarRest {
  
  @Autowired
  CarService carService;
  
  @PostMapping("/calculateCost")
  public ResponseEntity<BigDecimal> calculateCost(CarDto carDto){
	  List<String> lo = new ArrayList();
	return ResponseEntity.ok(carService.calculateCost(carDto.getModel(), carDto.getOptionals()));
  }
  
  @PostMapping("/")
  public ResponseEntity<Car> create(CarDto carDto){
	return new ResponseEntity<Car>(carService.create(carDto.getModel(), carDto.getOptionals()), HttpStatus.CREATED);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<Car> update(@PathVariable long id, CarDto carDto){
	return ResponseEntity.ok(carService.update(id, carDto.getModel(), carDto.getOptionals()));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id){
	boolean isSuccess = carService.delete(id);
	return isSuccess ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
  }
  

}

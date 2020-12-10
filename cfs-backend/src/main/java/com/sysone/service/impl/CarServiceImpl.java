package com.sysone.service.impl;

import java.math.BigDecimal;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysone.contracts.StatsCar;
import com.sysone.entity.Car;
import com.sysone.entity.Model;
import com.sysone.entity.Optional;
import com.sysone.repository.CarRepository;
import com.sysone.repository.ModelRepository;
import com.sysone.repository.OptionalRepository;
import com.sysone.service.CarService;
import com.sysone.service.OptionalService;

import io.vavr.control.Try;

@Service
@Transactional
public class CarServiceImpl implements CarService {
  
  @Autowired
  CarRepository carRepository;
  @Autowired
  ModelRepository modelRepository;
  @Autowired
  OptionalService optionalService;
  @Autowired
  OptionalRepository optionalRepository;
    
  public boolean delete(long id){
	if(Boolean.FALSE.equals(carRepository.existsById(id)))
	  throw new IllegalArgumentException("No existe un Auto con este id:" + id);
	return Try.run(() -> carRepository.deleteById(id)).isSuccess();
  }
  
  public BigDecimal calculateCost(Long idModel, Set<Long> idsOptionals){
	
	if (idsOptionals == null || idsOptionals.isEmpty())
	  return modelRepository.getOne(idModel).getCost();
	return calculateCost(modelRepository.getOne(idModel), optionalService.getByIds(idsOptionals));
  }
  
  public StatsCar stats(){
	StatsCar statsCar = new StatsCar();
	statsCar.setCount_car(carRepository.count());
	statsCar.setCars(carRepository.statsModel());
	statsCar.setOptionals(carRepository.statsOptional());
	return statsCar;
  }
  
  public Car create(long idModel, Set<Long> idsOptionals){
	return saveCar(new Car(), idModel, idsOptionals);
  }
  
  public Car update(long id, long idModel, Set<Long> idsOptionals){
	return saveCar(carRepository.getOne(id), idModel, idsOptionals);
  }
  
  private Car saveCar(Car entity, long idModel, Set<Long> idsOptionals){
	
	Model model = modelRepository.getOne(idModel);
	entity.setModel(model);
	Set<Optional> optionals = optionalService.getByIds(idsOptionals);
	entity.setOptionals(optionals);
	entity.setPrice(calculateCost(model, optionals));
	return carRepository.save(entity);
  }
  
  
  private BigDecimal calculateCost(Model model, Set<Optional> optionals){
	
	if (optionals == null || optionals.isEmpty())
	  return model.getCost();
	return model.getCost().add(optionalService.sumCost(optionals));
  }
}

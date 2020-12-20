package com.sysone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sysone.contracts.StatsCar;
import com.sysone.repository.CarRepository;
import com.sysone.repository.StatsRepository;
import com.sysone.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService {

	@Autowired
	CarRepository carRepository;
	@Autowired
	StatsRepository statsRepository;

	public StatsCar stats() {
		StatsCar statsCar = new StatsCar();
		statsCar.setCount_car(carRepository.count());
		statsCar.setCars(statsRepository.model());
		statsCar.setOptionals(statsRepository.optional());
		return statsCar;
	}
}

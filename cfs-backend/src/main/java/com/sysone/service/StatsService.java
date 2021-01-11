package com.sysone.service;

import org.springframework.stereotype.Service;

import com.sysone.contracts.StatsCar;

@Service
public interface StatsService {

	  StatsCar stats();
}

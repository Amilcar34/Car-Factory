package com.sysone.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysone.contracts.StatsCar;
import com.sysone.service.StatsService;

@RestController
@RequestMapping("/stats")
public class StatsRest {
	
	@Autowired
	StatsService statsService;
	
	@GetMapping("/")
	public StatsCar stats() {
		return statsService.stats();
	}

}

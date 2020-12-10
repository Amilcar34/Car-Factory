package com.sysone.contracts;

import java.util.Set;

import lombok.Data;

@Data
public class StatsCar {
  
  long count_car;
  Set<StatsModel> cars;
  Set<StatsOptional> optionals;
  
}

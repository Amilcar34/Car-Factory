package com.sysone.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarDto {

  long model;
  Set<Long> optionals;
}

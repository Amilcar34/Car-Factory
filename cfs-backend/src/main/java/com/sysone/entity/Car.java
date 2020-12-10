package com.sysone.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
@Access(AccessType.FIELD)
public class Car {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne
  @JoinColumn(name = "id_model")
  private Model model;
  
  @ManyToMany
  @JoinTable(name = "optional_car", joinColumns = @JoinColumn(name = "id_car"), inverseJoinColumns = {
	  @JoinColumn(name = "id_optional") })
  private Set<Optional> optionals;
  
  private BigDecimal price;
}

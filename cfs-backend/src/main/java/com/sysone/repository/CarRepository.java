package com.sysone.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sysone.contracts.StatsModel;
import com.sysone.contracts.StatsOptional;
import com.sysone.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, CrudRepository<Car, Long>{

  @Query("SELECT DISTINCT c.model, "
  	+ " m.name as model,"
	+ " COUNT(c.model) AS count, "
	+ " 100 * COUNT(c.model) / (SELECT COUNT(*) FROM Car) AS percent"
  	+ " FROM Car AS c"
  	+ " JOIN c.model m"
  	+ " GROUP BY c.model, m.id ")
  Set<StatsModel> statsModel();

  @Query("SELECT op.name AS optional, "
  	+ " COUNT(op.id) AS count, "
  	+ " 100 * COUNT(op.id) / (SELECT COUNT(opt.id) FROM Car ca JOIN ca.optionals opt) AS percent "
  	+ " FROM Car c "
  	+ " JOIN c.optionals op "
  	+ " GROUP BY op.id")
  Set<StatsOptional> statsOptional();  
  
}

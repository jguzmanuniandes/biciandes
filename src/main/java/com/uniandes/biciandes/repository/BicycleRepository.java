package com.uniandes.biciandes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniandes.biciandes.model.Bicycle;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, Long>{
	
	List<Bicycle> findByUserEmail(String userId);

}

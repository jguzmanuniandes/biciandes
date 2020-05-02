package com.uniandes.biciandes.service;

import java.util.List;

import com.uniandes.biciandes.model.Bicycle;

public interface BicycleService {
	
	Bicycle saveBicycle(Bicycle bicycle);
	
	List<Bicycle> getBicycles();
	
	Bicycle getBicycle(Long id);
	
	List<Bicycle> getUserBicycles(String userId);

}

package com.uniandes.biciandes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.uniandes.biciandes.model.Bicycle;
import com.uniandes.biciandes.repository.BicycleRepository;
import com.uniandes.biciandes.service.BicycleService;

@Service
@Primary
public class BicycleServiceImpl implements BicycleService{
	
	private BicycleRepository bicycleRepository;
	
	@Autowired
	public BicycleServiceImpl(BicycleRepository bicycleRepository) {
		this.bicycleRepository = bicycleRepository;
	}

	@Override
	public Bicycle saveBicycle(Bicycle bicycle) {
		return bicycleRepository.save(bicycle);
	}

	@Override
	public List<Bicycle> getBicycles() {
		return bicycleRepository.findAll();
	}

	@Override
	public Bicycle getBicycle(Long id) {
		return bicycleRepository.findById(id).orElse(null);
	}

	@Override
	public List<Bicycle> getUserBicycles(String userId) {
		return bicycleRepository.findByUserEmail(userId);
	}

}

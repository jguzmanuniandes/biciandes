package com.uniandes.biciandes.repository;

import com.uniandes.biciandes.model.Photo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
	
	List<Photo> findByUserEmail(String userId);

}

package com.uniandes.biciandes.repository;


import com.uniandes.biciandes.model.Video;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
	
	List<Video> findByUserEmail(String userId);

}

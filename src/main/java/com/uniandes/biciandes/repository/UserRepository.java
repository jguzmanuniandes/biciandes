package com.uniandes.biciandes.repository;

import com.uniandes.biciandes.model.Group;
import com.uniandes.biciandes.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	public User findByEmail(String email);
	
}

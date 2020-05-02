package com.uniandes.biciandes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uniandes.biciandes.model.Group;
import com.uniandes.biciandes.model.User;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
	
	Group findById(Long id);

}

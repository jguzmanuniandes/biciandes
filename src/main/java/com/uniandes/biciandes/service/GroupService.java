package com.uniandes.biciandes.service;

import java.util.List;
import java.util.Set;

import com.uniandes.biciandes.model.Group;
import com.uniandes.biciandes.model.User;

public interface GroupService {
	
	Group saveGroup(Group group);
		
	Group findById(Long id);
	
	void delete(Group group);

	List<Group> findAll();
	
	List<User> findByGroup(Long id);
	
	User saveMembers(String email, Long id);
	
}


package com.uniandes.biciandes.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.uniandes.biciandes.model.Group;
import com.uniandes.biciandes.model.User;
import com.uniandes.biciandes.repository.GroupRepository;
import com.uniandes.biciandes.repository.UserRepository;
import com.uniandes.biciandes.service.GroupService;

@Service
@Primary
public class GroupServiceImpl implements GroupService{
	private GroupRepository groupRepository;
	private UserRepository userRepository;

	@Autowired
	public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
	}

	public List<Group> findAll() {
		List<Group> lista = new ArrayList<Group>(); 
		try {
			lista = groupRepository.findAll();			
		}catch(Exception e) {
			lista = new ArrayList<Group>();
		}
		
		return lista;
	}

	@Override
	public Group findById(Long id) {
		return groupRepository.findById(id);
	}

	@Override
	public void delete(Group group) {
		this.groupRepository.delete(group);
	}

	@Override
	public Group saveGroup(Group group) {
		// TODO Auto-generated method stub
		return groupRepository.save(group);
	}

	@Override
	public List<User> findByGroup(Long id) {
		return new ArrayList<>(findById(id).getMembers());
	}

	@Override
	public User saveMembers(String email, Long id) {
		Group group = new Group();
		Optional<User> user;
		/*group.setId(id);
		group.setDescription("");
		group.setIsPrivate(true);*/
		
		group = groupRepository.findById(id);
		
		user = userRepository.findById(email);
		
		user.get().getMemberGroup().add(group);
		group.getMembers().add(user.get());
		groupRepository.save(group);
		
		return user.get();
	}

	
}

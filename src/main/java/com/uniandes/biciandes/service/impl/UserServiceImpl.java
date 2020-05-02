package com.uniandes.biciandes.service.impl;

import com.uniandes.biciandes.model.User;
import com.uniandes.biciandes.repository.UserRepository;
import com.uniandes.biciandes.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

	@Override
	public List<User> findAll() {
        return userRepository.findAll();
    }

	public User getUser(String id) {
		return userRepository.findById(id).orElse(null);
	}
}

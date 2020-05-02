package com.uniandes.biciandes.service;

import java.util.List;

import com.uniandes.biciandes.model.User;

public interface UserService {

    User saveUser(User user);
    
    List<User> findAll();
    
    User getUser(String id);

}

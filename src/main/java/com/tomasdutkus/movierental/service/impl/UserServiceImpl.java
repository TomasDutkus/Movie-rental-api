package com.tomasdutkus.movierental.service.impl;

import com.tomasdutkus.movierental.dao.UserRepository;
import com.tomasdutkus.movierental.model.User;
import com.tomasdutkus.movierental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void deleteById(Long id) {

    }
}

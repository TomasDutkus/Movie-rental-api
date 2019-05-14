package com.tomasdutkus.movierental.service;

import com.tomasdutkus.movierental.model.User;

import java.util.List;

public interface UserService {

    public List<User> findAll();

    public User findById(Long id);

    public void save(User user);

    public void deleteById(Long id);
}

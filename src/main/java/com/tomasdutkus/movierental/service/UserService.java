package com.tomasdutkus.movierental.service;

import com.tomasdutkus.movierental.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {

    public User registerUser(User user);

    public Page<User> getAllUsers(PageRequest pageRequest);

    public User getUserById(Long id);

    public User getUserByEmailId(String emailid);

    public User updateUser(User user, Long id);

    public Boolean deleteUser(Long id);

}

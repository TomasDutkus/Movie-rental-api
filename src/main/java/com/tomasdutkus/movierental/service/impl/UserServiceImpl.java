package com.tomasdutkus.movierental.service.impl;

import com.tomasdutkus.movierental.dao.UserRepository;
import com.tomasdutkus.movierental.exception.UserAlreadyExistException;
import com.tomasdutkus.movierental.exception.UserNotFoundException;
import com.tomasdutkus.movierental.model.User;
import com.tomasdutkus.movierental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        try {
            getUserByEmailId(user.getEmail());
        } catch (UserNotFoundException rnfe) {
            return this.userRepository.save(user);
        }

        throw new UserAlreadyExistException(user.getEmail());
    }

    @Override
    public Page<User> getAllUsers(PageRequest pageRequest) {
        return this.userRepository.findAll(pageRequest);
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getUserByEmailId(String emailId) {
        return this.userRepository.findByEmail(emailId).orElseThrow(() -> new UserNotFoundException(emailId));
    }

    @Override
    public User updateUser(User user, Long id) {
        return (User) this.userRepository.findById(id).map(persistedUser -> {
            user.setId(id);
            user.setEnabled(persistedUser.getEnabled());
            return this.userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Boolean deleteUser(Long id) {
        this.userRepository.findById(id).map(user -> {
            this.userRepository.delete(user);
            return true;
        }).orElseThrow(() -> new UserNotFoundException(id));

        return false;
    }
}

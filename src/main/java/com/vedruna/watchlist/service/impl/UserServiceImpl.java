package com.vedruna.watchlist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vedruna.watchlist.exception.UserNotFoundException;
import com.vedruna.watchlist.persistance.model.User;
import com.vedruna.watchlist.persistance.repository.UserRepositoryI;
import com.vedruna.watchlist.service.UserServiceI;

@Service
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserRepositoryI userRepository;

    @Override
    public Page<User> selectAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User selectUserById(Integer userId) {
        return userRepository.findById(userId)
                                .orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public User selectUserByUsername(String username){
        return userRepository.findByUsername(username)
                                .orElseThrow(() -> new EmptyResultDataAccessException(
                                    "User not found", 
                            1));
    }

    @Override
    public Page<User> selectUserByUsernameStartingWith(String name, Pageable pageable) {
        return userRepository.findByUsernameStartingWith(name, pageable);
    }
    
}

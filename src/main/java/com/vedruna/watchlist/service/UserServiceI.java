package com.vedruna.watchlist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vedruna.watchlist.persistance.model.User;

public interface UserServiceI {
    Page<User> selectAllUsers(Pageable pageable);
    User selectUserById(Integer userId);
    User selectUserByUsername(String username);
    Page<User> selectUserByUsernameStartingWith(String name, Pageable pageable);
}
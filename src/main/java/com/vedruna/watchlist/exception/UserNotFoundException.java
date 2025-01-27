package com.vedruna.watchlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends EmptyResultDataAccessException {
    public UserNotFoundException(){
        super(
            "User not found", 
            1);
    }
}
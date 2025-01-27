package com.vedruna.watchlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Film not found")
public class FilmNotFoundException extends EmptyResultDataAccessException {
    public FilmNotFoundException(){
        super(
            "Film not found", 
            1);
    }
}

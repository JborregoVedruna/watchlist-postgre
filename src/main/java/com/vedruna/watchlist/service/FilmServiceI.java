package com.vedruna.watchlist.service;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vedruna.watchlist.dto.entitydto.EditTitleDTO;
import com.vedruna.watchlist.dto.entitydto.FilmDTO;
import com.vedruna.watchlist.dto.entitydto.UserIdDTO;
import com.vedruna.watchlist.persistance.model.Film;

public interface FilmServiceI {
    Page<Film> selectAllFilms(Pageable pageable);
    Film selectFilmById(Integer filmId);
    Page<Film> selectFilmsByReleaseDate(Date fromDate, Date toDate, Pageable pageable);
    Page<Film> selectFilmsByUserWatched(Integer userId, Pageable pageable);
    Film insertFilm(FilmDTO filmDTO);
    Film updateFilm(Integer filmId, FilmDTO filmDTO);
    Film updateFilmTitle(Integer filmId, EditTitleDTO title);
    void deleteFilm(Integer filmId);
    Film markAsWatched(Integer filmId, UserIdDTO userId);
    void markAsUnwatched(Integer filmId, UserIdDTO userId);
}

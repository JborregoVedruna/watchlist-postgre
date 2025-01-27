package com.vedruna.watchlist.service.impl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vedruna.watchlist.dto.entitydto.EditTitleDTO;
import com.vedruna.watchlist.dto.entitydto.FilmDTO;
import com.vedruna.watchlist.dto.entitydto.UserIdDTO;
import com.vedruna.watchlist.exception.FilmNotFoundException;
import com.vedruna.watchlist.exception.UserNotFoundException;
import com.vedruna.watchlist.persistance.model.Film;
import com.vedruna.watchlist.persistance.model.User;
import com.vedruna.watchlist.persistance.repository.FilmRepositoryI;
import com.vedruna.watchlist.persistance.repository.UserRepositoryI;
import com.vedruna.watchlist.service.FilmServiceI;

@Service
public class FilmServiceImpl implements FilmServiceI {

    @Autowired
    private FilmRepositoryI filmRepository;

    @Autowired
    private UserRepositoryI userRepository;

    @Override
    public Page<Film> selectAllFilms(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    @Override
    public Film selectFilmById(Integer filmId) {
        return filmRepository.findById(filmId)
                        .orElseThrow(() -> new FilmNotFoundException());
    }

    @Override
    public Page<Film> selectFilmsByReleaseDate(Date fromDate, Date toDate, Pageable pageable) {
        return filmRepository.findByReleaseDateBetween(fromDate, toDate, pageable);
    }

    @Override
    public Film insertFilm(FilmDTO filmDTO) {
        Film film = new Film();
        film.setTitle(filmDTO.getTitle());
        film.setReleaseDate(filmDTO.getReleaseDate());

        return filmRepository.save(film);
    }

    @Override
    public Film updateFilm(Integer filmId, FilmDTO filmDTO) {
        Film film = filmRepository.findById(filmId)
                                    .orElseThrow(() -> new FilmNotFoundException());

        film.setTitle(filmDTO.getTitle());
        film.setReleaseDate(filmDTO.getReleaseDate());
        return filmRepository.save(film);  
    }

    @Override
    public Film updateFilmTitle(Integer filmId, EditTitleDTO title) {
        Film film = filmRepository.findById(filmId)
                                    .orElseThrow(() -> new FilmNotFoundException());

        film.setTitle(title.getTitle());
        return filmRepository.save(film);  
    }

    @Override
    public void deleteFilm(Integer filmId) {
        filmRepository.deleteById(filmId);
    }

    @Override
    public Page<Film> selectFilmsByUserWatched(Integer userId, Pageable pageable) {
        return filmRepository.findFilmByUser(userId, pageable);
    }

    @Override
    public Film markAsWatched(Integer filmId, UserIdDTO userId) {
        Film film = filmRepository.findById(filmId)
                                    .orElseThrow(() -> new FilmNotFoundException());
        User userDB = userRepository.findById(userId.getUserId())
                                        .orElseThrow(() -> new UserNotFoundException()
                                        );
        film.getUsersWatchedThisFilm().add(userDB);
        userDB.getFilmsWatchedByThisUser().add(film);
        return filmRepository.save(film);
    }

    @Override
    public void markAsUnwatched(Integer filmId, UserIdDTO userId){
        Film film = filmRepository.findById(filmId)
                                    .orElseThrow(() -> new FilmNotFoundException());
        User userDB = userRepository.findById(userId.getUserId())
                                        .orElseThrow(() -> new FilmNotFoundException());
        film.getUsersWatchedThisFilm().remove(userDB);
        userDB.getFilmsWatchedByThisUser().remove(film);
        filmRepository.save(film);
    }
    
}

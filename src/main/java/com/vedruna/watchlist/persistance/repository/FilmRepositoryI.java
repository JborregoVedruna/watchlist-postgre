package com.vedruna.watchlist.persistance.repository;
import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vedruna.watchlist.persistance.model.Film;

@Repository
public interface FilmRepositoryI extends JpaRepository<Film, Integer> {
    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Page<Film> findByReleaseDateBetween(Date fromDate, Date toDate, Pageable pageable);
    @Query("SELECT f FROM Film f JOIN f.usersWatchedThisFilm u WHERE u.userId = :id")
    Page<Film> findFilmByUser(@Param("id") Integer id, Pageable pageable);
} 

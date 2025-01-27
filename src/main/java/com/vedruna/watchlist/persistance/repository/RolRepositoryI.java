package com.vedruna.watchlist.persistance.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedruna.watchlist.persistance.model.Rol;

@Repository
public interface RolRepositoryI extends JpaRepository<Rol, Integer> {
    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Optional<Rol> findByRolName(String rolName);
} 

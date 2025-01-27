package com.vedruna.watchlist.dto.mapper;

import org.mapstruct.Mapper;

import com.vedruna.watchlist.dto.entitydto.FilmDTO;
import com.vedruna.watchlist.persistance.model.Film;

@Mapper(componentModel = "spring")
public interface FilmMapper extends BaseMapper<Film, FilmDTO> {
    
}

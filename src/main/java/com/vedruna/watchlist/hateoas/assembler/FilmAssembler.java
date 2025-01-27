package com.vedruna.watchlist.hateoas.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Date;

import com.vedruna.watchlist.controller.rest.FilmController;
import com.vedruna.watchlist.dto.entitydto.EditTitleDTO;
import com.vedruna.watchlist.dto.entitydto.FilmDTO;
import com.vedruna.watchlist.dto.mapper.FilmMapper;
import com.vedruna.watchlist.persistance.model.Film;

@Component
public class FilmAssembler extends RepresentationModelAssemblerSupport<Film, FilmDTO>{
    
    @Autowired
    private FilmMapper filmMapper;

    public FilmAssembler() {
		super(FilmController.class, FilmDTO.class);
	}

    @Override
    public FilmDTO toModel(Film entity) {
        FilmDTO filmDTO = filmMapper.entityToDTO(entity);

        filmDTO.add(
            linkTo(methodOn(FilmController.class).getAllFilms(null)).withRel("collection").withType("GET"),
            linkTo(methodOn(FilmController.class).getFilmById(filmDTO.getFilmId())).withSelfRel().withType("GET"),
            linkTo(methodOn(FilmController.class).deleteFilmById(filmDTO.getFilmId())).withSelfRel().withType("DELETE"),
            linkTo(methodOn(FilmController.class).putFilmById(filmDTO.getFilmId(), filmDTO)).withSelfRel().withType("PUT"),
            linkTo(methodOn(FilmController.class).patchTitleByFilmId(filmDTO.getFilmId(), new EditTitleDTO(filmDTO.getTitle()))).withSelfRel().withType("PATCH"),
            linkTo(methodOn(FilmController.class).postFilm(filmDTO)).withRel("class").withType("POST")
        );

        return filmDTO;
    }

    @Override
    public CollectionModel<FilmDTO> toCollectionModel(Iterable<? extends Film> entities) {
        CollectionModel<FilmDTO> collectionModel = super.toCollectionModel(entities);

        collectionModel.add(
            linkTo(methodOn(FilmController.class).getAllFilms(null)).withSelfRel(),
            linkTo(methodOn(FilmController.class).getFilmsByReleaseDate(null, new Date(System.currentTimeMillis()), null)).withRel("filter-by-release-date").withType("GET"),
            linkTo(methodOn(FilmController.class).getFilmsByUserWatched(1,null)).withRel("filter-by-user-watched").withType("GET")
        );

        return collectionModel;
    }
    
}

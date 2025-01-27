package com.vedruna.watchlist.controller.rest;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.watchlist.dto.entitydto.EditTitleDTO;
import com.vedruna.watchlist.dto.entitydto.FilmDTO;
import com.vedruna.watchlist.dto.entitydto.UserIdDTO;
import com.vedruna.watchlist.hateoas.assembler.FilmAssembler;
import com.vedruna.watchlist.persistance.model.Film;
import com.vedruna.watchlist.service.FilmServiceI;
import com.vedruna.watchlist.swagger.annotation.GeneralApiDoc;
import com.vedruna.watchlist.swagger.schema.Examples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/film")
@Tag(name = "Films", description = "Film operations")
public class FilmController {

    @Autowired
    private FilmServiceI filmService;

    @Autowired
    FilmAssembler filmAssembler;

    @Autowired
	private PagedResourcesAssembler<Film> pagedResourcesAssembler;

    @Operation(summary = "Insert a film", description = "Insert a film in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Film inserted successfully", content = {
                @Content(mediaType = "application/hal+json", schema = @Schema(implementation = FilmDTO.class))
        }),
        @ApiResponse(responseCode = "409", description = "Film already exists", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
        })
    })
    @GeneralApiDoc
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The Film to insert", required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilmDTO.class), 
        examples = {
            @ExampleObject(name = "Film Sample", description = "A sample film", value = Examples.FILM_SAMPLE)
        })
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public ResponseEntity<FilmDTO> postFilm(@RequestBody @Valid FilmDTO filmDTO) {
        Film film = filmService.insertFilm(filmDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            filmAssembler.toModel(film)
        ); 
    }

    @Operation(summary = "Get all films", description = "Return all films in paginated format")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved films", content = {
            @Content(mediaType = "application/hal+json", schema = @Schema(implementation = PagedModel.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "pageable", description = "Pageable parameters", required = false,
            schema = @Schema(implementation = Pageable.class),
            example = Examples.FILM_PAGEABLE_SAMPLE
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public ResponseEntity<PagedModel<FilmDTO>> getAllFilms(Pageable pageable) {
        Page<Film> films = filmService.selectAllFilms(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(films, filmAssembler));
    }

    @Operation(summary = "Get all films in a range of dates", description = "Return all films in a range of dates in paginated format")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved films in the date range", content = {
            @Content(mediaType = "application/hal+json", schema = @Schema(implementation = PagedModel.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "from", description = "Date to search from", required = false,
            schema = @Schema(implementation = Date.class), example = Examples.FILM_RELEASE_DATE_SAMPLE
        ),
        @Parameter(
            name = "to", description = "Date to search until", required = false,
            schema = @Schema(implementation = Date.class), example = Examples.FILM_RELEASE_DATE_SAMPLE_2
        ),
        @Parameter(
            name = "pageable", description = "Pageable parameters", required = false,
            schema = @Schema(implementation = Pageable.class),
            example = Examples.FILM_PAGEABLE_SAMPLE
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/release_date")
    public ResponseEntity<PagedModel<FilmDTO>> getFilmsByReleaseDate(@RequestParam("from") Date fromDate, @RequestParam("to")Date toDate, Pageable pageable) {
        Page<Film> films = filmService.selectFilmsByReleaseDate(fromDate, toDate, pageable);
        return ResponseEntity.ok(
            pagedResourcesAssembler.toModel(films, filmAssembler)
        );
    }
    
    @Operation(summary = "Get film by id", description = "Return the film with the given id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the film", content = {
            @Content(mediaType = "application/hal+json", schema = @Schema(implementation = FilmDTO.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "filmId", description = "Id of the film to search", required = true,
            schema = @Schema(type = "integer"), example = "1"
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{filmId}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable Integer filmId) {
        Film film = filmService.selectFilmById(filmId);
        return ResponseEntity.ok(filmAssembler.toModel(film));
    }

    @Operation(summary = "Edit film by id", description = "Edit the film with the given id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Film updated successfully", content = {
            @Content(mediaType = "application/hal+json", schema = @Schema(implementation = FilmDTO.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "filmId", description = "Id of the film to update", required = true,
            schema = @Schema(type = "integer"), example = "1"
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The Film to update", required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = FilmDTO.class), 
        examples = {
            @ExampleObject(name = "Film Sample", description = "A sample film", value = Examples.FILM_SAMPLE)
        })
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{filmId}")
    public ResponseEntity<FilmDTO> putFilmById(@PathVariable Integer filmId, @RequestBody @Valid FilmDTO filmDTO) {
        Film film = filmService.updateFilm(filmId, filmDTO);
        return ResponseEntity.ok(filmAssembler.toModel(film));
    }

    @Operation(summary = "Edit film title by id", description = "Edit the title of the film with the given id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Title of the film updated successfully", content = {
            @Content(mediaType = "application/hal+json", schema = @Schema(implementation = FilmDTO.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "filmId", description = "Id of the film to update", required = true,
            schema = @Schema(type = "integer"), example = "2"
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The new title for the film", required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = EditTitleDTO.class), 
        examples = {
            @ExampleObject(name = "Title Sample", description = "A sample title", value = Examples.EDIT_TITLE_SAMPLE)
        })
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{filmId}")
    public ResponseEntity<FilmDTO> patchTitleByFilmId(@PathVariable Integer filmId, @RequestBody EditTitleDTO title) {
        Film film = filmService.updateFilmTitle(filmId, title);
        return ResponseEntity.ok(filmAssembler.toModel(film));
    }

    @Operation(summary = "Delete film by id", description = "Delete the film with the given id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Film deleted successfully", content = {
                @Content(mediaType = "application/json")
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "filmId", description = "Id of the film to delete", required = true,
            schema = @Schema(type = "integer"), example = "1"
        )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{filmId}")
    public ResponseEntity deleteFilmById(@PathVariable Integer filmId) {
        filmService.deleteFilm(filmId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Get all films watched by a user", description = "Return all films watched by a user in paginated format")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved films watched by the user", content = {
            @Content(mediaType = "application/hal+json", schema = @Schema(implementation = PagedModel.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "userId", description = "Id of the user whose movies you want to get", required = true,
            schema = @Schema(type = "integer"), example = "1"
        
        ),
        @Parameter(
            name = "pageable", description = "Pageable parameters", required = false,
            schema = @Schema(implementation = Pageable.class),
            example = Examples.FILM_PAGEABLE_SAMPLE
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{userId:\\d+}")
    public ResponseEntity<PagedModel<FilmDTO>> getFilmsByUserWatched(@PathVariable Integer userId, Pageable pageable) {
        Page<Film> films = filmService.selectFilmsByUserWatched(userId, pageable);
        return ResponseEntity.ok(
            pagedResourcesAssembler.toModel(films, filmAssembler)
        );
    }

    @Operation(summary = "Check film as watched", description = "Chek the film with the given id as watched")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Film checked successfully", content = {
                @Content(mediaType = "application/hal+json", schema = @Schema(implementation = FilmDTO.class))
        }),
        @ApiResponse(responseCode = "409", description = "The user has already watched this film", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "filmId", description = "Id of the film to check as watched", required = true,
            schema = @Schema(type = "integer"), example = "2"
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The userId of the user who want to check the film", required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserIdDTO.class), 
        examples = {
            @ExampleObject(name = "UserId Sample", description = "A sample userId", value = Examples.USER_ID_DTO_SAMPLE)
        })
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/check/{filmId}")
    public ResponseEntity<FilmDTO> checkFilm(@PathVariable Integer filmId, @RequestBody UserIdDTO userId) {
        Film film = filmService.markAsWatched(filmId, userId);
        return ResponseEntity.ok(filmAssembler.toModel(film));
    }

    @Operation(summary = "Check film as unwatched", description = "Check the film with the given id as unwatched")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Film unchecked successfully", content = {
                @Content(mediaType = "application/json")
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "filmId", description = "Id of the film to uncheck", required = true,
            schema = @Schema(type = "integer"), example = "2"
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The userId of the user who want to uncheck the film", required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserIdDTO.class), 
        examples = {
            @ExampleObject(name = "UserId Sample", description = "A sample userId", value = Examples.USER_ID_DTO_SAMPLE)
        })
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/uncheck/{filmId}")
    public ResponseEntity<FilmDTO> uncheckFilm(@PathVariable Integer filmId, @RequestBody UserIdDTO userId) {
        filmService.markAsUnwatched(filmId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

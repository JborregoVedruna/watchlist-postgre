package com.vedruna.watchlist.dto.entitydto;

import java.sql.Date;

import org.springframework.hateoas.RepresentationModel;

import com.vedruna.watchlist.swagger.schema.Examples;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Represents a film", example = Examples.FILM_SAMPLE)
@NoArgsConstructor
@Getter
@Setter
@Data
public class FilmDTO extends RepresentationModel<FilmDTO> {

    @Schema(description = "The id of the film on database",
            example = "0", defaultValue = "0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    Integer filmId;

    @Schema(description = "The title of the film",
            example = Examples.FILM_TITLE_SAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Title is required and must not be blank")
    //@Size(max = 45, message = "Title must be at most 45 characters long") //Comentado para probar excepciones
    String title;
    
    @Schema(description = "The release date of the film",
            example = Examples.FILM_RELEASE_DATE_SAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Release date is required and must not be blank")
    @PastOrPresent(message = "Release date must be in the past or present")
    Date releaseDate;
}

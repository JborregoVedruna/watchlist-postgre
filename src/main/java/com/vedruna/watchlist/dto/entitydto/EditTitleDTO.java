package com.vedruna.watchlist.dto.entitydto;

import com.vedruna.watchlist.swagger.schema.Examples;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Represents the title of a film to be edited", example = Examples.EDIT_TITLE_SAMPLE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditTitleDTO {
    @Schema(description = "The title of the film", example = Examples.FILM_TITLE_SAMPLE_2, 
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Title is required and must not be blank")
    @Size(max = 45, message = "Title must be at most 45 characters long")
    String title;
}

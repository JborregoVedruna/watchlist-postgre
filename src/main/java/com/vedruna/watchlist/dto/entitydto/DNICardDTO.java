package com.vedruna.watchlist.dto.entitydto;

import org.springframework.hateoas.RepresentationModel;

import com.vedruna.watchlist.swagger.schema.Examples;
import com.vedruna.watchlist.validation.DNI;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Represents a DNI document", example = Examples.DNI_SAMPLE)
@NoArgsConstructor
@Getter
@Setter
@Data
public class DNICardDTO extends RepresentationModel<DNICardDTO> {

    @Schema(description = "The id of the DNI document on database", 
            example = "0", defaultValue = "0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    Integer dniId;

    @Schema(description = "The DNI number of the document", 
            example = Examples.DNI_NUMBER_SAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "DNI number is required and must not be blank")
    @Size(min = 9, max = 9, message = "DNI number must be 9 characters long")
    @Pattern(regexp = "^\\d{8}[A-Z]$")
    @DNI(message = "DNI number is not valid")
    String number;

    @Schema(description = "The path to the front image of the DNI document", 
            example = Examples.PATH_SAMPLE, requiredMode = Schema.RequiredMode.REQUIRED) 
    @NotBlank(message = "Front image is required and must not be blank")
    String frontImg;

    @Schema(description = "The path to the back image of the DNI document", 
            example = Examples.PATH_SAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Back image is required and must not be blank")
    String backImg;
    
    @Schema(description = "The id of the user that owns the DNI document", 
            example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    Integer dniOwnerId;
}

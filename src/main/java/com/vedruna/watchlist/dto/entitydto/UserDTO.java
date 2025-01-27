package com.vedruna.watchlist.dto.entitydto;

import org.springframework.hateoas.RepresentationModel;

import com.vedruna.watchlist.swagger.schema.Examples;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.examples.Example;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Represents an User of the system")
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDTO extends RepresentationModel<UserDTO>{

    @Schema(description = "The id of the user on database",
            example = "0", defaultValue = "0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    Integer userId;

    @Schema(description = "The username of the user",
            example = Examples.USERNAME_SAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Username is required and must not be blank")
    @Size(min=3, max = 20, message = "Username must be between 3 and 20 characters long")
    String username;

    @Schema(description = "The email of the user",
            example = Examples.EMAIL_SAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email is required and must not be blank")
    @Email(message = "Please provide a valid email address")
    @Size(max = 90, message = "Email must be at most 90 characters long")
    String email;

    @Schema(description = "The rol of the user",
            example = "USER", defaultValue = "USER", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Rol is required and must not be blank")
    String rolName;
    
    @Schema(description = "The DNI document of the user", 
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    DNICardDTO document;
}

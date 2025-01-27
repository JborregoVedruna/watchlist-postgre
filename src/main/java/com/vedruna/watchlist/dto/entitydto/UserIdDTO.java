package com.vedruna.watchlist.dto.entitydto;

import com.vedruna.watchlist.swagger.schema.Examples;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Represents the id of the user who wants to mark a film as watched/unwatched", example = Examples.USER_ID_DTO_SAMPLE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserIdDTO {
    @Schema(description = "The id of the user on database", example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    Integer userId;
}

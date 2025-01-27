package com.vedruna.watchlist.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.watchlist.dto.entitydto.UserDTO;
import com.vedruna.watchlist.hateoas.assembler.UserAssembler;
import com.vedruna.watchlist.persistance.model.User;
import com.vedruna.watchlist.service.UserServiceI;
import com.vedruna.watchlist.swagger.annotation.GeneralApiDoc;
import com.vedruna.watchlist.swagger.schema.Examples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Users", description = "User GET operations")
public class UserController {

    @Autowired
    UserServiceI userService;

    @Autowired
    UserAssembler userAssembler;

    @Autowired
	private PagedResourcesAssembler<User> pagedResourcesAssembler;

    @Operation(summary = "Get all users", description = "Return all users in paginated format")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved users", content = {
            @Content(mediaType = "application/hal+json", schema = @Schema(implementation = PagedModel.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "pageable", description = "Pageable parameters", required = false,
            schema = @Schema(implementation = Pageable.class),
            example = Examples.USER_PAGEABLE_SAMPLE
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public ResponseEntity<PagedModel<UserDTO>> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        Page<User> users = userService.selectAllUsers(pageable);
        return ResponseEntity.ok(
            pagedResourcesAssembler.toModel(users, userAssembler)
        );
    }

    @Operation(summary = "Get users by name starts with", description = "Return users whose name starts with the given name in paginated format")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved users", content = {
            @Content(mediaType = "application/hal+json", schema = @Schema(implementation = PagedModel.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "name", description = "Name to search", required = true,
            schema = @Schema(type = "string"), example = Examples.USERNAME_SAMPLE
        ),
        @Parameter(
            name = "pageable", description = "Pageable parameters", required = false,
            schema = @Schema(implementation = Pageable.class),
            example = Examples.USER_PAGEABLE_SAMPLE
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public ResponseEntity<PagedModel<UserDTO>> getUserByUsernameStartingWith(@RequestParam String name, @PageableDefault(size = 5) Pageable pageable) {
        Page<User> users = userService.selectUserByUsernameStartingWith(name, pageable);
        return ResponseEntity.ok(
            pagedResourcesAssembler.toModel(users, userAssembler)
        );
    }

    @Operation(summary = "Get user by name", description = "Return the user whose name is exactly the given one")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the user", content = {
            @Content(mediaType = "application/hal+json", schema = @Schema(implementation = UserDTO.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "name", description = "Name to search", required = true,
            schema = @Schema(type = "string"), example = Examples.USERNAME_SAMPLE_INC
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/profile/{name}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String name) {
        User user = userService.selectUserByUsername(name);
        return ResponseEntity.ok(
            userAssembler.toModel(user)
        );
    }
    @Operation(summary = "Get user by id", description = "Return the user with the given id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the user", content = {
            @Content(mediaType = "application/hal+json", schema = @Schema(implementation = UserDTO.class))
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "userId", description = "Id of the user to search", required = true,
            schema = @Schema(type = "integer"), example = "1"
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId) {
        User user = userService.selectUserById(userId);
        return ResponseEntity.ok(
            userAssembler.toModel(user)
        );
    }
    
}

package com.vedruna.watchlist.hateoas.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.vedruna.watchlist.controller.rest.FilmController;
import com.vedruna.watchlist.controller.rest.UserController;
import com.vedruna.watchlist.dto.entitydto.UserDTO;
import com.vedruna.watchlist.dto.mapper.UserMapper;
import com.vedruna.watchlist.persistance.model.User;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserDTO>{

    @Autowired
    private UserMapper userMapper;

    public UserAssembler() {
        super(UserController.class, UserDTO.class);
    }

    @Override
    public UserDTO toModel(User entity) {
        UserDTO user = userMapper.entityToDTO(entity);

        user.add(
                linkTo(methodOn(UserController.class).getAllUsers(null)).withRel("collection").withType("GET"),
                linkTo(methodOn(UserController.class).getUserById(entity.getUserId())).withSelfRel().withType("GET"),
                linkTo(methodOn(FilmController.class).getFilmsByUserWatched(entity.getUserId(), null)).withRel("films-watched").withType("GET")
                );
        return user;
    }

    @Override
    public CollectionModel<UserDTO> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserDTO> collectionModel = super.toCollectionModel(entities);

        collectionModel.add(
                linkTo(methodOn(UserController.class).getAllUsers(null)).withSelfRel(),
                linkTo(methodOn(UserController.class).getUserByUsernameStartingWith(null, null)).withRel("filter-by-username").withType("GET")
                );

        return collectionModel;
    }
    
}

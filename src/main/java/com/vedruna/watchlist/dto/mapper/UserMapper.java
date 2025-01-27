package com.vedruna.watchlist.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vedruna.watchlist.dto.entitydto.UserDTO;
import com.vedruna.watchlist.persistance.model.User;

@Mapper(componentModel = "spring", uses = {DNICardMapper.class})
public interface UserMapper extends BaseMapper<User, UserDTO>{

    @Mapping(source="userRol.rolName", target="rolName")
    @Mapping(source="document", target="document")
    UserDTO entityToDTO(User entity);
    
}

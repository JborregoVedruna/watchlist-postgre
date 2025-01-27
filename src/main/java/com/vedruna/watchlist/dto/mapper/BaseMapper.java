package com.vedruna.watchlist.dto.mapper;

import java.util.List;

import org.springframework.data.domain.Page;

public interface BaseMapper<Entity, EntityDTO> {
    EntityDTO entityToDTO(Entity entity);
    //Entity dtoToEntity(EntityDTO dto);
    List<EntityDTO> entitiesToDTOs(List<Entity> entities);
    default Page<EntityDTO> pageEntitiesToDTOs(Page<Entity> entities) {
        return entities.map(this::entityToDTO);
    }
}

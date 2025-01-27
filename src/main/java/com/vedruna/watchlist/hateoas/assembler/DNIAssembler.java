package com.vedruna.watchlist.hateoas.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.vedruna.watchlist.controller.rest.DNIController;
import com.vedruna.watchlist.dto.entitydto.DNICardDTO;
import com.vedruna.watchlist.dto.mapper.DNICardMapper;
import com.vedruna.watchlist.persistance.model.DNICard;

@Component
public class DNIAssembler extends RepresentationModelAssemblerSupport<DNICard, DNICardDTO> {

    @Autowired
    private DNICardMapper dniMapper;

    public DNIAssembler() {
        super(DNIController.class, DNICardDTO.class);
    }

    @Override
    public DNICardDTO toModel(DNICard entity) {
        DNICardDTO dni = dniMapper.entityToDTO(entity);

        dni.add(
            linkTo(methodOn(DNIController.class).deleteDNIById(dni.getDniId())).withSelfRel().withType("DELETE"),
            linkTo(methodOn(DNIController.class).postDNI(dni)).withRel("class").withType("POST")
        );

        return dni;
    }
    
}

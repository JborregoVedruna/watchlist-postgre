package com.vedruna.watchlist.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.watchlist.dto.entitydto.DNICardDTO;
import com.vedruna.watchlist.hateoas.assembler.DNIAssembler;
import com.vedruna.watchlist.persistance.model.DNICard;
import com.vedruna.watchlist.service.DNIServiceI;
import com.vedruna.watchlist.swagger.annotation.GeneralApiDoc;
import com.vedruna.watchlist.swagger.schema.Examples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/DNI")
@Tag(name = "DNIs", description = "DNI operations")
public class DNIController {

    @Autowired
    private DNIServiceI dniService;

    @Autowired
    private DNIAssembler dniAssembler;

    @Operation(summary = "Insert a DNI", description = "Insert a DNI in the database")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "DNI inserted successfully", content = {
                @Content(mediaType = "application/hal+json", schema = @Schema(implementation = DNICardDTO.class))
        }),
        @ApiResponse(responseCode = "409", description = "DNI already exists", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
        })
    })
    @GeneralApiDoc
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The DNI document to insert", required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = DNICardDTO.class),
        examples = {@ExampleObject(name = "DNISample", description = "A sample DNI document", value = Examples.DNI_SAMPLE)})
    )
    @PostMapping("/")
    public ResponseEntity<DNICardDTO> postDNI(@RequestBody @Valid DNICardDTO document) {
        DNICard dni = dniService.insertDNI(document);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            dniAssembler.toModel(dni)
        );
        
    }

    @Operation(summary = "Delete DNI by id", description = "Delete the DNI with the given id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "DNI deleted successfully", content = {
                @Content(mediaType = "application/json")
        })
    })
    @GeneralApiDoc
    @Parameters( value = {
        @Parameter(
            name = "dniCardId", description = "Id of the DNI to delete", required = true,
            schema = @Schema(type = "integer"), example = "1"
        )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{dniCardId}")
    public ResponseEntity deleteDNIById(@PathVariable Integer dniCardId) {
        dniService.deleteDNI(dniCardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}

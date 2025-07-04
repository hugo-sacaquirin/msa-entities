package com.sipeip.controller;


import com.sipeip.infrastructure.input.adapter.rest.EntitiesApi;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityResultResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityUpdateRequest;
import com.sipeip.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EntityController implements EntitiesApi {
    private final EntityService entityService;


    @Override
    public ResponseEntity<Void> deleteEntity(Integer id) {
        entityService.deleteEntityById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<EntityResultResponse> updateEntity(Integer id, EntityUpdateRequest entityUpdateRequest) {
        return new ResponseEntity<>(entityService.updateEntity(id, entityUpdateRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<EntityPagedResponse> getPagedEntities(Integer page, Integer size) {
        return new ResponseEntity<>(entityService.getEntities(page, size), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityResultResponse> createEntity(EntityRequest entityCreateRequest) {
        return new ResponseEntity<>(entityService.createEntity(entityCreateRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<EntityPagedResponse> searchEntities(Integer page, Integer size, String name, String code, String type) {
        return new ResponseEntity<>(entityService.searchEntities(page, size, name, code, type), HttpStatus.OK);
    }
}

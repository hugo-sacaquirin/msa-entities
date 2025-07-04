package com.sipeip.controller;

import com.sipeip.infrastructure.input.adapter.rest.models.EntityPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityResultResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityUpdateRequest;
import com.sipeip.service.EntityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

class EntityControllerTest {

    @Test
    void deleteEntityReturnsNoContent() {
        EntityService mockService = Mockito.mock(EntityService.class);
        EntityController controller = new EntityController(mockService);

        ResponseEntity<Void> response = controller.deleteEntity(1);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(mockService).deleteEntityById(1);
    }

    @Test
    void updateEntityReturnsCreatedStatus() {
        EntityService mockService = Mockito.mock(EntityService.class);
        EntityUpdateRequest request = new EntityUpdateRequest("Updated Name", "Updated Code", "Updated SubSector", "Updated Level", "Active");
        EntityResultResponse mockResponse = new EntityResultResponse();
        mockResponse.setResult("Entity updated successfully");
        mockResponse.setCode("204");

        Mockito.when(mockService.updateEntity(Mockito.eq(1), Mockito.eq(request))).thenReturn(mockResponse);

        EntityController controller = new EntityController(mockService);
        ResponseEntity<EntityResultResponse> response = controller.updateEntity(1, request);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(mockResponse, response.getBody());
    }

    @Test
    void getEntitiesReturnsPagedResponse() {
        EntityService mockService = Mockito.mock(EntityService.class);
        EntityPagedResponse mockResponse = new EntityPagedResponse();
        mockResponse.setContent(Collections.emptyList());
        Mockito.when(mockService.getEntities(Mockito.eq(0), Mockito.eq(10))).thenReturn(mockResponse);

        EntityController controller = new EntityController(mockService);
        ResponseEntity<EntityPagedResponse> response = controller.getPagedEntities(0, 10);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(mockResponse, response.getBody());
    }

    @Test
    void createEntityReturnsCreatedStatus() {
        EntityService mockService = Mockito.mock(EntityService.class);
        EntityRequest request = new EntityRequest("Test Name", "Test Code", "Test SubSector", "Test Level", "Active");
        EntityResultResponse mockResponse = new EntityResultResponse();
        mockResponse.setResult("Entity created successfully");
        mockResponse.setCode("204");
        Mockito.when(mockService.createEntity(Mockito.eq(request))).thenReturn(mockResponse);

        EntityController controller = new EntityController(mockService);
        ResponseEntity<EntityResultResponse> response = controller.createEntity(request);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(mockResponse, response.getBody());
    }

    @Test
    void searchEntitiesReturnsWhenReturnOk() {
        EntityService mockService = Mockito.mock(EntityService.class);
        EntityController controller = new EntityController(mockService);

        ResponseEntity<EntityPagedResponse> response = controller.searchEntities(0, 10, "name", "code", "0");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
package com.sipeip.service;

import com.sipeip.domain.entity.Entity;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityResultResponse;
import com.sipeip.repository.EntityRepository;
import com.sipeip.service.impl.EntityServiceImpl;
import com.sipeip.service.mapper.EntitiesMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

class EntityServiceImplTest {

    @Test
    void givenValidRequestWhenCreateEntityThenReturnsSuccess() {
        EntityRequest request = new EntityRequest("Test Name", "Test Code", "Test SubSector", "Test Level", "ACTIVO");
        Entity savedEntity = Entity.builder()
                .id(1)
                .name(request.getName())
                .code(request.getCode())
                .subSector(request.getSubSector())
                .status(request.getStatus())
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .level(request.getLevel())
                .build();

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.save(Mockito.any(Entity.class))).thenReturn(savedEntity);

        EntitiesMapper mockMapper = Mockito.mock(EntitiesMapper.class);
        EntityServiceImpl service = new EntityServiceImpl(mockRepository, mockMapper);

        EntityResultResponse response = service.createEntity(request);

        Assertions.assertEquals("Entity created successfully", response.getResult());
        Assertions.assertEquals("204", response.getCode());
    }

    @Test
    void givenNullIdWhenCreateEntityThenThrowsException() {
        EntityRequest request = new EntityRequest("Test Name", "Test Code", "Test SubSector", "Test Level", "INACTIVO");
        Entity savedEntity = Entity.builder()
                .name(request.getName())
                .code(request.getCode())
                .subSector(request.getSubSector())
                .status(request.getStatus())
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .level(request.getLevel())
                .build();

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.save(Mockito.any(Entity.class))).thenReturn(savedEntity);

        EntitiesMapper mockMapper = Mockito.mock(EntitiesMapper.class);
        EntityServiceImpl service = new EntityServiceImpl(mockRepository, mockMapper);

        Assertions.assertThrows(ResponseStatusException.class, () -> service.createEntity(request));
    }

    @Test
    void givenNoEntitiesWhenGetEntitiesThenReturnsEmptyList() {
        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        EntitiesMapper mockMapper = Mockito.mock(EntitiesMapper.class);
        Mockito.when(mockMapper.toEntityResponseFromEntities(Mockito.anyList())).thenReturn(Collections.emptyList());

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, mockMapper);

        EntityPagedResponse response = service.getEntities(0, 10);

        Assertions.assertTrue(response.getContent().isEmpty());
    }

    @Test
    void givenEntitiesExistWhenGetEntitiesThenReturnsMappedEntities() {
        Entity entity = Entity.builder()
                .id(1)
                .name("Test Name")
                .code("Test Code")
                .subSector("Test SubSector")
                .status("Active")
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .level("Test Level")
                .build();

        EntityResponse mappedResponse = getEntityResponse(entity);

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.findAll()).thenReturn(List.of(entity));

        EntitiesMapper mockMapper = Mockito.mock(EntitiesMapper.class);
        Mockito.when(mockMapper.toEntityResponseFromEntities(Mockito.anyList())).thenReturn(List.of(mappedResponse));

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, mockMapper);

        EntityPagedResponse response = service.getEntities(0, 10);

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertEquals(mappedResponse, response.getContent().get(0));
    }

    private static EntityResponse getEntityResponse(Entity entity) {
        EntityResponse mappedResponse = new EntityResponse();
        mappedResponse.setId(entity.getId());
        mappedResponse.setName(entity.getName());
        mappedResponse.setCode(entity.getCode());
        mappedResponse.setSubSector(entity.getSubSector());
        mappedResponse.setLevel(entity.getLevel());
        mappedResponse.setStatus(entity.getStatus());
        mappedResponse.setCreatedAt(String.valueOf(entity.getCreatedAt()));
        mappedResponse.setUpdatedAt(String.valueOf(entity.getUpdatedAt()));
        return mappedResponse;
    }

    @Test
    void givenValidIdAndRequestWhenUpdateEntityThenReturnsSuccess() {
        Integer id = 1;
        EntityRequest request = new EntityRequest("Updated Name", "Updated Code", "Updated SubSector", "Updated Level", "Active");
        Entity updatedEntity = Entity.builder()
                .id(id)
                .name(request.getName())
                .code(request.getCode())
                .subSector(request.getSubSector())
                .status(request.getStatus())
                .updatedAt(java.time.LocalDateTime.now())
                .level(request.getLevel())
                .build();

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.save(Mockito.any(Entity.class))).thenReturn(updatedEntity);

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, Mockito.mock(EntitiesMapper.class));

        EntityResultResponse response = service.updateEntity(id, request);

        Assertions.assertEquals("Entity updated successfully", response.getResult());
        Assertions.assertEquals("204", response.getCode());
    }

    @Test
    void givenNullIdWhenUpdateEntityThenThrowsException() {
        Integer id = null;
        EntityRequest request = new EntityRequest("Updated Name", "Updated Code", "Updated SubSector", "Updated Level", "Active");

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.save(Mockito.any(Entity.class))).thenReturn(Entity.builder().id(null).build());

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, Mockito.mock(EntitiesMapper.class));

        Assertions.assertThrows(ResponseStatusException.class, () -> service.updateEntity(id, request));
    }

    @Test
    void givenNameWhenSearchEntitiesByNameThenReturnsMappedEntities() {
        String name = "Test Name";
        String type = "0";
        Entity entity = Entity.builder()
                .id(1)
                .name(name)
                .code("Test Code")
                .subSector("Test SubSector")
                .level("Test Level")
                .status("Active")
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .build();

        EntityResponse mappedResponse = getEntityResponse(entity);

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.findByName(name)).thenReturn(List.of(entity));

        EntitiesMapper mockMapper = Mockito.mock(EntitiesMapper.class);
        Mockito.when(mockMapper.toEntityResponseFromEntities(Mockito.anyList())).thenReturn(List.of(mappedResponse));

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, mockMapper);

        EntityPagedResponse response = service.searchEntities(0, 10, name, null, type);

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertEquals(mappedResponse, response.getContent().get(0));
    }

    @Test
    void givenCodeWhenSearchEntitiesByCodeThenReturnsMappedEntities() {
        String code = "Test Code";
        String type = "1";
        Entity entity = Entity.builder()
                .id(1)
                .name("Test Name")
                .code(code)
                .subSector("Test SubSector")
                .level("Test Level")
                .status("Active")
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .build();

        EntityResponse mappedResponse = getEntityResponse(entity);

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.findByCode(code)).thenReturn(List.of(entity));

        EntitiesMapper mockMapper = Mockito.mock(EntitiesMapper.class);
        Mockito.when(mockMapper.toEntityResponseFromEntities(Mockito.anyList())).thenReturn(List.of(mappedResponse));

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, mockMapper);

        EntityPagedResponse response = service.searchEntities(0, 10, null, code, type);

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertEquals(mappedResponse, response.getContent().get(0));
    }

    @Test
    void givenNoEntitiesFoundWhenSearchEntitiesThenReturnsEmptyList() {
        String name = "Nonexistent Name";
        String type = "0";

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.findByName(name)).thenReturn(Collections.emptyList());

        EntitiesMapper mockMapper = Mockito.mock(EntitiesMapper.class);
        Mockito.when(mockMapper.toEntityResponseFromEntities(Mockito.anyList())).thenReturn(Collections.emptyList());

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, mockMapper);

        EntityPagedResponse response = service.searchEntities(0, 10, name, null, type);

        Assertions.assertTrue(response.getContent().isEmpty());
    }

    @Test
    void givenValidIdWhenDeleteEntityByIdThenSucceeds() {
        Integer id = 1;

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.existsById(id)).thenReturn(false);

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, Mockito.mock(EntitiesMapper.class));
        service.deleteEntityById(id);

        Mockito.verify(mockRepository).deleteById(id);
        Mockito.verify(mockRepository).existsById(id);
    }

    @Test
    void givenEntityStillExistsWhenDeleteEntityByIdThenThrowsException() {
        Integer id = 1;

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.existsById(id)).thenReturn(true);

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, Mockito.mock(EntitiesMapper.class));

        Assertions.assertThrows(ResponseStatusException.class, () -> service.deleteEntityById(id));
        Mockito.verify(mockRepository).deleteById(id);
        Mockito.verify(mockRepository).existsById(id);
    }
}

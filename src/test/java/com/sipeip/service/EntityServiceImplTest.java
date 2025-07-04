package com.sipeip.service;

import com.sipeip.domain.entity.Entities;
import com.sipeip.infrastructure.input.adapter.rest.models.*;
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
    void createEntitySuccessfully() {
        EntityCreateRequest request = new EntityCreateRequest("Test Name", "Test Code", "Test SubSector", "Test Level", "Active");
        Entities savedEntity = Entities.builder()
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
        Mockito.when(mockRepository.save(Mockito.any(Entities.class))).thenReturn(savedEntity);

        EntitiesMapper mockMapper = Mockito.mock(EntitiesMapper.class);
        EntityServiceImpl service = new EntityServiceImpl(mockRepository, mockMapper);

        EntityResultResponse response = service.createEntity(request);

        Assertions.assertEquals("Entity created successfully", response.getResult());
        Assertions.assertEquals("204", response.getCode());
    }

    @Test
    void createEntityFailsWhenIdIsNull() {
        EntityCreateRequest request = new EntityCreateRequest("Test Name", "Test Code", "Test SubSector", "Test Level", "Active");
        Entities savedEntity = Entities.builder()
                .name(request.getName())
                .code(request.getCode())
                .subSector(request.getSubSector())
                .status(request.getStatus())
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .level(request.getLevel())
                .build();

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.save(Mockito.any(Entities.class))).thenReturn(savedEntity);

        EntitiesMapper mockMapper = Mockito.mock(EntitiesMapper.class);
        EntityServiceImpl service = new EntityServiceImpl(mockRepository, mockMapper);

        Assertions.assertThrows(ResponseStatusException.class, () -> service.createEntity(request));
    }

    @Test
    void getEntitiesReturnsEmptyListWhenNoEntitiesExist() {
        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        EntitiesMapper mockMapper = Mockito.mock(EntitiesMapper.class);
        Mockito.when(mockMapper.toEntityResponseFromEntities(Mockito.anyList())).thenReturn(Collections.emptyList());

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, mockMapper);

        EntityPagedResponse response = service.getEntities(0, 10);

        Assertions.assertTrue(response.getContent().isEmpty());
    }

    @Test
    void getEntitiesReturnsMappedEntities() {
        Entities entity = Entities.builder()
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

    private static EntityResponse getEntityResponse(Entities entity) {
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
    void updateEntitySuccessfully() {
        Integer id = 1;
        EntityUpdateRequest request = new EntityUpdateRequest("Updated Name", "Updated Code", "Updated SubSector", "Updated Level", "Active");
        Entities updatedEntity = Entities.builder()
                .id(id)
                .name(request.getName())
                .code(request.getCode())
                .subSector(request.getSubSector())
                .status(request.getStatus())
                .updatedAt(java.time.LocalDateTime.now())
                .level(request.getLevel())
                .build();

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.save(Mockito.any(Entities.class))).thenReturn(updatedEntity);

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, Mockito.mock(EntitiesMapper.class));

        EntityResultResponse response = service.updateEntity(id, request);

        Assertions.assertEquals("Entity updated successfully", response.getResult());
        Assertions.assertEquals("204", response.getCode());
    }

    @Test
    void updateEntityFailsWhenIdIsNull() {
        Integer id = null;
        EntityUpdateRequest request = new EntityUpdateRequest("Updated Name", "Updated Code", "Updated SubSector", "Updated Level", "Active");

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.save(Mockito.any(Entities.class))).thenReturn(Entities.builder().id(null).build());

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, Mockito.mock(EntitiesMapper.class));

        Assertions.assertThrows(ResponseStatusException.class, () -> service.updateEntity(id, request));
    }

    @Test
    void searchEntitiesByNameReturnsMappedEntities() {
        String name = "Test Name";
        String type = "0";
        Entities entity = Entities.builder()
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
    void searchEntitiesByCodeReturnsMappedEntities() {
        String code = "Test Code";
        String type = "1";
        Entities entity = Entities.builder()
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
    void searchEntitiesReturnsEmptyListWhenNoEntitiesFound() {
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
    void deleteEntityByIdSuccessfully() {
        Integer id = 1;

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.existsById(id)).thenReturn(false);

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, Mockito.mock(EntitiesMapper.class));
        service.deleteEntityById(id);

        Mockito.verify(mockRepository).deleteById(id);
        Mockito.verify(mockRepository).existsById(id);
    }

    @Test
    void deleteEntityByIdFailsWhenEntityStillExists() {
        Integer id = 1;

        EntityRepository mockRepository = Mockito.mock(EntityRepository.class);
        Mockito.when(mockRepository.existsById(id)).thenReturn(true);

        EntityServiceImpl service = new EntityServiceImpl(mockRepository, Mockito.mock(EntitiesMapper.class));

        Assertions.assertThrows(ResponseStatusException.class, () -> service.deleteEntityById(id));
        Mockito.verify(mockRepository).deleteById(id);
        Mockito.verify(mockRepository).existsById(id);
    }
}
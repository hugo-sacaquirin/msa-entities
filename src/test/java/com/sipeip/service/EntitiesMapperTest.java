package com.sipeip.service;

import com.sipeip.domain.entity.Entity;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityResponse;
import com.sipeip.service.mapper.EntitiesMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class EntitiesMapperTest {

    @Test
    void mapEntityToEntityResponseSuccessfully() {
        Entity entity = Entity.builder()
                .id(1)
                .name("Test Name")
                .code("Test Code")
                .subSector("Test SubSector")
                .level("Test Level")
                .status("Active")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        EntitiesMapper mapper = new EntitiesMapper();
        EntityResponse response = mapper.toEntityResponse(entity);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(entity.getId(), response.getId());
        Assertions.assertEquals(entity.getName(), response.getName());
        Assertions.assertEquals(entity.getCode(), response.getCode());
        Assertions.assertEquals(entity.getSubSector(), response.getSubSector());
        Assertions.assertEquals(entity.getLevel(), response.getLevel());
        Assertions.assertEquals(entity.getStatus(), response.getStatus());
        Assertions.assertEquals(entity.getCreatedAt().toString(), response.getCreatedAt());
        Assertions.assertEquals(entity.getUpdatedAt().toString(), response.getUpdatedAt());
    }

    @Test
    void mapNullEntityReturnsNullResponse() {
        EntitiesMapper mapper = new EntitiesMapper();
        EntityResponse response = mapper.toEntityResponse(null);

        Assertions.assertNull(response);
    }

    @Test
    void mapEmptyEntityListReturnsEmptyResponseList() {
        EntitiesMapper mapper = new EntitiesMapper();
        List<EntityResponse> responseList = mapper.toEntityResponseFromEntities(Collections.emptyList());

        Assertions.assertNotNull(responseList);
        Assertions.assertTrue(responseList.isEmpty());
    }

    @Test
    void mapEntityListToEntityResponseListSuccessfully() {
        Entity entity = Entity.builder()
                .id(1)
                .name("Test Name")
                .code("Test Code")
                .subSector("Test SubSector")
                .level("Test Level")
                .status("Active")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        EntitiesMapper mapper = new EntitiesMapper();
        List<EntityResponse> responseList = mapper.toEntityResponseFromEntities(List.of(entity));

        Assertions.assertNotNull(responseList);
        Assertions.assertEquals(1, responseList.size());
        Assertions.assertEquals(entity.getId(), responseList.get(0).getId());
        Assertions.assertEquals(entity.getName(), responseList.get(0).getName());
        Assertions.assertEquals(entity.getCode(), responseList.get(0).getCode());
        Assertions.assertEquals(entity.getSubSector(), responseList.get(0).getSubSector());
        Assertions.assertEquals(entity.getLevel(), responseList.get(0).getLevel());
        Assertions.assertEquals(entity.getStatus(), responseList.get(0).getStatus());
        Assertions.assertEquals(entity.getCreatedAt().toString(), responseList.get(0).getCreatedAt());
        Assertions.assertEquals(entity.getUpdatedAt().toString(), responseList.get(0).getUpdatedAt());
    }
}
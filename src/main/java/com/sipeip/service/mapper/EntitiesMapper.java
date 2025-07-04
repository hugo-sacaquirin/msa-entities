package com.sipeip.service.mapper;


import com.sipeip.domain.entity.Entities;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntitiesMapper {

    public EntityResponse toEntityResponse(Entities entities) {
        if (entities == null) {
            return null;
        }
        EntityResponse entityResponse = new EntityResponse();
        entityResponse.setId(entities.getId());
        entityResponse.setName(entities.getName());
        entityResponse.setCode(entities.getCode());
        entityResponse.setSubSector(entities.getSubSector());
        entityResponse.setLevel(entities.getLevel());
        entityResponse.setStatus(entities.getStatus());
        entityResponse.setCreatedAt(String.valueOf(entities.getCreatedAt()));
        entityResponse.setUpdatedAt(String.valueOf(entities.getUpdatedAt()));
        return entityResponse;
    }

    public List<EntityResponse> toEntityResponseFromEntities(List<Entities> entitiesList) {
        return entitiesList.stream()
                .map(this::toEntityResponse)
                .toList();
    }
}
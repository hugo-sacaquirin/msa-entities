package com.sipeip.service;

import com.sipeip.infrastructure.input.adapter.rest.models.EntityPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityResultResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityUpdateRequest;

public interface EntityService {
    EntityResultResponse createEntity(EntityRequest request);

    EntityResultResponse updateEntity(Integer id, EntityUpdateRequest request);

    void deleteEntityById(Integer id);

    EntityPagedResponse getEntities(Integer page, Integer size);

    EntityPagedResponse searchEntities(Integer page, Integer size, String name, String code, String type);
}

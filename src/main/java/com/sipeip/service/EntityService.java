package com.sipeip.service;

import com.sipeip.infrastructure.input.adapter.rest.models.*;

public interface EntityService {
    EntityResultResponse createEntity(EntityCreateRequest request);

    EntityResultResponse updateEntity(Integer id, EntityUpdateRequest request);

    void deleteEntityById(Integer id);

    EntityPagedResponse getEntities(Integer page, Integer size);

    EntityPagedResponse searchEntities(Integer page, Integer size, String name, String code, String type);
}

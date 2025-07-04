package com.sipeip.service.impl;

import com.sipeip.domain.entity.Entities;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityCreateRequest;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityPagedResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityResultResponse;
import com.sipeip.infrastructure.input.adapter.rest.models.EntityUpdateRequest;
import com.sipeip.repository.EntityRepository;
import com.sipeip.service.EntityService;
import com.sipeip.service.mapper.EntitiesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.sipeip.util.StaticValues.CREATED;

@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {
    private final EntityRepository entityRepository;
    private final EntitiesMapper entitiesMapper;

    @Override
    public EntityResultResponse createEntity(EntityCreateRequest request) {
        Entities entities = entityRepository.save(Entities.builder()
                .name(request.getName())
                .code(request.getCode())
                .subSector(request.getSubSector())
                .status(request.getStatus())
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .level(request.getLevel())
                .build());
        if (entities.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating entity");
        }
        return getEntityResultResponse("Entity created successfully");
    }

    @Override
    public EntityResultResponse updateEntity(Integer id, EntityUpdateRequest request) {
        Entities entities = entityRepository.save(Entities.builder()
                .id(id)
                .name(request.getName())
                .code(request.getCode())
                .subSector(request.getSubSector())
                .status(request.getStatus())
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .level(request.getLevel())
                .build());
        if (entities.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating entity");
        }
        return getEntityResultResponse("Entity updated successfully");
    }

    private static EntityResultResponse getEntityResultResponse(String message) {
        EntityResultResponse entityResultResponse = new EntityResultResponse();
        entityResultResponse.setCode(CREATED);
        entityResultResponse.setResult(message);
        return entityResultResponse;
    }

    @Override
    public void deleteEntityById(Integer id) {
        entityRepository.deleteById(id);
        if (entityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error deleting entity");
        }
    }


    @Override
    public EntityPagedResponse getEntities(Integer page, Integer size) {
        EntityPagedResponse entityPagedResponse = new EntityPagedResponse();
        entityPagedResponse.setContent(entitiesMapper.toEntityResponseFromEntities(entityRepository.findAll()));
        return entityPagedResponse;
    }

    @Override
    public EntityPagedResponse searchEntities(Integer page, Integer size, String name, String code, String type) {
        EntityPagedResponse entityPagedResponse = new EntityPagedResponse();
        if (type.equals("0")) {
            entityPagedResponse.setContent(entitiesMapper.toEntityResponseFromEntities(entityRepository.findByName(name)));
        } else {
            entityPagedResponse.setContent(entitiesMapper.toEntityResponseFromEntities(entityRepository.findByCode(code)));

        }
        return entityPagedResponse;
    }
}

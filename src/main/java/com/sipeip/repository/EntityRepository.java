package com.sipeip.repository;

import com.sipeip.domain.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityRepository extends JpaRepository<Entity, Integer> {
    List<Entity> findByCode(String code);

    List<Entity> findByName(String name);
}
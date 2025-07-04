package com.sipeip.repository;

import com.sipeip.domain.entity.Entities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityRepository extends JpaRepository<Entities, Integer> {
    List<Entities> findByCode(String code);

    List<Entities> findByName(String name);
}
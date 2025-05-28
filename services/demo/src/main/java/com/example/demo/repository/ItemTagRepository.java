package com.example.demo.repository;

import org.dreams.backend.entity.ItemTagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTagRepository extends CrudRepository<ItemTagEntity, ItemTagEntity.PK> {
}

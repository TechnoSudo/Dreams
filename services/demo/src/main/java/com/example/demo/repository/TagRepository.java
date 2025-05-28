package com.example.demo.repository;

import org.dreams.backend.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, String> {
}

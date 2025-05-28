package org.dreams.backend.repository;

import org.dreams.backend.entity.ReviewEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity, UUID> {
    List<ReviewEntity> findAllByItemUuid(UUID userUuid);
}

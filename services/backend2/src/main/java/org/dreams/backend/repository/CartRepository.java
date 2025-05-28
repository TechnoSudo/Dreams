package org.dreams.backend.repository;

import org.dreams.backend.entity.CartItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends CrudRepository<CartItemEntity, UUID> {
    List<CartItemEntity> findAllByUserUuid(UUID userUuid);
}

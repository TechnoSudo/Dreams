package org.dreams.backend.repository;

import org.dreams.backend.entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<ItemEntity, UUID>, CrudRepository<ItemEntity, UUID> {

    List<ItemEntity> findAllByUuidIsIn(Collection<UUID> uuids);
}

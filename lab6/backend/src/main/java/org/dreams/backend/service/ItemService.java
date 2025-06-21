package org.dreams.backend.service;

import lombok.AllArgsConstructor;
import org.dreams.backend.dto.item.CreateItemDto;
import org.dreams.backend.dto.item.ItemDto;
import org.dreams.backend.entity.ItemEntity;
import org.dreams.backend.entity.ItemTagEntity;
import org.dreams.backend.repository.ItemRepository;
import org.dreams.backend.repository.ItemTagRepository;
import org.dreams.backend.repository.ReviewRepository;
import org.dreams.backend.repository.TagRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemRepository itemRepository;
    private ItemTagRepository itemTagRepository;
    private TagRepository tagRepository;
    private ReviewRepository reviewRepository;

    public ItemDto getItem(UUID itemUuid) {
        return itemRepository.findById(itemUuid).orElseThrow().toDto();
    }

    public List<ItemDto> getItems(int records, List<String> tags) {
        return itemRepository.findAll(Pageable.ofSize(records)).stream()
                .map(ItemEntity::toDto)
                .collect(Collectors.toList());
    }

    public ItemDto addItem(CreateItemDto createItemDto) {
        var itemEntity = createItemDto.toEntity();
//        var tags = tagRepository.findAllById(createItemDto.getTags());
        var result = itemRepository.save(itemEntity);
//        itemTagRepository.saveAll(StreamSupport
//                .stream(tags.spliterator(), false)
//                .map(tag -> new ItemTagEntity(result.uuid(), tag.tag()))
//                .toList()
//        );
        return result.toDto();
    }


}

package com.talkovia.mapper;

import com.talkovia.dto.category.CategoryRequestDTO;
import com.talkovia.dto.category.CategoryResponseDTO;
import com.talkovia.dto.item.ItemRequestDTO;
import com.talkovia.dto.item.ItemResponseDTO;
import com.talkovia.model.Category;
import com.talkovia.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemResponseDTO entityToResponseDTO(Item item);
    
    @Mapping(target = "id", ignore = true)
    Item requestDTOToEntity(ItemRequestDTO itemRequestDTO);
    
    List<ItemResponseDTO> entityListToResponseDTOList(List<Item> categories);
}

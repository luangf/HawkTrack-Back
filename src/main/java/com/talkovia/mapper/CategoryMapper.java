package com.talkovia.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.talkovia.dto.category.CategoryRequestDTO;
import com.talkovia.dto.category.CategoryResponseDTO;
import com.talkovia.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDTO entityToResponseDTO(Category category);
    
    @Mapping(target = "id", ignore = true)
    Category requestDTOToEntity(CategoryRequestDTO categoryRequestDTO);
    
    List<CategoryResponseDTO> entityListToResponseDTOList(List<Category> categories);
}

package com.talkovia.services;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkovia.customexceptions.ObjectNotFoundException;
import com.talkovia.dto.CategoryRequestDTO;
import com.talkovia.dto.CategoryResponseDTO;
import com.talkovia.mapper.CategoryMapper;
import com.talkovia.model.Category;
import com.talkovia.repositories.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	
	public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}
	
	public List<CategoryResponseDTO> getAllCategories() {
		return categoryMapper.entityListToResponseDTOList(categoryRepository.findAll());
	}

	public CategoryResponseDTO getCategoryById(Long id) {
		return categoryMapper.entityToResponseDTO(categoryRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Category not found! ID: " + id + ", Tipo: " + Category.class.getName())));
	}

	public void saveCategory(CategoryRequestDTO categoryRequestDTO) {
		categoryRepository.save(categoryMapper.requestDTOToEntity(categoryRequestDTO));
	}

	@Transactional
	public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
		Category existingCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Category not found! ID: " + id + ", Tipo: " + Category.class.getName()));
		existingCategory.setName(categoryRequestDTO.name());
		existingCategory.setDescription(categoryRequestDTO.description());
		return categoryMapper.entityToResponseDTO(existingCategory);
	}

	public void deleteCategoryById(Long id) {
		if(!categoryRepository.existsById(id)) {
			throw new ObjectNotFoundException("Category not found! ID: " + id + ", Tipo: " + Category.class.getName());
		}
		categoryRepository.deleteById(id);
	}
	
	public void deleteAllCategories() {
		categoryRepository.deleteAll();
	}
}

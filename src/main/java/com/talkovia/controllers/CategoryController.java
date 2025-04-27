package com.talkovia.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkovia.dto.CategoryRequestDTO;
import com.talkovia.dto.CategoryResponseDTO;
import com.talkovia.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories")
public class CategoryController {
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Operation(summary = "Get all categories")
	@GetMapping
	public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	@Operation(summary = "Get category by ID")
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	@Operation(summary = "Save category")
	@PostMapping
	public ResponseEntity<Void> saveCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
		categoryService.saveCategory(categoryRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "Update category by ID")
	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
		return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequestDTO));
	}

	@Operation(summary = "Delete category by ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
		categoryService.deleteCategoryById(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Delete all categories")
	@DeleteMapping
	public ResponseEntity<Void> deleteAllCategories() {
		categoryService.deleteAllCategories();
		return ResponseEntity.noContent().build();
	}
}

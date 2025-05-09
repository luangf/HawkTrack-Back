package com.talkovia.controllers;

import com.talkovia.dto.category.CategoryRequestDTO;
import com.talkovia.dto.category.CategoryResponseDTO;
import com.talkovia.dto.item.ItemRequestDTO;
import com.talkovia.dto.item.ItemResponseDTO;
import com.talkovia.services.CategoryService;
import com.talkovia.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@Tag(name = "Items")
public class ItemController {
	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@Operation(summary = "Get all items")
	@GetMapping
	public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
		return ResponseEntity.ok(itemService.getAllItems());
	}

	@Operation(summary = "Get item by ID")
	@GetMapping("/{id}")
	public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable Long id) {
		return ResponseEntity.ok(itemService.getItemById(id));
	}

	@Operation(summary = "Save item")
	@PostMapping
	public ResponseEntity<Void> saveItem(@Valid @RequestBody ItemRequestDTO itemRequestDTO) {
		itemService.saveItem(itemRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "Update item by ID")
	@PutMapping("/{id}")
	public ResponseEntity<ItemResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody ItemRequestDTO itemRequestDTO) {
		return ResponseEntity.ok(itemService.updateItem(id, itemRequestDTO));
	}

	@Operation(summary = "Delete item by ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItemsById(@PathVariable Long id) {
		itemService.deleteItemById(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Delete all items")
	@DeleteMapping
	public ResponseEntity<Void> deleteAllItems() {
		itemService.deleteAllItems();
		return ResponseEntity.noContent().build();
	}
}

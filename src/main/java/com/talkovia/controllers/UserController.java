package com.talkovia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.talkovia.dto.UserRequestDTO;
import com.talkovia.dto.UserResponseDTO;
import com.talkovia.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {
	@Autowired
	private UserService userService;

	@Operation(summary = "Get all users")
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@Operation(summary = "Get user by ID")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@Operation(summary = "Save user")
	@PostMapping
	public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRequestDTO));
	}

	@Operation(summary = "Update user by ID")
	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
		return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
	}

	@Operation(summary = "Delete user by ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Delete all users")
	@DeleteMapping
	public ResponseEntity<Void> deleteAllUsers() {
		userService.deleteAllUsers();
		return ResponseEntity.noContent().build();
	}
}

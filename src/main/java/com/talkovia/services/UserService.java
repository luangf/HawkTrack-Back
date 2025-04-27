package com.talkovia.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkovia.customexceptions.ObjectNotFoundException;
import com.talkovia.dto.UserRequestDTO;
import com.talkovia.dto.UserResponseDTO;
import com.talkovia.mapper.UserMapper;
import com.talkovia.model.User;
import com.talkovia.repositories.UserRepository;
	
@Service
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public List<UserResponseDTO> getAllUsers() {
		return userMapper.entityListToResponseDTOList(userRepository.findAll());
	}

	public UserResponseDTO getUserById(Long id) {
		return userMapper.entityToResponseDTO(userRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("User not found! ID: " + id + ", Tipo: " + User.class.getName())));
	}

	public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
		return userMapper.entityToResponseDTO(userRepository.save(userMapper.requestDTOToEntity(userRequestDTO)));
	}

	@Transactional
	public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("User not found! ID: " + id + ", Tipo: " + User.class.getName()));
		existingUser.setEmail(userRequestDTO.email());
		existingUser.setUsername(userRequestDTO.username());
		existingUser.setPassword(userRequestDTO.password());
		return userMapper.entityToResponseDTO(existingUser);
	}

	public void deleteUserById(Long id) {
		if(!userRepository.existsById(id)) {
			throw new ObjectNotFoundException("User not found! ID: " + id + ", Tipo: " + User.class.getName());
		}
		userRepository.deleteById(id);
	}

	public void deleteAllUsers() {
		userRepository.deleteAll();
	}
}

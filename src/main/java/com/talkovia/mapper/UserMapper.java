package com.talkovia.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.talkovia.dto.UserRequestDTO;
import com.talkovia.dto.UserResponseDTO;
import com.talkovia.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO entityToResponseDTO(User user);
    
    @Mapping(target = "id", ignore = true)
    User requestDTOToEntity(UserRequestDTO userRequestDTO);
    
    List<UserResponseDTO> entityListToResponseDTOList(List<User> users);
}

package com.example.DtoMapper;

import com.example.DTO.PostDTO;
import com.example.DTO.UserDTO;
import com.example.Entity.Users;
import com.example.config.ModelMapperConfig;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userDtoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Users covertDTOtoUserEntity(UserDTO userDto )  {
        Users user = modelMapper.map(userDto, Users.class);
        return user;

    }
    public UserDTO covertEntityToUserDTO(Users user)  {
        UserDTO userDto = modelMapper.map(user, UserDTO.class);
        return userDto;
    }

}

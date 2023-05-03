package com.moonshade.week10secureblogapi.service.implementation;

import com.moonshade.week10secureblogapi.dto.response.UserResponse;
import com.moonshade.week10secureblogapi.entity.UserEntity;
import com.moonshade.week10secureblogapi.exceptions.UserNotFoundException;
import com.moonshade.week10secureblogapi.repository.UserRepository;
import com.moonshade.week10secureblogapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public List<UserResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    @Override
    public String deleteUserByPostId(Long id) {
        log.info("Checking if user exists");
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return "User with ID " + id + " deleted";
        }
        return "User Not Found";
    }

    private UserResponse mapToResponse(UserEntity userEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userEntity, UserResponse.class);
    }


}

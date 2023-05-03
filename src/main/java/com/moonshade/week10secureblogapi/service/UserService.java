package com.moonshade.week10secureblogapi.service;

import com.moonshade.week10secureblogapi.dto.response.UserResponse;
import com.moonshade.week10secureblogapi.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    String deleteUserByPostId(Long id);
}

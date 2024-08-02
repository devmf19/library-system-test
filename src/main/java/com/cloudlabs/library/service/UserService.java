package com.cloudlabs.library.service;

import com.cloudlabs.library.dto.request.AddRolesRequestDto;
import com.cloudlabs.library.dto.request.UserRequestDto;
import com.cloudlabs.library.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto create(UserRequestDto userRequestDto);

    List<UserResponseDto> readAll();

    UserResponseDto readById(Long id);

    UserResponseDto modify(Long id, UserRequestDto userRequestDto);

    void remove(Long id);

    UserResponseDto addRoles(Long userId, AddRolesRequestDto rolesRequestDto);
}
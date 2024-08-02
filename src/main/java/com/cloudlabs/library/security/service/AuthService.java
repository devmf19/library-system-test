package com.cloudlabs.library.security.service;

import com.cloudlabs.library.security.dto.request.LoginRequestDto;
import com.cloudlabs.library.dto.response.UserResponseDto;
import com.cloudlabs.library.security.dto.request.NewUserRequestDto;

public interface AuthService {
    String authenticate(LoginRequestDto loginRequestDto);

    UserResponseDto create(NewUserRequestDto newUserRequestDto);
}

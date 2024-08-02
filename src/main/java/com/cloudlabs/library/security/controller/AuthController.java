package com.cloudlabs.library.security.controller;

import com.cloudlabs.library.security.dto.request.LoginRequestDto;
import com.cloudlabs.library.dto.response.ResponseDto;
import com.cloudlabs.library.dto.response.UserResponseDto;
import com.cloudlabs.library.security.dto.request.NewUserRequestDto;
import com.cloudlabs.library.security.service.AuthService;
import com.cloudlabs.library.security.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<String>> logIn(@Validated @RequestBody LoginRequestDto loginRequestDto) throws BadCredentialsException {
        return new ResponseEntity<>(
                ResponseDto.<String>builder()
                        .data(authService.authenticate(loginRequestDto))
                        .message(SecurityConstants.SUCCESS_LOGIN)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<UserResponseDto>> create(@Validated @RequestBody NewUserRequestDto newUserRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<UserResponseDto>builder()
                        .data(authService.create(newUserRequestDto))
                        .message(SecurityConstants.CREATED_USER)
                        .status(HttpStatus.CREATED)
                        .build(),
                HttpStatus.CREATED
        );
    }
}

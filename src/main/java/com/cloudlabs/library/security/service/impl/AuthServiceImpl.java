package com.cloudlabs.library.security.service.impl;

import com.cloudlabs.library.security.dto.request.LoginRequestDto;
import com.cloudlabs.library.dto.response.UserResponseDto;
import com.cloudlabs.library.enums.RoleEnum;
import com.cloudlabs.library.mapper.UserMapper;
import com.cloudlabs.library.model.Role;
import com.cloudlabs.library.model.User;
import com.cloudlabs.library.repository.UserRepository;
import com.cloudlabs.library.security.dto.request.NewUserRequestDto;
import com.cloudlabs.library.security.jwt.JwtGenerator;
import com.cloudlabs.library.security.service.AuthService;
import com.cloudlabs.library.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthServiceImpl(ApplicationContext applicationContext,
                           RoleService roleService,
                           UserMapper userMapper,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           JwtGenerator jwtGenerator) {
        this.userRepository = applicationContext.getBean(UserRepository.class);
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    public String authenticate(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        );

        Authentication authenticationResult = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
        return jwtGenerator.generateToken(authenticationResult);
    }

    public UserResponseDto create(NewUserRequestDto newUserRequestDto){
        Role role = roleService.readByName(RoleEnum.USER);

        User user = userMapper.toEntity(newUserRequestDto);
        user.setPassword(passwordEncoder.encode(newUserRequestDto.getPassword()));
        user.setRoles(Set.of(role));

        return userMapper.toResponse(userRepository.save(user));
    };
}

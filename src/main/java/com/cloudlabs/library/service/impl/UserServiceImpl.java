package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.dto.request.AddRolesRequestDto;
import com.cloudlabs.library.dto.request.UserRequestDto;
import com.cloudlabs.library.dto.response.UserResponseDto;
import com.cloudlabs.library.enums.RoleEnum;
import com.cloudlabs.library.mapper.UserMapper;
import com.cloudlabs.library.model.Role;
import com.cloudlabs.library.model.User;
import com.cloudlabs.library.repository.UserRepository;
import com.cloudlabs.library.security.service.CustomUserDetailsService;
import com.cloudlabs.library.service.RoleService;
import com.cloudlabs.library.service.UserService;
import com.cloudlabs.library.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService, CustomUserDetailsService {

    private final ApplicationContext applicationContext;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@Qualifier("userRepository") UserRepository userRepository,
                           ApplicationContext applicationContext,
                           UserMapper userMapper,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.applicationContext = applicationContext;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponseDto> readAll() {
        return userMapper.toResponseList(this.findAll());
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.readByName(RoleEnum.USER));

        userRequestDto.getRoles()
                .stream()
                .map(String::toUpperCase)
                .forEach(role -> {
                    if (RoleEnum.ADMIN.name().equals(role)) {
                        roles.add(roleService.readByName(RoleEnum.ADMIN));
                    }
                });

        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRoles(roles);

        return userMapper.toResponse(this.save(user));
    }

    @Override
    public UserResponseDto readById(Long id) {
        return this.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_USER.concat(id.toString()))
                );
    }

    @Override
    public UserResponseDto modify(Long id, UserRequestDto userRequestDto) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.readByName(RoleEnum.USER));

        userRequestDto.getRoles()
                .stream()
                .map(String::toUpperCase)
                .forEach(role -> {
                    if (RoleEnum.ADMIN.name().equals(role)) {
                        roles.add(roleService.readByName(RoleEnum.ADMIN));
                    }
                });

        return this.findById(id)
                .map(user -> {
                    user.setName(userRequestDto.getName());
                    user.setUsername(userRequestDto.getUsername());

                    if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
                        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
                    }

                    user.setRoles(roles);

                    return this.save(user);
                })
                .map(userMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_USER.concat(id.toString()))
                );
    }

    @Override
    public void remove(Long id) {
        User user = this.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_USER.concat(id.toString()))
                );
        Role role = roleService.readByName(RoleEnum.DISABLED);
        user.setRoles(Set.of(role));
        this.save(user);
    }

    @Override
    public UserResponseDto addRoles(Long userId, AddRolesRequestDto rolesRequestDto) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.readByName(RoleEnum.USER));

        rolesRequestDto.getRoles()
                .stream()
                .map(String::toUpperCase)
                .forEach(role -> {
                    if (RoleEnum.ADMIN.name().equals(role)) {
                        roles.add(roleService.readByName(RoleEnum.ADMIN));
                    } else if(RoleEnum.DISABLED.name().equals(role)){
                        roles.add(roleService.readByName(RoleEnum.DISABLED));
                    }
                });

        return this.findById(userId)
                .map(user -> {
                    user.setRoles(roles);
                    return this.save(user);
                })
                .map(userMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_USER.concat(userId.toString()))
                );
    }

    @Override
    public Optional<User> readByUsername(String username) {
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        return userRepository.findByUsername(username);
    }
}

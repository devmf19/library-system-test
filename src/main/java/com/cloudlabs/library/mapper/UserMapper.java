package com.cloudlabs.library.mapper;

import com.cloudlabs.library.dto.request.UserRequestDto;
import com.cloudlabs.library.dto.response.UserResponseDto;
import com.cloudlabs.library.model.Role;
import com.cloudlabs.library.model.User;
import com.cloudlabs.library.security.dto.request.NewUserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(NewUserRequestDto newUserRequestDto);

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponseDto toResponse(User user);

    List<UserResponseDto> toResponseList(List<User> users);

    default List<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());
    }
}

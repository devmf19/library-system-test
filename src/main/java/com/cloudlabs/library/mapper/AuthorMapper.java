package com.cloudlabs.library.mapper;

import com.cloudlabs.library.dto.request.AuthorRequestDto;
import com.cloudlabs.library.dto.response.AuthorResponseDto;
import com.cloudlabs.library.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    Author toEntity (AuthorRequestDto authorRequestDto);

    AuthorResponseDto toResponse (Author author);

    List<AuthorResponseDto> toResponseList(List<Author> authors);
}

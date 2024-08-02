package com.cloudlabs.library.service;

import com.cloudlabs.library.dto.request.AuthorRequestDto;
import com.cloudlabs.library.dto.response.AuthorResponseDto;

import java.util.List;

public interface AuthorService {
    List<AuthorResponseDto> readAll();

    AuthorResponseDto create(AuthorRequestDto authorRequestDto);

    AuthorResponseDto readById(Long id);

    AuthorResponseDto modify(Long id, AuthorRequestDto authorRequestDto);

    void remove(Long id);
}

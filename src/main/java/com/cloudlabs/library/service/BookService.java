package com.cloudlabs.library.service;

import com.cloudlabs.library.dto.request.BookRequestDto;
import com.cloudlabs.library.dto.response.BookResponseDto;

import java.util.List;

public interface BookService {
    List<BookResponseDto> readAll();

    BookResponseDto create(BookRequestDto bookRequestDto);

    BookResponseDto readById(Long id);

    BookResponseDto modify(Long id, BookRequestDto bookRequestDto);

    void remove(Long id);
}

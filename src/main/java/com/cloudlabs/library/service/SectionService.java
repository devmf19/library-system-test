package com.cloudlabs.library.service;

import com.cloudlabs.library.dto.request.SectionRequestDto;
import com.cloudlabs.library.dto.response.SectionResponseDto;

import java.util.List;

public interface SectionService {
    List<SectionResponseDto> readAll();

    SectionResponseDto create(SectionRequestDto sectionRequestDto);

    SectionResponseDto readById(Long id);

    SectionResponseDto modify(Long id, SectionRequestDto sectionRequestDto);

    void remove(Long id);
}

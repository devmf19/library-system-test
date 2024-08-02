package com.cloudlabs.library.service;

import com.cloudlabs.library.dto.request.MemberRequestDto;
import com.cloudlabs.library.dto.response.MemberResponseDto;

import java.util.List;

public interface MemberService {
    List<MemberResponseDto> readAll();

    MemberResponseDto create(MemberRequestDto memberRequestDto);

    MemberResponseDto readById(Long id);

    MemberResponseDto modify(Long id, MemberRequestDto memberRequestDto);

    void remove(Long id);
}

package com.cloudlabs.library.mapper;

import com.cloudlabs.library.dto.request.MemberRequestDto;
import com.cloudlabs.library.dto.response.MemberResponseDto;
import com.cloudlabs.library.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "id", ignore = true)
    Member toEntity(MemberRequestDto memberRequestDto);

    MemberResponseDto toResponse(Member member);

    List<MemberResponseDto> toResponseList (List<Member> members);
}

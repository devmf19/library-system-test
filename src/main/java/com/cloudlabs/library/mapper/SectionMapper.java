package com.cloudlabs.library.mapper;

import com.cloudlabs.library.dto.request.SectionRequestDto;
import com.cloudlabs.library.dto.response.SectionResponseDto;
import com.cloudlabs.library.model.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    @Mapping(target = "id", ignore = true)
    Section toEntity(SectionRequestDto sectionRequestDto);

    SectionResponseDto toResponse(Section section);

    List<SectionResponseDto> toResponseList(List<Section> sections);

}

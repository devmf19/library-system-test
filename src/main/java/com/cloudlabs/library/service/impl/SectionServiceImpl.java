package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.dto.request.SectionRequestDto;
import com.cloudlabs.library.dto.response.SectionResponseDto;
import com.cloudlabs.library.mapper.SectionMapper;
import com.cloudlabs.library.model.Section;
import com.cloudlabs.library.repository.SectionRepository;
import com.cloudlabs.library.service.SectionService;
import com.cloudlabs.library.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl extends GenericServiceImpl<Section, Long> implements SectionService {

    private final SectionMapper sectionMapper;

    @Autowired
    public SectionServiceImpl(@Qualifier("sectionRepository") SectionRepository sectionRepository, SectionMapper sectionMapper) {
        super(sectionRepository);
        this.sectionMapper = sectionMapper;
    }

    @Override
    public List<SectionResponseDto> readAll() {
        return sectionMapper.toResponseList(this.findAll());
    }

    @Override
    public SectionResponseDto create(SectionRequestDto sectionRequestDto) {
        return sectionMapper.toResponse(
                this.save(
                        sectionMapper.toEntity(sectionRequestDto)
                )
        );
    }

    @Override
    public SectionResponseDto readById(Long id) {
        return this.findById(id)
                .map(sectionMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_SECTION.concat(id.toString()))
                );
    }

    @Override
    public SectionResponseDto modify(Long id, SectionRequestDto sectionRequestDto) {
        return this.findById(id)
                .map(section -> {
                    Section updatedSection = sectionMapper.toEntity(sectionRequestDto);
                    updatedSection.setId(section.getId());
                    return updatedSection;
                })
                .map(this::save)
                .map(sectionMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_SECTION.concat(id.toString()))
                );
    }

    @Override
    public void remove(Long id) {
        this.delete(
                findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(Constants.NOT_FOUND_SECTION.concat(id.toString()))
                        )
                        .getId()
        );
    }
}

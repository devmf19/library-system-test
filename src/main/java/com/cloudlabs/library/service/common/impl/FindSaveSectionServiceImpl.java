package com.cloudlabs.library.service.common.impl;

import com.cloudlabs.library.model.Section;
import com.cloudlabs.library.repository.SectionRepository;
import com.cloudlabs.library.service.common.FindSaveSectionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class FindSaveSectionServiceImpl implements FindSaveSectionService {
    private final SectionRepository sectionRepository;

    @Autowired
    public FindSaveSectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public Optional<Section> get(Long id) {
        return sectionRepository.findById(id);
    }

    @Override
    public Section post(Section section) {
        return sectionRepository.save(section);
    }
}

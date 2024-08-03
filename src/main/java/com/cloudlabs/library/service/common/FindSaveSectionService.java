package com.cloudlabs.library.service.common;

import com.cloudlabs.library.model.Section;

import java.util.Optional;

public interface FindSaveSectionService {
    Optional<Section> get(Long id);
    Section post(Section member);
}

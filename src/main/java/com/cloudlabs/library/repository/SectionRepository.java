package com.cloudlabs.library.repository;

import com.cloudlabs.library.model.Section;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("sectionRepository")
public interface SectionRepository extends JpaRepository<Section, Long> {
}

package com.cloudlabs.library.repository;

import com.cloudlabs.library.model.Author;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("authorRepository")
public interface AuthorRepository extends JpaRepository<Author, Long> {
}

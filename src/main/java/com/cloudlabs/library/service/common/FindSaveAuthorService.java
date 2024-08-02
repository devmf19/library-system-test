package com.cloudlabs.library.service.common;

import com.cloudlabs.library.model.Author;

import java.util.Optional;

public interface FindSaveAuthorService {
    Optional<Author> get(Long id);
    Author post(Author author);
}

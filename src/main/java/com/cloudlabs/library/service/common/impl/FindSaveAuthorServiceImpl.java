package com.cloudlabs.library.service.common.impl;

import com.cloudlabs.library.model.Author;
import com.cloudlabs.library.repository.AuthorRepository;
import com.cloudlabs.library.service.common.FindSaveAuthorService;

import java.util.Optional;

public class FindSaveAuthorServiceImpl implements FindSaveAuthorService {
    private final AuthorRepository authorRepository;

    public FindSaveAuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<Author> get(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author post(Author author) {
        return authorRepository.save(author);
    }
}

package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.dto.request.AuthorRequestDto;
import com.cloudlabs.library.dto.response.AuthorResponseDto;
import com.cloudlabs.library.mapper.AuthorMapper;
import com.cloudlabs.library.model.Author;
import com.cloudlabs.library.repository.AuthorRepository;
import com.cloudlabs.library.service.AuthorService;
import com.cloudlabs.library.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl extends GenericServiceImpl<Author, Long> implements AuthorService {

    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(@Qualifier("authorRepository") AuthorRepository authorRepository, AuthorMapper authorMapper) {
        super(authorRepository);
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorResponseDto> readAll() {
        return authorMapper.toResponseList(this.findAll());
    }


    @Override
    public AuthorResponseDto create(AuthorRequestDto authorRequestDto) {
        return authorMapper.toResponse(
                this.save(
                        authorMapper.toEntity(authorRequestDto)
                )
        );
    }

    @Override
    public AuthorResponseDto readById(Long id) {
        return this.findById(id)
                .map(authorMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_AUTHOR.concat(id.toString()))
                );
    }

    @Override
    public AuthorResponseDto modify(Long id, AuthorRequestDto authorRequestDto) {
        return this.findById(id)
                .map(author -> {
                    Author updatedAuthor = authorMapper.toEntity(authorRequestDto);
                    updatedAuthor.setId(author.getId());
                    return updatedAuthor;
                })
                .map(this::save)
                .map(authorMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_AUTHOR)
                );
    }

    @Override
    public void remove(Long id) {
        this.delete(
                findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(Constants.NOT_FOUND_AUTHOR.concat(id.toString()))
                        )
                        .getId()
        );
    }
}

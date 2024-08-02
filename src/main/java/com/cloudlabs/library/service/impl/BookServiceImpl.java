package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.dto.request.BookRequestDto;
import com.cloudlabs.library.dto.response.BookResponseDto;
import com.cloudlabs.library.mapper.BookMapper;
import com.cloudlabs.library.model.Author;
import com.cloudlabs.library.model.Book;
import com.cloudlabs.library.model.Section;
import com.cloudlabs.library.repository.BookRepository;
import com.cloudlabs.library.service.BookService;
import com.cloudlabs.library.service.common.FindSaveAuthorService;
import com.cloudlabs.library.service.common.FindSaveSectionService;
import com.cloudlabs.library.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl extends GenericServiceImpl<Book, Long> implements BookService {

    private final BookMapper bookMapper;
    private final FindSaveAuthorService findSaveAuthorService;
    private final FindSaveSectionService findSaveSectionService;

    @Autowired
    public BookServiceImpl(@Qualifier("bookRepository") BookRepository bookRepository,
                           BookMapper bookMapper,
                           FindSaveAuthorService findSaveAuthorService,
                           FindSaveSectionService findSaveSectionService) {
        super(bookRepository);
        this.bookMapper = bookMapper;
        this.findSaveAuthorService = findSaveAuthorService;
        this.findSaveSectionService = findSaveSectionService;
    }

    @Override
    public List<BookResponseDto> readAll() {
        return bookMapper.toResponseList(this.findAll());
    }

    @Override
    public BookResponseDto create(BookRequestDto bookRequestDto) {
        Book book = bookMapper.toEntity(bookRequestDto);
        Set<Author> authors = new HashSet<>();

        bookRequestDto.getAuthorsId().forEach(id -> {
            Author author = findSaveAuthorService.get(id).orElseThrow(
                    () -> new EntityNotFoundException(Constants.NOT_FOUND_AUTHOR.concat(id.toString()))
            );
            authors.add(author);
        });

        Section section = findSaveSectionService.get(bookRequestDto.getSectionId()).orElseThrow(
                () -> new EntityNotFoundException(Constants.NOT_FOUND_SECTION.concat(bookRequestDto.getSectionId().toString()))
        );

        book.setAuthors(authors);
        book.setSection(section);

        return bookMapper.toResponse(this.save(book));
    }

    @Override
    public BookResponseDto readById(Long id) {
        return this.findById(id)
                .map(bookMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_BOOK.concat(id.toString()))
                );
    }

    @Override
    public BookResponseDto modify(Long id, BookRequestDto bookRequestDto) {
        return this.findById(id)
                .map(book -> {
                    Book updatedBook = bookMapper.toEntity(bookRequestDto);
                    updatedBook.setId(book.getId());
                    return updatedBook;
                })
                .map(this::save)
                .map(bookMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_BOOK.concat(id.toString()))
                );
    }

    @Override
    public void remove(Long id) {
        this.delete(
                findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(Constants.NOT_FOUND_BOOK.concat(id.toString()))
                        )
                        .getId()
        );
    }
}

package com.cloudlabs.library.service.common.impl;

import com.cloudlabs.library.model.Book;
import com.cloudlabs.library.repository.BookRepository;
import com.cloudlabs.library.service.common.FindSaveBookService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindSaveBookServiceImpl implements FindSaveBookService {

    private final BookRepository bookRepository;

    public FindSaveBookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Book> get(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book post(Book book) {
        return bookRepository.save(book);
    }
}

package com.cloudlabs.library.service;

import com.cloudlabs.library.model.Book;

import java.util.Optional;

public interface FindSaveBookService {
    Optional<Book> get(Long id);
    Book post(Book book);
}

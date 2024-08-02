package com.cloudlabs.library.repository;

import com.cloudlabs.library.model.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long> {
}

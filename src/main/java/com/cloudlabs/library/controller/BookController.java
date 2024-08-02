package com.cloudlabs.library.controller;

import com.cloudlabs.library.dto.request.BookRequestDto;
import com.cloudlabs.library.dto.response.BookResponseDto;
import com.cloudlabs.library.dto.response.ResponseDto;
import com.cloudlabs.library.service.BookService;
import com.cloudlabs.library.util.Constants;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<BookResponseDto>>> readAll() {
        return new ResponseEntity<>(
                ResponseDto.<List<BookResponseDto>>builder()
                        .data(bookService.readAll())
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDto<BookResponseDto>> create(@Validated @RequestBody BookRequestDto bookRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<BookResponseDto>builder()
                        .data(bookService.create(bookRequestDto))
                        .message(Constants.CREATED_BOOk)
                        .status(HttpStatus.CREATED)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/r/{id}")
    public ResponseEntity<ResponseDto<BookResponseDto>> readById(@Min(value = 1) @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                ResponseDto.<BookResponseDto>builder()
                        .data(bookService.readById(id))
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );

    }

    @PutMapping("/u/{id}")
    @PreAuthorize(Constants.ADMIN_ROLE)
    public ResponseEntity<ResponseDto<BookResponseDto>> modify(@Min(value = 1) @PathVariable("id") Long id,
                                                               @Validated @RequestBody BookRequestDto bookRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<BookResponseDto>builder()
                        .data(bookService.modify(id, bookRequestDto))
                        .message(Constants.UPDATED_BOOK)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/d/{id}")
    @PreAuthorize(Constants.ADMIN_ROLE)
    public ResponseEntity<ResponseDto<String>> remove(@Min(value = 1) @PathVariable Long id) {
        return new ResponseEntity<>(
                ResponseDto.<String>builder()
                        .data(Constants.DELETED_BOOK.concat(id.toString()))
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }
}

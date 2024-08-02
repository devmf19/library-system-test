package com.cloudlabs.library.controller;

import com.cloudlabs.library.dto.request.AuthorRequestDto;
import com.cloudlabs.library.dto.response.AuthorResponseDto;
import com.cloudlabs.library.dto.response.ResponseDto;
import com.cloudlabs.library.service.AuthorService;
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
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<AuthorResponseDto>>> readAll(){
        return new ResponseEntity<>(
                ResponseDto.<List<AuthorResponseDto>>builder()
                        .data(authorService.readAll())
                        .status(HttpStatus.OK)
                        .message(Constants.SUCCESS)
                        .build(),
                HttpStatus.OK
        );
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDto<AuthorResponseDto>> create(@Validated @RequestBody AuthorRequestDto authorRequestDto){
        return new ResponseEntity<>(
                ResponseDto.<AuthorResponseDto>builder()
                        .data(authorService.create(authorRequestDto))
                        .status(HttpStatus.CREATED)
                        .message(Constants.CREATED_AUTHOR)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/r/{id}")
    public ResponseEntity<ResponseDto<AuthorResponseDto>> readById(@Min(value = 1) @PathVariable("id") Long id){
        return new ResponseEntity<>(
                ResponseDto.<AuthorResponseDto>builder()
                        .data(authorService.readById(id))
                        .status(HttpStatus.OK)
                        .message(Constants.SUCCESS)
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/u/{id}")
    @PreAuthorize(Constants.ADMIN_ROLE)
    public ResponseEntity<ResponseDto<AuthorResponseDto>> modify(@Min(value = 1) @PathVariable("id") Long id,
                                                                 @Validated @RequestBody AuthorRequestDto authorRequestDto){
        return new ResponseEntity<>(
                ResponseDto.<AuthorResponseDto>builder()
                        .data(authorService.modify(id, authorRequestDto))
                        .status(HttpStatus.OK)
                        .message(Constants.UPDATED_AUTHOR)
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/d/{id}")
    @PreAuthorize(Constants.ADMIN_ROLE)
    public ResponseEntity<ResponseDto<String>> remove(@Min(value = 1) @PathVariable("id") Long id){
        authorService.remove(id);
        return new ResponseEntity<>(
                ResponseDto.<String>builder()
                        .data(Constants.DELETED_AUTHOR.concat(id.toString()))
                        .status(HttpStatus.OK)
                        .message(Constants.SUCCESS)
                        .build(),
                HttpStatus.OK
        );
    }
}

package com.cloudlabs.library.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String isbn;
    private String name;
    private String genre;
    private int total;
    private int stock;
    private Set<AuthorResponseDto> authors;
    private SectionResponseDto section;
}

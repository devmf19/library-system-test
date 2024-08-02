package com.cloudlabs.library.mapper;

import com.cloudlabs.library.dto.request.BookRequestDto;
import com.cloudlabs.library.dto.response.BookResponseDto;
import com.cloudlabs.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class, SectionMapper.class})
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "total", source = "stock")
    Book toEntity(BookRequestDto bookRequestDto);

    BookResponseDto toResponse(Book book);

    List<BookResponseDto> toResponseList(List<Book> books);
}

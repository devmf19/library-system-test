package com.cloudlabs.library.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceResponseDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime finalDate;
    private String comments;
    private String status;
    private List<InvoiceItemResponseDto> books;
    private MemberResponseDto member;
    private UserResponseDto user;
}

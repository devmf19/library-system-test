package com.cloudlabs.library.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectionResponseDto {
    private Long id;
    private String name;
    private String description;
}

package com.cloudlabs.library.dto.request;

import com.cloudlabs.library.util.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookRequestDto {

    @NotBlank(message = Constants.REQUIRED_ISBN)
    private String isbn;

    @NotBlank(message = Constants.REQUIRED_NAME)
    private String name;

    private String genre;

    @NotNull(message = Constants.REQUIRED_STOCK)
    @Min(value = 1, message = Constants.INVALID_STOCK)
    private int stock;

    @NotNull(message = Constants.REQUIRED_AUTHORS_ID)
    @Valid
    private Set<@NotNull(message = Constants.INVALID_ID)
                @Min(value = 1)
            Long> authorsId;

    @NotNull(message = Constants.REQUIRED_SECTION_ID)
    @Min(value = 1, message = Constants.INVALID_SECTION_ID)
    private Long sectionId;
}

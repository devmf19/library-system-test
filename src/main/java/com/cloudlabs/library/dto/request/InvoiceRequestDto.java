package com.cloudlabs.library.dto.request;

import com.cloudlabs.library.util.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceRequestDto {

    @NotNull(message = Constants.REQUIRED_END_DATE)
    private LocalDate finalDate;

    private String comments;

    @NotNull(message = Constants.REQUIRED_MEMBER_ID)
    @Min(value = 1, message = Constants.INVALID_ID)
    private Long memberId;

    @NotNull(message = Constants.REQUIRED_BOOKS_ID)
    @Valid
    private Set<@NotNull(message = Constants.INVALID_ID)
                @Min(value = 1)
            Long> booksId;
}

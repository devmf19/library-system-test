package com.cloudlabs.library.dto.request;

import com.cloudlabs.library.util.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorRequestDto {

    @NotBlank(message = Constants.REQUIRED_NAME)
    private String name;

    private String country;
}

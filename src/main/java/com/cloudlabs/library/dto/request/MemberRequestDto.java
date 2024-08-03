package com.cloudlabs.library.dto.request;

import com.cloudlabs.library.util.Constants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = Constants.REQUIRED_NAME)
    private String name;

    @NotNull(message = Constants.REQUIRED_PHONE)
    @Min(value = 7, message = Constants.PHONE_LENGTH_ERROR)
    @Max(value = 10, message = Constants.PHONE_LENGTH_ERROR)
    private Long phone;

    @Email(message = Constants.INVALID_EMAIL)
    private String email;

    @NotBlank(message = Constants.REQUIRED_ADDRESS)
    private String address;
}

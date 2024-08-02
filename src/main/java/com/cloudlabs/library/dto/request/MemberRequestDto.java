package com.cloudlabs.library.dto.request;

import com.cloudlabs.library.util.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {

    @NotBlank(message = Constants.REQUIRED_NAME)
    private String name;

    @NotBlank(message = Constants.REQUIRED_PHONE)
    private String phone;

    @Email(message = Constants.INVALID_EMAIL)
    private String email;

    @NotBlank(message = Constants.REQUIRED_ADDRESS)
    private String address;
}

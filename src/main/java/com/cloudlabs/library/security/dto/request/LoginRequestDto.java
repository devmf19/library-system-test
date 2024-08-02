package com.cloudlabs.library.security.dto.request;

import com.cloudlabs.library.security.util.SecurityConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = SecurityConstants.REQUIRED_USERNAME)
    private String username;

    @NotBlank(message = SecurityConstants.REQUIRED_PASSWORD)
    private String password;
}

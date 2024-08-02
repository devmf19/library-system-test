package com.cloudlabs.library.security.dto.request;

import com.cloudlabs.library.util.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewUserRequestDto {
    @NotBlank(message = Constants.REQUIRED_NAME)
    private String name;

    @NotBlank(message = Constants.REQUIRED_USERNAME)
    private String username;

    @NotBlank(message = Constants.REQUIRED_PASSWORD)
    private String password;
}

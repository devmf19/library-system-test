package com.cloudlabs.library.dto.request;

import com.cloudlabs.library.util.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = Constants.REQUIRED_NAME)
    private String name;

    @NotBlank(message = Constants.REQUIRED_USERNAME)
    private String username;

    @NotBlank(message = Constants.REQUIRED_PASSWORD)
    private String password;

    private Set<String> roles;
}

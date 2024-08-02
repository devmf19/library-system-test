package com.cloudlabs.library.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Getter
@Service
@NoArgsConstructor
public class AddRolesRequestDto {
    @NotEmpty(message = "Dene introducir al menos un rol USER, ADMIN o DISABLED")
    Set<String> roles;
}

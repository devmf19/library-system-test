package com.cloudlabs.library.controller;

import com.cloudlabs.library.dto.request.AddRolesRequestDto;
import com.cloudlabs.library.dto.request.UserRequestDto;
import com.cloudlabs.library.dto.response.UserResponseDto;
import com.cloudlabs.library.dto.response.ResponseDto;
import com.cloudlabs.library.service.UserService;
import com.cloudlabs.library.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize(Constants.ADMIN_ROLE)
@Tag(name = "Usuarios", description = "Usuarios que gestionan y administran el sistema y la información (SOLO ADMIN)")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @Operation(
            summary = "Muestra todos los usuarios registrados en el sistema (SOLO ADMIN)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> readAll() {
        return new ResponseEntity<>(
                ResponseDto.<List<UserResponseDto>>builder()
                        .data(userService.readAll())
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    @Operation(
            summary = "Registrar un nuevo usuario con roles (SOLO ADMIN)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<UserResponseDto>> create(@Validated @RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<UserResponseDto>builder()
                        .data(userService.create(userRequestDto))
                        .message(Constants.CREATED_USER)
                        .status(HttpStatus.CREATED)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/r/{id}")
    @Operation(
            summary = "Buscar usuario por id (SOLO ADMIN)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<UserResponseDto>> readById(@Min(value = 1) @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                ResponseDto.<UserResponseDto>builder()
                        .data(userService.readById(id))
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );

    }

    @PutMapping("/u/{id}")
    @Operation(
            summary = "Actualiza la información de un usuario registrado mediante su id (SOLO ADMIN)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<UserResponseDto>> modify(@Min(value = 1) @PathVariable("id") Long id,
                                                               @Validated @RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<UserResponseDto>builder()
                        .data(userService.modify(id, userRequestDto))
                        .message(Constants.UPDATED_USER)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/u/role/{id}")
    @Operation(
            summary = "Inhabilita un usuario registrado mediante su id (SOLO ADMIN)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<UserResponseDto>> modifyRoles(@Min(value = 1) @PathVariable("id") Long id,
                                                                    @Validated @RequestBody AddRolesRequestDto addRolesRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<UserResponseDto>builder()
                        .data(userService.addRoles(id, addRolesRequestDto))
                        .message(Constants.UPDATED_USER)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/d/{id}")
    public ResponseEntity<ResponseDto<String>> remove(@Min(value = 1) @PathVariable Long id) {
        userService.remove(id);
        return new ResponseEntity<>(
                ResponseDto.<String>builder()
                        .data(Constants.DISABLED_USER.concat(id.toString()))
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }
}

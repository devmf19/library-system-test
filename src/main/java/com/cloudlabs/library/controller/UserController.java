package com.cloudlabs.library.controller;

import com.cloudlabs.library.dto.request.AddRolesRequestDto;
import com.cloudlabs.library.dto.request.UserRequestDto;
import com.cloudlabs.library.dto.response.UserResponseDto;
import com.cloudlabs.library.dto.response.ResponseDto;
import com.cloudlabs.library.service.UserService;
import com.cloudlabs.library.util.Constants;
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
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
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
    public ResponseEntity<ResponseDto<UserResponseDto>> modify(@Min(value = 1) @PathVariable("id") Long id,
                                                               @Validated @RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<UserResponseDto>builder()
                        .data(userService.modify(id, userRequestDto))
                        .message(Constants.UPDATED_BOOK)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/u/role/{id}")
    public ResponseEntity<ResponseDto<UserResponseDto>> modifyRoles(@Min(value = 1) @PathVariable("id") Long id,
                                                                    @Validated @RequestBody AddRolesRequestDto addRolesRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<UserResponseDto>builder()
                        .data(userService.addRoles(id, addRolesRequestDto))
                        .message(Constants.UPDATED_BOOK)
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

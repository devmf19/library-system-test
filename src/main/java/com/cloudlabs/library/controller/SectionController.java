package com.cloudlabs.library.controller;

import com.cloudlabs.library.dto.request.SectionRequestDto;
import com.cloudlabs.library.dto.response.ResponseDto;
import com.cloudlabs.library.dto.response.SectionResponseDto;
import com.cloudlabs.library.service.SectionService;
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
@RequestMapping("/section")
@Tag(name = "Secciones", description = "Secciones donde se almacenan los libros dentro de la biblioteca")
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }


    @GetMapping("/all")
    @Operation(
            summary = "Muestra todos las secciones registradas en el sistema",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<List<SectionResponseDto>>> readAll(){
        return new ResponseEntity<>(
                ResponseDto.<List<SectionResponseDto>>builder()
                        .data(sectionService.readAll())
                        .status(HttpStatus.OK)
                        .message(Constants.SUCCESS)
                        .build(),
                HttpStatus.OK
        );
    }


    @PostMapping("/create")@Operation(
            summary = "Registrar una nueva sección",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<SectionResponseDto>> create(@Validated @RequestBody SectionRequestDto sectionRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<SectionResponseDto>builder()
                        .data(sectionService.create(sectionRequestDto))
                        .status(HttpStatus.CREATED)
                        .message(Constants.CREATED_SECTION)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/r/{id}")@Operation(
            summary = "Buscar sección por id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<SectionResponseDto>> readById(@Min(value = 1) @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                ResponseDto.<SectionResponseDto>builder()
                        .data(sectionService.readById(id))
                        .status(HttpStatus.OK)
                        .message(Constants.SUCCESS)
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/u/{id}")
    @PreAuthorize(Constants.ADMIN_ROLE)
    @Operation(
            summary = "Actualiza la información de una sección registrada mediante su id (SOLO ADMIN)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<SectionResponseDto>> modify(@Min(value = 1) @PathVariable("id") Long id,
                                                  @Validated @RequestBody SectionRequestDto sectionRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<SectionResponseDto>builder()
                        .data(sectionService.modify(id, sectionRequestDto))
                        .status(HttpStatus.OK)
                        .message(Constants.UPDATED_SECTION)
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/d/{id}")
    @PreAuthorize(Constants.ADMIN_ROLE)
    @Operation(
            summary = "Elimina una sección registrada mediante su id (SOLO ADMIN)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<String>> remove(@Min(value = 1) @PathVariable("id") Long id) {
        sectionService.remove(id);
        return new ResponseEntity<>(
                ResponseDto.<String>builder()
                        .data(Constants.DELETED_SECTION.concat(id.toString()))
                        .status(HttpStatus.OK)
                        .message(Constants.SUCCESS)
                        .build(),
                HttpStatus.OK
        );
    }
}
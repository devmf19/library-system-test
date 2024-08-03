package com.cloudlabs.library.controller;

import com.cloudlabs.library.dto.request.InvoiceRequestDto;
import com.cloudlabs.library.dto.response.InvoiceResponseDto;
import com.cloudlabs.library.dto.response.ResponseDto;
import com.cloudlabs.library.service.InvoiceService;
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
@RequestMapping("/loan")
@Tag(name = "Préstamos", description = "Préstamos de los mimebros de la biblioteca")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/all")
    @Operation(
            summary = "Muestra todos los préstamos realizados hasta la fecha",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<List<InvoiceResponseDto>>> readAll() {
        return new ResponseEntity<>(
                ResponseDto.<List<InvoiceResponseDto>>builder()
                        .data(invoiceService.readAll())
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    @Operation(
            summary = "Registrar un un préstamo de uno o más libros a un miembro",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<InvoiceResponseDto>> create(@Validated @RequestBody InvoiceRequestDto invoiceRequestDto) {
        return new ResponseEntity<>(
                ResponseDto.<InvoiceResponseDto>builder()
                        .data(invoiceService.create(invoiceRequestDto))
                        .message(Constants.CREATED_BOOk_LOAN)
                        .status(HttpStatus.CREATED)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Buscar préstamo (tiket) por id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/r/{id}")
    public ResponseEntity<ResponseDto<InvoiceResponseDto>> readById(@Min(value = 1) @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                ResponseDto.<InvoiceResponseDto>builder()
                        .data(invoiceService.readById(id))
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );

    }

    @PutMapping("/receive/{id}")
    @Operation(
            summary = "Recibir la devolución de todos los libros de un ticket (actualizar estado a RETURNED)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<InvoiceResponseDto>> receiveBooks(@Min(value = 1) @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                ResponseDto.<InvoiceResponseDto>builder()
                        .data(invoiceService.modify(id))
                        .message(Constants.RECEIVED_BOOK_LOAN)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/d/{id}")
    @PreAuthorize(Constants.ADMIN_ROLE)
    @Operation(
            summary = "Elimina un préstamo (tiket) (SOLO ADMIN)",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ResponseDto<String>> remove(@Min(value = 1) @PathVariable Long id) {
        return new ResponseEntity<>(
                ResponseDto.<String>builder()
                        .data(Constants.DELETED_BOOK_LOAN.concat(id.toString()))
                        .message(Constants.SUCCESS)
                        .status(HttpStatus.OK)
                        .build(),
                HttpStatus.OK
        );
    }
}

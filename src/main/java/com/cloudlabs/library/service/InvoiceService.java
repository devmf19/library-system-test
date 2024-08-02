package com.cloudlabs.library.service;

import com.cloudlabs.library.dto.request.InvoiceRequestDto;
import com.cloudlabs.library.dto.response.InvoiceResponseDto;

import java.util.List;

public interface InvoiceService {
    List<InvoiceResponseDto> readAll();

    InvoiceResponseDto create(InvoiceRequestDto invoiceRequestDto);

    InvoiceResponseDto readById(Long id);

    InvoiceResponseDto modify(Long id, InvoiceRequestDto invoiceRequestDto);

    void remove(Long id);
}

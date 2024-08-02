package com.cloudlabs.library.service;

import com.cloudlabs.library.dto.response.InvoiceItemResponseDto;
import com.cloudlabs.library.dto.response.InvoiceResponseDto;
import com.cloudlabs.library.model.Invoice;

import java.util.List;
import java.util.Set;

public interface InvoiceItemService {
    InvoiceResponseDto create(Set<Long> bookIds, Invoice invoice);
    List<InvoiceItemResponseDto> readByInvoice(Invoice invoice);
    InvoiceItemResponseDto readById(Long id);
    void removeByInvoice(Invoice invoice);
    void increaseStockForAllBooks(Invoice invoice);

}

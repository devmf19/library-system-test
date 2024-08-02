package com.cloudlabs.library.service;

import com.cloudlabs.library.dto.response.InvoiceItemResponseDto;
import com.cloudlabs.library.model.Book;
import com.cloudlabs.library.model.Invoice;
import com.cloudlabs.library.model.InvoiceItem;

import java.util.List;

public interface InvoiceItemService {
    void create(Invoice invoice, Book book);
    List<InvoiceItem> readByInvoice(Invoice invoice);
    InvoiceItemResponseDto readById(Long id);
    void removeByInvoice(Invoice invoice);

}

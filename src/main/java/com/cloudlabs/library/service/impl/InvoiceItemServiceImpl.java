package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.dto.response.InvoiceItemResponseDto;
import com.cloudlabs.library.mapper.InvoiceMapper;
import com.cloudlabs.library.model.Book;
import com.cloudlabs.library.model.Invoice;
import com.cloudlabs.library.model.InvoiceItem;
import com.cloudlabs.library.repository.InvoiceItemRepository;
import com.cloudlabs.library.service.InvoiceItemService;
import com.cloudlabs.library.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoiceMapper invoiceMapper;

    @Autowired
    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceItemRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public void create(Invoice invoice, Book book) {
        invoiceItemRepository.save(
                InvoiceItem.builder()
                        .invoice(invoice)
                        .book(book)
                        .build()
        );
    }

    @Override
    public List<InvoiceItem> readByInvoice(Invoice invoice) {
        return invoiceItemRepository.findByInvoice(invoice);
    }

    @Override
    public InvoiceItemResponseDto readById(Long id) {
        return invoiceMapper.itemToResponse(
                invoiceItemRepository.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(Constants.NOT_FOUND_INVOICE_ITEM)
                        )
        );
    }

    @Override
    public void removeByInvoice(Invoice invoice) {
        invoiceItemRepository.deleteByInvoice(invoice);
    }

}

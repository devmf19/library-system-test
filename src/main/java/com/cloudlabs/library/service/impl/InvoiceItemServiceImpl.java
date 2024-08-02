package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.dto.response.InvoiceItemResponseDto;
import com.cloudlabs.library.dto.response.InvoiceResponseDto;
import com.cloudlabs.library.mapper.InvoiceMapper;
import com.cloudlabs.library.model.Book;
import com.cloudlabs.library.model.Invoice;
import com.cloudlabs.library.model.InvoiceItem;
import com.cloudlabs.library.repository.InvoiceItemRepository;
import com.cloudlabs.library.service.FindSaveBookService;
import com.cloudlabs.library.service.InvoiceItemService;
import com.cloudlabs.library.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;
    private final FindSaveBookService findSaveBookService;
    private final InvoiceMapper invoiceMapper;

    @Autowired
    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceRepository,
                                  FindSaveBookService findSaveBookService,
                                  InvoiceMapper invoiceMapper) {
        this.invoiceItemRepository = invoiceRepository;
        this.findSaveBookService = findSaveBookService;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    @Transactional
    public InvoiceResponseDto create(Set<Long> bookIds, Invoice invoice) {
        bookIds.stream()
                .map(bookId -> findSaveBookService.get(bookId)
                        .orElseThrow(() -> new EntityNotFoundException(Constants.NOT_FOUND_BOOK.concat(bookId.toString())))
                ).
                peek(book -> {
                    if (book.getStock() == 0) {
                        throw new EntityNotFoundException(Constants.INSUFFICIENT_STOCK.concat(book.getName()));
                    }
                })
                .forEach(book -> {
                    invoiceItemRepository.save(InvoiceItem.builder()
                            .invoice(invoice)
                            .book(book)
                            .build()
                    );
                    book.setStock(book.getStock() - 1);
                    findSaveBookService.post(book);
                });

        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public List<InvoiceItemResponseDto> readByInvoice(Invoice invoice) {
        return invoiceMapper.itemToResponseList(
                invoiceItemRepository.findByInvoice(invoice)
        );
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

    @Override
    public void increaseStockForAllBooks(Invoice invoice) {
        invoiceItemRepository.findByInvoice(invoice)
                .forEach(bookItem -> {
                    Book book = bookItem.getBook();
                    book.setStock(book.getStock() + 1);
                    findSaveBookService.post(book);
                });
    }

}

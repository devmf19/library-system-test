package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.dto.request.InvoiceRequestDto;
import com.cloudlabs.library.dto.response.InvoiceResponseDto;
import com.cloudlabs.library.mapper.InvoiceMapper;
import com.cloudlabs.library.model.Book;
import com.cloudlabs.library.model.Invoice;
import com.cloudlabs.library.model.InvoiceItem;
import com.cloudlabs.library.repository.BookRepository;
import com.cloudlabs.library.repository.InvoiceRepository;
import com.cloudlabs.library.service.InvoiceItemService;
import com.cloudlabs.library.service.InvoiceService;
import com.cloudlabs.library.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvoiceServiceImpl extends GenericServiceImpl<Invoice, Long> implements InvoiceService {

    private final InvoiceMapper invoiceMapper;
    private final InvoiceItemService invoiceItemService;
    private final BookRepository bookRepository;

    @Autowired
    public InvoiceServiceImpl(@Qualifier("invoiceRepository") InvoiceRepository invoiceRepository,
                              InvoiceMapper invoiceMapper,
                              InvoiceItemService invoiceItemService,
                              BookRepository bookRepository) {
        super(invoiceRepository);
        this.invoiceMapper = invoiceMapper;
        this.invoiceItemService = invoiceItemService;
        this.bookRepository = bookRepository;
    }


    @Override
    public List<InvoiceResponseDto> readAll() {
        return invoiceMapper.toResponseList(this.findAll());
    }

    @Override
    @Transactional
    public InvoiceResponseDto create(InvoiceRequestDto invoiceRequestDto) {
        Invoice invoice = this.save(invoiceMapper.toEntity(invoiceRequestDto));

        invoiceRequestDto.getBooksId()
                .stream()
                .map(id -> bookRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(Constants.NOT_FOUND_BOOK.concat(id.toString())))
                )
                .peek(book -> {
                    if (book.getStock() <= 0) {
                        throw new EntityNotFoundException(Constants.INSUFFICIENT_STOCK.concat(book.getName()));
                    }
                })
                .forEach(book -> {
                    invoiceItemService.create(invoice, book);
                    book.setStock(book.getStock() - 1);
                    bookRepository.save(book);
                });

        InvoiceResponseDto invoiceResponse = invoiceMapper.toResponse(invoice);
        invoiceResponse.setBooks(
                invoiceMapper.itemToResponseList(
                        invoiceItemService.readByInvoice(invoice)
                )
        );

        return invoiceResponse;
    }

    @Override
    public InvoiceResponseDto readById(Long id) {
        return this.findById(id)
                .map(invoiceMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_INVOICE.concat(id.toString()))
                );
    }

    @Override
    @Transactional
    public InvoiceResponseDto modify(Long id, InvoiceRequestDto invoiceRequestDto) {
        Invoice existingInvoice = this.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Constants.NOT_FOUND_INVOICE.concat(id.toString()))
        );

        // Verificar si el estado de la factura es 'D' (devolución)
        if (existingInvoice.getState() == 'D') {
            throw new IllegalStateException("El estado de la factura ya es 'D' (devolución) y no puede ser cambiado.");
        }

        List<InvoiceItem> invoiceItems = invoiceItemService.readByInvoice(existingInvoice);
        if (invoiceRequestDto.getState() == 'D') {
            increaseStockForAllBooks(invoiceItems);

            existingInvoice.setState('D');
        }

        increaseStockForAllBooks(invoiceItems);

        // Eliminar los items de la factura existente
        invoiceItemService.removeByInvoice(existingInvoice);

        Invoice updatedInvoice = invoiceMapper.toEntity(invoiceRequestDto);
        updatedInvoice.setId(existingInvoice.getId());

        // Guardar la factura actualizada
        this.save(updatedInvoice);

        // Obtener el set de booksId de la nueva factura
        Set<Long> newBookIds = invoiceRequestDto.getBooksId();

        // Actualizar stock para los nuevos libros en la nueva factura
        newBookIds.forEach(bookId -> {
            Book book = bookRepository.findById(bookId).orElseThrow(
                    () -> new EntityNotFoundException(Constants.NOT_FOUND_BOOK.concat(bookId.toString()))
            );

            if (book.getStock() <= 0) {
                throw new EntityNotFoundException(Constants.INSUFFICIENT_STOCK.concat(book.getName()));
            }

            // Crear el item de la factura y reducir el stock
            invoiceItemService.create(updatedInvoice, book);
            book.setStock(book.getStock() - 1);
            bookRepository.save(book);
        });

        // Verificar el estado de la nueva factura
        if (updatedInvoice.getState() == 'D') {
            increaseStockForAllBooks(id); // Método separado para manejar devoluciones
        }

        InvoiceResponseDto invoiceResponse = invoiceMapper.toResponse(updatedInvoice);
        invoiceResponse.setBooks(invoiceItemService.readByInvoice(updatedInvoice));

        return invoiceResponse;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Invoice existingInvoice = this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constants.NOT_FOUND_INVOICE.concat(id.toString())));

        invoiceItemService.removeByInvoice(existingInvoice);

        this.delete(existingInvoice.getId());
    }


    private void increaseStockForAllBooks(List<InvoiceItem> invoiceItems) {
        invoiceItems.forEach(bookItem -> {
            Book book = bookItem.getBook();
            book.setStock(book.getStock() + 1);
            bookRepository.save(book);
        });
    }
}

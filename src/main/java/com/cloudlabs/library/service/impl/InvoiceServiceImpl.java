package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.dto.request.InvoiceRequestDto;
import com.cloudlabs.library.dto.response.InvoiceItemResponseDto;
import com.cloudlabs.library.dto.response.InvoiceResponseDto;
import com.cloudlabs.library.enums.InvoiceStatusEnum;
import com.cloudlabs.library.mapper.InvoiceMapper;
import com.cloudlabs.library.model.*;
import com.cloudlabs.library.repository.InvoiceRepository;
import com.cloudlabs.library.security.model.PrincipalUser;
import com.cloudlabs.library.service.*;
import com.cloudlabs.library.service.common.FindSaveMemberService;
import com.cloudlabs.library.service.common.FindSaveUserService;
import com.cloudlabs.library.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceServiceImpl extends GenericServiceImpl<Invoice, Long> implements InvoiceService {

    private final InvoiceMapper invoiceMapper;
    private final InvoiceItemService invoiceItemService;
    private final FindSaveUserService findSaveUserService;
    private final FindSaveMemberService findSaveMemberService;
    private final InvoiceStatusService invoiceStatusService;

    @Autowired
    public InvoiceServiceImpl(@Qualifier("invoiceRepository") InvoiceRepository invoiceRepository,
                              InvoiceMapper invoiceMapper,
                              InvoiceItemService invoiceItemService,
                              FindSaveUserService findSaveUserService,
                              FindSaveMemberService findSaveMemberService,
                              InvoiceStatusService invoiceStatusService) {
        super(invoiceRepository);
        this.invoiceMapper = invoiceMapper;
        this.invoiceItemService = invoiceItemService;
        this.findSaveUserService = findSaveUserService;
        this.findSaveMemberService = findSaveMemberService;
        this.invoiceStatusService = invoiceStatusService;
    }


    @Override
    public List<InvoiceResponseDto> readAll() {
        return invoiceMapper.toResponseList(this.findAll());
    }

    @Override
    @Transactional
    public InvoiceResponseDto create(InvoiceRequestDto invoiceRequestDto) {
        Invoice invoice = invoiceMapper.toEntity(invoiceRequestDto);
        invoice.setStatus(invoiceStatusService.readByStatus(InvoiceStatusEnum.BORROWED));

        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = findSaveUserService.get(principalUser.getId()).orElseThrow(
                () -> new EntityNotFoundException(Constants.NOT_FOUND_USER.concat(principalUser.getId().toString()))
        );

        Member member = findSaveMemberService.get(invoiceRequestDto.getMemberId()).orElseThrow(
                () -> new EntityNotFoundException(Constants.NOT_FOUND_MEMBER.concat(invoiceRequestDto.getMemberId().toString()))
        );

        invoice.setUser(user);
        invoice.setMember(member);
        invoice.setStartDate(LocalDate.now());

        return invoiceItemService.create(invoiceRequestDto.getBooksId(), this.save(invoice));
    }

    @Override
    public InvoiceResponseDto readById(Long id) {
        Invoice invoice = this.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Constants.NOT_FOUND_INVOICE.concat(id.toString()))
        );

        return this.findById(id)
                .map(invoiceMapper::toResponse)
                .map(invoiceResponseDto -> {
                    invoiceResponseDto.setBooks(invoiceItemService.readByInvoice(invoice));
                    return invoiceResponseDto;
                })
                .orElseThrow(
                        () -> new EntityNotFoundException(Constants.NOT_FOUND_INVOICE.concat(id.toString()))
                );
    }

    @Override
    @Transactional
    public InvoiceResponseDto modify(Long id) {
        Invoice invoice = this.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Constants.NOT_FOUND_INVOICE.concat(id.toString()))
        );

        if (invoice.getStatus().getStatus().equals(InvoiceStatusEnum.RETURNED)) {
            throw new EntityNotFoundException(Constants.OLD_BOOK_LOAN_RECEIVED);
        }

        List<InvoiceItemResponseDto> itemResponseDtoList = invoiceItemService.readByInvoice(invoice);

        invoice.setStatus(invoiceStatusService.readByStatus(InvoiceStatusEnum.RETURNED));
        invoiceItemService.increaseStockForAllBooks(invoice);

        InvoiceResponseDto invoiceResponseDto = invoiceMapper.toResponse(this.save(invoice));
        invoiceResponseDto.setBooks(itemResponseDtoList);

        return invoiceResponseDto;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Invoice invoice = this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constants.NOT_FOUND_INVOICE.concat(id.toString())));

        invoiceItemService.removeByInvoice(invoice);

        if (invoice.getStatus().getStatus().equals(InvoiceStatusEnum.BORROWED))
            invoiceItemService.increaseStockForAllBooks(invoice);

        this.delete(invoice.getId());
    }
}

package com.cloudlabs.library.service.impl;

import com.cloudlabs.library.enums.InvoiceStatusEnum;
import com.cloudlabs.library.model.InvoiceStatus;
import com.cloudlabs.library.repository.InvoiceStatusRepository;
import com.cloudlabs.library.service.InvoiceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceStatusServiceImpl implements InvoiceStatusService {

    private final InvoiceStatusRepository invoiceStatusRepository;

    @Autowired
    public InvoiceStatusServiceImpl(InvoiceStatusRepository invoiceStatusRepository) {
        this.invoiceStatusRepository = invoiceStatusRepository;
    }

    @Override
    public InvoiceStatus readByStatus(InvoiceStatusEnum status) {
        return invoiceStatusRepository.findByStatus(status);
    }
}
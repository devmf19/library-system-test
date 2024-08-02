package com.cloudlabs.library.service;

import com.cloudlabs.library.enums.InvoiceStatusEnum;
import com.cloudlabs.library.model.InvoiceStatus;

public interface InvoiceStatusService {
    InvoiceStatus readByStatus(InvoiceStatusEnum name);
}

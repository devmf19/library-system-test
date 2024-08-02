package com.cloudlabs.library.repository;

import com.cloudlabs.library.enums.InvoiceStatusEnum;
import com.cloudlabs.library.model.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatus, Long> {
    InvoiceStatus findByStatus(InvoiceStatusEnum status);
}

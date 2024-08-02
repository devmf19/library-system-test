package com.cloudlabs.library.repository;

import com.cloudlabs.library.model.Invoice;
import com.cloudlabs.library.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("invoiceItemRepository")
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    List<InvoiceItem> findByInvoice(Invoice invoice);
    void deleteByInvoice(Invoice invoice);
}

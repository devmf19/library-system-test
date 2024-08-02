package com.cloudlabs.library.repository;

import com.cloudlabs.library.model.Invoice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("invoiceRepository")
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}

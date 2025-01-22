package com.analitrix.sellbook.repository;

import com.analitrix.sellbook.model.InvoiceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceUserRepository extends JpaRepository <InvoiceUser, String> {
}

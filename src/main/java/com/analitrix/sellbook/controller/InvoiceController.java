package com.analitrix.sellbook.controller;

import java.util.List;

import com.analitrix.sellbook.dto.BookDtoId;
import com.analitrix.sellbook.dto.InvoiceDtoCreate;
import com.analitrix.sellbook.dto.PersonDtoId;
import com.analitrix.sellbook.service.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Invoice")
@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@PostMapping("/create")
	public ResponseEntity<String> createInvoice(@RequestBody InvoiceDtoCreate invoiceDtoCreate) {
		return invoiceService.createInvoice(invoiceDtoCreate);
	}
}

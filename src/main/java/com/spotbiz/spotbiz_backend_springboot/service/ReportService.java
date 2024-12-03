package com.spotbiz.spotbiz_backend_springboot.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import com.spotbiz.spotbiz_backend_springboot.repo.SubscriptionBillingRepo;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {

    private final SubscriptionBillingRepo subscriptionBillingRepository;

    public ReportService(SubscriptionBillingRepo subscriptionBillingRepository) {
        this.subscriptionBillingRepository = subscriptionBillingRepository;
    }

    public byte[] generateReport() {
        List<SubscriptionBilling> billings = subscriptionBillingRepository.findAll();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Subscription Billing Report"));
        document.add(new Paragraph("Generated on: " + java.time.LocalDate.now()));

        for (SubscriptionBilling billing : billings) {
            document.add(new Paragraph("Billing ID: " + billing.getSubscriptionBillingId()));
            document.add(new Paragraph("Package: " + billing.getPkg().getFeature()));
            document.add(new Paragraph("Business: " + billing.getBusiness().getName()));
            document.add(new Paragraph("Billing Date: " + billing.getBillingDate()));
            document.add(new Paragraph("Billing Status: " + billing.getBillingStatus()));
            document.add(new Paragraph("Amount: $" + billing.getAmount()));
            document.add(new Paragraph("Is Active: " + billing.getIsActive()));
            document.add(new Paragraph("---------------------------------------------------"));
        }

        document.close();

        return outputStream.toByteArray();
    }
}

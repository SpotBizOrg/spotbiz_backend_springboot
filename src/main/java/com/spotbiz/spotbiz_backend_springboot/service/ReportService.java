package com.spotbiz.spotbiz_backend_springboot.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.spotbiz.spotbiz_backend_springboot.entity.ReimbursementStatus;
import com.spotbiz.spotbiz_backend_springboot.entity.Reimbursements;
import com.spotbiz.spotbiz_backend_springboot.entity.SubscriptionBilling;
import com.spotbiz.spotbiz_backend_springboot.repo.ReimbursementRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.SubscriptionBillingRepo;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private final SubscriptionBillingRepo subscriptionBillingRepository;
    private final ReimbursementRepo reimbursementRepo;

    public ReportService(SubscriptionBillingRepo subscriptionBillingRepository, ReimbursementRepo reimbursementRepo) {
        this.subscriptionBillingRepository = subscriptionBillingRepository;
        this.reimbursementRepo = reimbursementRepo;
    }

    public byte[] generateReport() throws IOException {
        try {
            List<SubscriptionBilling> billings = subscriptionBillingRepository.findAll();
            List<Reimbursements> reimbursements = reimbursementRepo.findAllByStatus(ReimbursementStatus.PAYED);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Set font to Times New Roman
            // Set the font using PdfFontFactory
            PdfFont font = PdfFontFactory.createFont("Times-Roman", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
            document.setFont(font);        // Add heading with logo and transaction title
            addHeader(document);

            // Add Subscription Billings Table
            addSubscriptionBillingTable(document, billings);

            // Add Coupon Reimbursements Table
            addReimbursementTable(document, reimbursements);

            // Add footer with report generation date
            document.add(new Paragraph("\nReport generated on: " + LocalDate.now())
                    .setTextAlignment(TextAlignment.RIGHT));

            document.close();

            return outputStream.toByteArray();
        } catch (MalformedURLException e1){
            throw new MalformedURLException("Error occurred while generating the report");
        } catch (IOException e2){
            throw new IOException("Error occurred while generating the report");
        }
    }

    private void addHeader(Document document) throws MalformedURLException {
        // Add logo image
        document.add(new Image(ImageDataFactory.create("src/main/resources/static/final.png"))
                .setWidth(100)
                .setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER));

        // Add Transaction History heading
        document.add(new Paragraph("Transaction History")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER));

        // Add report generation date
        document.add(new Paragraph("Generated on: " + LocalDate.now())
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));
    }

    private void addSubscriptionBillingTable(Document document, List<SubscriptionBilling> billings) {
        document.add(new Paragraph("Subscription Billings")
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(5));

        // Define table column widths
        float[] columnWidths = {1, 2, 2, 2, 2, 1};
        Table table = new Table(columnWidths);

        // Add table header
        table.addHeaderCell(new Cell().add(new Paragraph("ID")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Package")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Business")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Billing Date")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Status")).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Amount")).setBold());

        // Add table rows with data
        for (SubscriptionBilling billing : billings) {
            table.addCell(new Paragraph(billing.getSubscriptionBillingId().toString()));
            table.addCell(new Paragraph(billing.getPkg().getFeature()));
            table.addCell(new Paragraph(billing.getBusiness().getName()));
            table.addCell(new Paragraph(billing.getBillingDate().toString()));
            table.addCell(new Paragraph(billing.getBillingStatus()));
            table.addCell(new Paragraph("Rs. " + billing.getAmount()));
        }

        // Add table to document
        document.add(table);
    }

    private void addReimbursementTable(Document document, List<Reimbursements> reimbursements) {
        document.add(new Paragraph("Coupon Reimbursements")
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(5));

        // Define table column widths
        float[] columnWidths2 = {1, 2, 2, 2};
        Table table2 = new Table(columnWidths2);

        // Add table header
        table2.addHeaderCell(new Cell().add(new Paragraph("ID")).setBold());
        table2.addHeaderCell(new Cell().add(new Paragraph("Business")).setBold());
        table2.addHeaderCell(new Cell().add(new Paragraph("Billing Date")).setBold());
        table2.addHeaderCell(new Cell().add(new Paragraph("Amount")).setBold());

        // Add table rows with data
        for (Reimbursements reimbursement : reimbursements) {
            table2.addCell(new Paragraph(String.valueOf(reimbursement.getId())));
            table2.addCell(new Paragraph(reimbursement.getBusiness().getName()));
            table2.addCell(new Paragraph(reimbursement.getDateTime().toString()));
            table2.addCell(new Paragraph("Rs. " + reimbursement.getAmount()));
        }

        // Add table to document
        document.add(table2);
    }
}

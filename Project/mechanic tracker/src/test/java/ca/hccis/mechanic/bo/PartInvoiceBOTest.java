package ca.hccis.mechanic.bo;

import ca.hccis.mechanic.jpa.entity.PartInvoice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PartInvoiceBOTest {

    @Test
    public void testCalculateTotalCost_NormalCase() {
        // Test the main calculation logic
        // Arrange
        PartInvoice invoice = new PartInvoice();
        invoice.setPartPrice(new BigDecimal("25.50"));
        invoice.setPartQuantity(4);
        invoice.setTaxRate(new BigDecimal("15.0"));

        // Act
        PartInvoiceBO.calculateTotalCost(invoice);

        // Assert
        // Calculation: 25.50 * 4 = 102.00
        // Tax: 102.00 * 0.15 = 15.30
        // Total: 102.00 + 15.30 = 117.30
        BigDecimal expectedTotal = new BigDecimal("117.30");
        assertEquals(0, expectedTotal.compareTo(invoice.getTotalCost()),
                "Total cost should be 117.30 (25.50 * 4 = 102.00 + 15% tax = 15.30)");
    }

    @Test
    public void testCalculateTotalCost_WithZeroQuantity() {
        // Test edge case: zero quantity
        // Arrange
        PartInvoice invoice = new PartInvoice();
        invoice.setPartPrice(new BigDecimal("100.00"));
        invoice.setPartQuantity(0);
        invoice.setTaxRate(new BigDecimal("13.0"));

        // Act
        PartInvoiceBO.calculateTotalCost(invoice);

        // Assert
        BigDecimal expectedTotal = new BigDecimal("0.00");
        assertEquals(0, expectedTotal.compareTo(invoice.getTotalCost()),
                "Total cost should be 0.00 when quantity is zero");
    }
}
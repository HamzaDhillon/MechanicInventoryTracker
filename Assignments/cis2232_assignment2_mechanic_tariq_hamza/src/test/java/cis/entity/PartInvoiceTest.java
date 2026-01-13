package cis.entity;

import cis.exception.MechanicTrackerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for PartInvoice class using Test Driven Development approach.
 * Tests the calculateTotal() method with various scenarios.
 * Ensures proper exceptions for invalid input values.
 *
 * Author: Hamza Tariq
 * Since: 2025-01
 */
public class PartInvoiceTest {

    private PartInvoice invoice;

    @BeforeEach
    public void setUp() {
        invoice = new PartInvoice();
    }

    // 1. Basic calculation with positive values
    @Test
    public void testCalculateTotal_BasicCalculation() {
        invoice.setPartPrice(100.0);
        invoice.setPartQuantity(2);
        invoice.setTaxRate(0.15);
        invoice.calculateTotal();
        Assertions.assertEquals(230.0, invoice.getTotalCost(), 0.001);
    }

    // 2. Zero quantity should result in zero total
    @Test
    public void testCalculateTotal_ZeroQuantity() {
        invoice.setPartPrice(50.0);
        invoice.setPartQuantity(0);
        invoice.setTaxRate(0.10);
        invoice.calculateTotal();
        Assertions.assertEquals(0.0, invoice.getTotalCost(), 0.001);
    }

    // 3. Zero tax rate should result in no tax added
    @Test
    public void testCalculateTotal_ZeroTaxRate() {
        invoice.setPartPrice(25.0);
        invoice.setPartQuantity(4);
        invoice.setTaxRate(0.0);
        invoice.calculateTotal();
        Assertions.assertEquals(100.0, invoice.getTotalCost(), 0.001);
    }

    // 4. Large quantity calculation
    @Test
    public void testCalculateTotal_LargeQuantity() {
        invoice.setPartPrice(1.5);
        invoice.setPartQuantity(1000);
        invoice.setTaxRate(0.13);
        invoice.calculateTotal();
        double expected = (1.5 * 1000) * 1.13;
        Assertions.assertEquals(expected, invoice.getTotalCost(), 0.001);
    }

    // 5. High tax rate calculation
    @Test
    public void testCalculateTotal_HighTaxRate() {
        invoice.setPartPrice(200.0);
        invoice.setPartQuantity(1);
        invoice.setTaxRate(0.50);
        invoice.calculateTotal();
        Assertions.assertEquals(300.0, invoice.getTotalCost(), 0.001);
    }

    // 6. Decimal precision handling
    @Test
    public void testCalculateTotal_DecimalPrecision() {
        invoice.setPartPrice(19.99);
        invoice.setPartQuantity(3);
        invoice.setTaxRate(0.0875);
        invoice.calculateTotal();
        double subtotal = 19.99 * 3;
        double expected = subtotal + (subtotal * 0.0875);
        Assertions.assertEquals(expected, invoice.getTotalCost(), 0.001);
    }

    // 7. Complex scenario with multiple validations
    @Test
    public void testCalculateTotal_ComplexScenario() {
        invoice.setPartPrice(75.50);
        invoice.setPartQuantity(10);
        invoice.setTaxRate(0.14975);
        invoice.calculateTotal();
        double subtotal = 75.50 * 10;
        Assertions.assertTrue(invoice.getTotalCost() > subtotal);
    }

    // 8. Edge case with minimum positive values
    @Test
    public void testCalculateTotal_MinimumValues() {
        invoice.setPartPrice(0.01);
        invoice.setPartQuantity(1);
        invoice.setTaxRate(0.01);
        invoice.calculateTotal();
        Assertions.assertEquals(0.0101, invoice.getTotalCost(), 0.0001);
    }

    // 9. Exception: negative part price
    @Test
    public void testSetPartPrice_Negative_ThrowsException() {
        Assertions.assertThrows(MechanicTrackerException.class, () -> {
            invoice.setPartPrice(-10.0);
        });
    }

    // 10. Exception: negative part quantity
    @Test
    public void testSetPartQuantity_Negative_ThrowsException() {
        Assertions.assertThrows(MechanicTrackerException.class, () -> {
            invoice.setPartQuantity(-5);
        });
    }

}

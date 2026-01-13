package ca.hccis.mechanic.bo;

import ca.hccis.mechanic.jpa.entity.PartInvoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class PartInvoiceBusinessValidatorTest {

    private PartInvoiceBusinessValidator validator;
    private PartInvoice invoice;
    private Errors errors;

    @BeforeEach
    public void setUp() {
        validator = new PartInvoiceBusinessValidator();
        invoice = new PartInvoice();
        errors = new BeanPropertyBindingResult(invoice, "partInvoice");
    }

    @Test
    public void testTransactionDateInFuture_ShouldFail() {
        // Test Business Rule 1: Transaction date cannot be in the future
        // Arrange
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        invoice.setTransactionDate(tomorrow.format(formatter));

        // Act
        validator.validate(invoice, errors);

        // Assert
        assertTrue(errors.hasErrors(), "Should have errors for future date");
        assertNotNull(errors.getFieldError("transactionDate"),
                "Should have error on transactionDate field");
        assertEquals("transactionDate.future",
                errors.getFieldError("transactionDate").getCode());
    }

    @Test
    public void testPartQuantityTooLarge_ShouldFail() {
        // Test Business Rule 2: Part quantity cannot exceed 1000
        // Arrange
        invoice.setPartQuantity(1001);

        // Act
        validator.validate(invoice, errors);

        // Assert
        assertTrue(errors.hasErrors(), "Should have errors for quantity > 1000");
        assertNotNull(errors.getFieldError("partQuantity"),
                "Should have error on partQuantity field");
        assertEquals("partQuantity.tooLarge",
                errors.getFieldError("partQuantity").getCode());
    }
}
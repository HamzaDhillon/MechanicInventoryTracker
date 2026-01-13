package ca.hccis.mechanic.bo;

import ca.hccis.mechanic.jpa.entity.PartInvoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Business validation rules for PartInvoice that are not covered by JSR-303 annotations.
 *
 * Business Rules:
 * 1. Transaction date cannot be in the future
 * 2. Part quantity must be between 1 and 1000 (not just positive)
 */
@Component
public class PartInvoiceBusinessValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(PartInvoiceBusinessValidator.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean supports(Class<?> clazz) {
        return PartInvoice.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PartInvoice partInvoice = (PartInvoice) target;

        // Rule 1: Transaction date cannot be in the future
        validateTransactionDate(partInvoice, errors);

        // Rule 2: Part quantity must be reasonable (1-1000)
        validatePartQuantity(partInvoice, errors);
    }

    private void validateTransactionDate(PartInvoice partInvoice, Errors errors) {
        if (partInvoice.getTransactionDate() != null && !partInvoice.getTransactionDate().isEmpty()) {
            try {
                LocalDate transactionDate = LocalDate.parse(partInvoice.getTransactionDate(), DATE_FORMATTER);
                LocalDate today = LocalDate.now();

                if (transactionDate.isAfter(today)) {
                    errors.rejectValue("transactionDate",
                            "transactionDate.future",
                            "Transaction date cannot be in the future");
                    logger.warn("Business validation failed: Transaction date {} is in the future",
                            partInvoice.getTransactionDate());
                }
            } catch (DateTimeParseException e) {
                // Date format validation is handled by JSR-303
                logger.debug("Invalid date format: {}", partInvoice.getTransactionDate());
            }
        }
    }

    private void validatePartQuantity(PartInvoice partInvoice, Errors errors) {
        if (partInvoice.getPartQuantity() != null) {
            if (partInvoice.getPartQuantity() <= 0) {
                errors.rejectValue("partQuantity",
                        "partQuantity.nonPositive",
                        "Part quantity must be at least 1");
                logger.warn("Business validation failed: Part quantity {} is not positive",
                        partInvoice.getPartQuantity());
            }
            if (partInvoice.getPartQuantity() > 1000) {
                errors.rejectValue("partQuantity",
                        "partQuantity.tooLarge",
                        "Part quantity cannot exceed 1000");
                logger.warn("Business validation failed: Part quantity {} exceeds maximum",
                        partInvoice.getPartQuantity());
            }
        }
    }
}
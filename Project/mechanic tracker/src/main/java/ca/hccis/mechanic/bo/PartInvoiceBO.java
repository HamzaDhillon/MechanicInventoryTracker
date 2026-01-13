package ca.hccis.mechanic.bo;

import ca.hccis.mechanic.dao.PartInvoiceDAO;
import ca.hccis.mechanic.jpa.entity.PartInvoice;
import ca.hccis.mechanic.util.CisUtilityFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartInvoiceBO {

    private static final Logger logger = LoggerFactory.getLogger(PartInvoiceBO.class);

    //--------------------------------------------------------------------------
    // REPORT 1: Transaction Summary (Date Range)
    //--------------------------------------------------------------------------
    /**
     * Process the Transaction Summary (Date Range) report.
     * This method retrieves all PartInvoice line items within the specified date range.
     *
     * @param start The start date.
     * @param end The end date.
     * @return List of PartInvoice objects.
     * @author BJM
     * @since 20241025
     */
    public ArrayList<PartInvoice> processDateRangeReport(String start, String end) throws SQLException {

        //**********************************************************************
        // For the reports, the requirements state that you are to use jdbc
        // to obtain the data for the report.
        //**********************************************************************
        PartInvoiceDAO partInvoiceDAO = new PartInvoiceDAO();
        ArrayList<PartInvoice> partInvoices;

        // Exception handling is declared in the method signature (throws SQLException)
        partInvoices = partInvoiceDAO.selectAllByDateRange(start, end);

        //Also write the report to a file
        CisUtilityFile.writeReportToFile("transactionSummaryReport", partInvoices);

        return partInvoices;
    }

    //--------------------------------------------------------------------------
    // REPORT 2: Part Sales Value (Part Number)
    //--------------------------------------------------------------------------
    /**
     * Process the Part Sales Value (Part Number) report.
     * This method retrieves aggregated sales data (Total Qty Sold, Total Revenue)
     * for a given part number.
     *
     * @param partNumber The Part Number to aggregate data for.
     * @return List containing a single PartInvoice object with aggregated values.
     * @author BJM
     * @since 20241025
     */
    public ArrayList<PartInvoice> processPartSalesValueReport(String partNumber) throws SQLException {

        //**********************************************************************
        // For the reports, the requirements state that you are to use jdbc
        // to obtain the data for the report.
        //**********************************************************************
        PartInvoiceDAO partInvoiceDAO = new PartInvoiceDAO();
        ArrayList<PartInvoice> aggregateResults;

        // Exception handling is declared in the method signature (throws SQLException)
        aggregateResults = partInvoiceDAO.selectAggregateSalesByPartNumber(partNumber);

        //Also write the report to a file
        CisUtilityFile.writeReportToFile("partSalesValueReport", aggregateResults);

        return aggregateResults;
    }

    //--------------------------------------------------------------------------
    // BUSINESS LOGIC METHOD (Static method for calculation)
    //--------------------------------------------------------------------------

    /**
     * Calculate total cost based on quantity, price, and tax rate
     * This is a static method that can be called without instantiating PartInvoiceBO
     *
     * @param partInvoice The PartInvoice object to calculate total cost for
     */
    public static void calculateTotalCost(PartInvoice partInvoice) {
        if (partInvoice.getPartPrice() != null &&
                partInvoice.getPartQuantity() != null &&
                partInvoice.getTaxRate() != null) {

            try {
                BigDecimal quantity = new BigDecimal(partInvoice.getPartQuantity());
                BigDecimal price = partInvoice.getPartPrice();
                BigDecimal taxRate = partInvoice.getTaxRate();

                // Calculate subtotal: price * quantity
                BigDecimal subtotal = price.multiply(quantity);

                // Calculate tax amount: subtotal * (taxRate / 100)
                BigDecimal taxAmount = subtotal.multiply(taxRate)
                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

                // Calculate total: subtotal + taxAmount
                BigDecimal totalCost = subtotal.add(taxAmount).setScale(2, RoundingMode.HALF_UP);

                partInvoice.setTotalCost(totalCost);

                logger.debug("Calculated total cost: {} (Subtotal: {}, Tax: {}, Qty: {}, Price: {}, Tax Rate: {})",
                        totalCost, subtotal, taxAmount, quantity, price, taxRate);

            } catch (Exception e) {
                logger.error("Error calculating total cost: {}", e.getMessage());
                partInvoice.setTotalCost(BigDecimal.ZERO);
            }
        } else {
            partInvoice.setTotalCost(BigDecimal.ZERO);
        }
    }
    // Add this method to PartInvoiceBO.java

    /**
     * Generate next invoice ID in pattern INV-XXX
     * This business logic should be in the BO class, not the controller
     *
     * @param existingInvoices List of existing PartInvoice objects
     * @return Next invoice ID (e.g., "INV-001")
     */
    public static String generateNextInvoiceId(List<PartInvoice> existingInvoices) {
        if (existingInvoices == null || existingInvoices.isEmpty()) {
            return "INV-001";
        }

        // Find the highest invoice ID
        String highestId = existingInvoices.stream()
                .map(PartInvoice::getInvoiceID)
                .filter(id -> id != null && id.startsWith("INV-"))
                .max(String::compareTo)
                .orElse("INV-000");

        // Extract the numeric part and increment
        if (highestId.startsWith("INV-")) {
            try {
                int lastNumber = Integer.parseInt(highestId.substring(4));
                int nextNumber = lastNumber + 1;
                return String.format("INV-%03d", nextNumber);
            } catch (NumberFormatException e) {
                logger.warn("Could not parse numeric part from ID: {}, starting from INV-001", highestId);
                return "INV-001";
            }
        }

        return "INV-001";
    }

}
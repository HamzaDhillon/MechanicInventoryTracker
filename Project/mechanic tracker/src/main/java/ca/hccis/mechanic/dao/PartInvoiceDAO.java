package ca.hccis.mechanic.dao;

import ca.hccis.mechanic.jpa.entity.PartInvoice;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Optional;  // Add this import
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO class to access ticket orders.
 *
 * @author bjmaclean
 * @since 20220621
 */
public class PartInvoiceDAO {

    private static ResultSet rs;
    private static Connection conn = null;
    private static final Logger logger = LoggerFactory.getLogger(PartInvoiceDAO.class);

    public PartInvoiceDAO() {

        String propFileName = "application";
        ResourceBundle rb = ResourceBundle.getBundle(propFileName);
        String connectionString = rb.getString("spring.datasource.url");
        String userName = rb.getString("spring.datasource.username");
        String password = rb.getString("spring.datasource.password");

        try {
            conn = DriverManager.getConnection(connectionString, userName, password);
        } catch (SQLException e) {
            logger.error(e.toString());
        }

    }

    /**
     * Select all
     *
     * @since 20210924
     * @author BJM
     */
    public ArrayList<PartInvoice> selectAll() throws SQLException {
        ArrayList<PartInvoice> invoices = new ArrayList<>();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            // SQL selects all columns from the PartInvoice table
            rs = stmt.executeQuery("SELECT partNumber, partPrice, custName, vehicle, partQuantity, " +
                    "invoiceID, taxRate, totalCost, transactionDate FROM PartInvoice;");

            while (rs.next()) {
                PartInvoice invoice = new PartInvoice();

                invoice.setPartNumber(rs.getString("partNumber"));
                invoice.setPartPrice(BigDecimal.valueOf(rs.getDouble("partPrice")));
                invoice.setCustName(rs.getString("custName"));
                invoice.setVehicle(rs.getString("vehicle"));
                invoice.setPartQuantity(rs.getInt("partQuantity"));
                invoice.setInvoiceID(rs.getString("invoiceID"));
                invoice.setTaxRate(BigDecimal.valueOf(rs.getDouble("taxRate")));
                invoice.setTotalCost(BigDecimal.valueOf(rs.getDouble("totalCost")));
                invoice.setTransactionDate(rs.getString("transactionDate"));

                invoices.add(invoice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println("There was an error closing the statement");
            }
        }
        return invoices;
    }
    public Optional<PartInvoice> selectById(String id) {
        // Implementation depends on your data access layer
        // This is a simplified example - adjust based on your actual DAO implementation
        try {
            List<PartInvoice> allInvoices = selectAll();
            return allInvoices.stream()
                    .filter(invoice -> id.equals(invoice.getInvoiceID()))
                    .findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    //-------------------------------------------------------------------------
    // REPORT 1: Transaction Summary (Date Range)
    //-------------------------------------------------------------------------

    /**
     * Select all PartInvoice records by transaction date range.
     *
     * @param start The start date (YYYY-MM-DD).
     * @param end   The end date (YYYY-MM-DD).
     * @return List of PartInvoice records.
     * @author BJM (Refactored)
     */
    public ArrayList<PartInvoice> selectAllByDateRange(String start, String end) throws SQLException {
        ArrayList<PartInvoice> invoices = new ArrayList<>();
        PreparedStatement ps = null;

        try {
            // Filter by transactionDate (REQUIRED FOR REPORT 2)
            String sqlStatement = "SELECT partNumber, partPrice, custName, vehicle, partQuantity, " +
                    "invoiceID, taxRate, totalCost, transactionDate FROM PartInvoice " +
                    "WHERE transactionDate >= ? AND transactionDate <= ? " +
                    "ORDER BY transactionDate DESC;";

            ps = conn.prepareStatement(sqlStatement);
            ps.setString(1, start);
            ps.setString(2, end);

            rs = ps.executeQuery();

            while (rs.next()) {
                PartInvoice invoice = new PartInvoice();

                invoice.setPartNumber(rs.getString("partNumber"));
                invoice.setPartPrice(BigDecimal.valueOf(rs.getDouble("partPrice")));
                invoice.setCustName(rs.getString("custName"));
                invoice.setVehicle(rs.getString("vehicle"));
                invoice.setPartQuantity(rs.getInt("partQuantity"));
                invoice.setInvoiceID(rs.getString("invoiceID"));
                invoice.setTaxRate(BigDecimal.valueOf(rs.getDouble("taxRate")));
                invoice.setTotalCost(BigDecimal.valueOf(rs.getDouble("totalCost")));
                invoice.setTransactionDate(rs.getString("transactionDate"));

                invoices.add(invoice);
            }

        } catch (SQLException e) {
            logger.error("Error running date range report:", e);
            throw e;

        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return invoices;
    }

    //-------------------------------------------------------------------------
    // REPORT 2: Part Sales Value (Part Number)
    //-------------------------------------------------------------------------

    /**
     * Select aggregate sales data for a specific part number.
     *
     * @param partNumber The part number to search for.
     * @return A list containing a single PartInvoice object, where the fields
     * hold the aggregate values (Total Qty Sold, Total Revenue).
     * @author BJM (Refactored)
     */
    public ArrayList<PartInvoice> selectAggregateSalesByPartNumber(String partNumber) throws SQLException {
        ArrayList<PartInvoice> aggregateData = new ArrayList<>();
        PreparedStatement ps = null;

        try {
            logger.info("Executing part sales query for part number: {}", partNumber);

            String sqlStatement = "SELECT partNumber, SUM(partQuantity) AS TotalQtySold, " +
                    "SUM(partPrice * partQuantity) AS TotalRevenue " +
                    "FROM PartInvoice WHERE partNumber = ? " +
                    "GROUP BY partNumber;";

            ps = conn.prepareStatement(sqlStatement);
            ps.setString(1, partNumber);

            rs = ps.executeQuery();

            if (rs.next()) {
                PartInvoice aggregate = new PartInvoice();
                aggregate.setPartNumber(rs.getString("partNumber"));
                aggregate.setPartQuantity(rs.getInt("TotalQtySold"));
                aggregate.setTotalCost(BigDecimal.valueOf(rs.getDouble("TotalRevenue")));
                aggregateData.add(aggregate);
                logger.info("Found aggregate data for part {}: Qty={}, Revenue={}",
                        partNumber, aggregate.getPartQuantity(), aggregate.getTotalCost());
            } else {
                logger.info("No data found for part number: {}", partNumber);
            }

        } catch (SQLException e) {
            logger.error("Error running part sales value report:", e);
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return aggregateData;
    }

}
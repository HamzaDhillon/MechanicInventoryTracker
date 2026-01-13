package ca.hccis.mechanic.controllers;
import ca.hccis.mechanic.dao.PartInvoiceDAO;
import ca.hccis.mechanic.bo.PartInvoiceBO; // New BO Import
import ca.hccis.mechanic.entity.ReportPartInvoice; // New DTO Import
import ca.hccis.mechanic.jpa.entity.PartInvoice; // New Entity Import
import ca.hccis.mechanic.util.CisUtility; // Utility class
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList; // Needed for casting/initialization

/**
 * Controller to administer reports of the project (Mechanic Tracker).
 * This controller handles the non-CRUD report requirements for Sprint 2.
 *
 * @author BJM (Refactored for Mechanic Tracker)
 * @since 2024-10-25
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    /**
     * Send the user to list of reports view.
     */
    @RequestMapping("")
    public String home(Model model, HttpSession session) {
        logger.info("Running the reports controller base method");
        return "report/list";
    }


    // -------------------------------------------------------------------------
    // REPORT 1: Transaction Summary (Date Range)
    // -------------------------------------------------------------------------

    /**
     * Method to send user to the date range report input view.
     */
    @RequestMapping("/invoice/daterange")
    public String reportTransactionDateRange(Model model, HttpSession session) {
        logger.info("Running the reports controller date range method");

        // Set default dates for the report DTO
        String currentDate = CisUtility.getCurrentDate("yyyy-MM-dd");
        session.setAttribute("currentDate", currentDate);

        String start = CisUtility.getCurrentDate(-30, "yyyy-MM-dd");
        String end = CisUtility.getCurrentDate(+30, "yyyy-MM-dd");

        ReportPartInvoice reportInput = new ReportPartInvoice();
        reportInput.setDateStart(start);
        reportInput.setDateEnd(end);

        model.addAttribute("reportInput", reportInput);

        return "report/reportTransactionDateRange";
    }

    /**
     * Process the Transaction Summary (Date Range) report.
     */
    @RequestMapping("/invoice/daterange/submit")
    public String reportTransactionDateRangeSubmit(Model model, @ModelAttribute("reportInput") ReportPartInvoice reportInput) {

        System.out.println("Date range from input form: " + reportInput.getDateStart() + " to " + reportInput.getDateEnd());

        PartInvoiceBO partInvoiceBO = new PartInvoiceBO(); // Instantiate BO
        List<PartInvoice> theList = new ArrayList<>();

        try {
            // Call non-static BO method to process report and write file
            theList = partInvoiceBO.processDateRangeReport(reportInput.getDateStart(), reportInput.getDateEnd());
        } catch (SQLException e) {
            // Handle SQLException from DAO layer
            model.addAttribute("message", "Database Error: Exception occurred while fetching data.");
            System.err.println("SQL Error in Date Range Report: " + e.getMessage());
        }

        reportInput.setPartInvoices((ArrayList<PartInvoice>) theList); // Set results

        // Add a message in case the report does not contain any data
        if (theList.isEmpty()) {
            model.addAttribute("message", "No transactions found for that date range.");
        }

        model.addAttribute("reportInput", reportInput);

        return "report/reportTransactionDateRange";
    }

    // -------------------------------------------------------------------------
    // REPORT 2: Part Sales Value (Part Number)
    // -------------------------------------------------------------------------

    /**
     * Method to send user to the Part Sales Value report input view.
     */
    @RequestMapping("/invoice/partsales")
    public String reportPartSalesValue(Model model) {
        logger.info("Running the reports controller part sales method");

        model.addAttribute("reportInput", new ReportPartInvoice());

        return "report/reportPartSalesValue";
    }

    /**
     * Process the Part Sales Value (Part Number) report.
     */
    @RequestMapping("/invoice/partsales/submit")
    public String reportPartSalesValueSubmit(Model model, @ModelAttribute("reportInput") ReportPartInvoice reportInput) {

        System.out.println("Part Number from input form: " + reportInput.getPartNumber());

        PartInvoiceBO partInvoiceBO = new PartInvoiceBO(); // Instantiate BO
        List<PartInvoice> theList = new ArrayList<>();

        try {
            // Call non-static BO method to process report and write file
            theList = partInvoiceBO.processPartSalesValueReport(reportInput.getPartNumber());
        } catch (SQLException e) {
            // Handle SQLException from DAO layer
            model.addAttribute("message", "Database Error: Exception occurred while fetching aggregate data.");
            System.err.println("SQL Error in Part Sales Report: " + e.getMessage());
        }

        reportInput.setPartInvoices((ArrayList<PartInvoice>) theList); // Set results

        // Add a message in case the report does not contain any data
        if (theList.isEmpty()) {
            model.addAttribute("message", "No sales data found for Part Number: " + reportInput.getPartNumber());
        }

        model.addAttribute("reportInput", reportInput);

        return "report/reportPartSalesValue";
    }

}

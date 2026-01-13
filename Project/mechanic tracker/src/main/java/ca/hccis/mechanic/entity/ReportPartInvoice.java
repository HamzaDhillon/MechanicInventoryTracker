package ca.hccis.mechanic.entity;

import ca.hccis.mechanic.jpa.entity.PartInvoice;
import ca.hccis.mechanic.bo.PartInvoiceBO; // **ASSUMED BO PACKAGE** (e.g., ca.hccis.mechanic.bo)
import ca.hccis.mechanic.entity.ReportPartInvoice; // **FIXED: matches ReportPartInvoice.java package**
import ca.hccis.mechanic.jpa.entity.PartInvoice; // **FIXED: matches PartInvoice.java package**
import ca.hccis.mechanic.util.CisUtility;

import java.util.ArrayList;

/**
 * Entity class to hold the attributes of the bus pass related reports.
 * @author bjmaclean
 * @since 20241010
 */

public class ReportPartInvoice {

    // --- Report 2: Transaction Summary (Date Range) ---
    private String dateStart;
    private String dateEnd;

    // --- Report 1: Part Sales Value (Part Number) ---
    private String partNumber; // Renamed from minLength

    // List to hold the results of the report (e.g., all matching PartInvoices or aggregated data)
    private ArrayList<PartInvoice> partInvoices;

    // --- Getters and Setters ---

    /**
     * Gets the input part number for the Part Sales Value Report.
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the input part number for the Part Sales Value Report.
     */
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public ArrayList<PartInvoice> getPartInvoices() {
        return partInvoices;
    }

    /**
     * Sets the list of PartInvoice results (or aggregated results) for the report view.
     */
    public void setPartInvoices(ArrayList<PartInvoice> partInvoices) {
        this.partInvoices = partInvoices;
    }
}


package cis.entity;

import cis.util.CisUtility;
import com.google.gson.Gson;

public class PartInvoice {

    private String partNumber; // e.g., 37280-5BA-C15 (max 15 chars)
    private double partPrice; // Price of the part
    private String custName;       // Customer name in "Firstname, Lastname"
    private String vehicle; // Vehicle in "Year,Make,Model"
    private int partQuantity;      // Number of parts purchased
    private String invoiceID;      // Unique invoice ID
    private double taxRate;        // e.g., 0.15 for 15%
    private double totalCost;      // Calculated total

    //Method to gather input from user
    public void getInformation(){
        partNumber = CisUtility.getInputString("Enter part number (max 15 chars): ");
        if (partNumber.length() > 15){
            partNumber = partNumber.substring(0, 15);
        }
        partPrice = CisUtility.getInputDouble("Enter part price: ");
        custName = CisUtility.getInputString("Enter customer name (FirstName, LastName): ");
        vehicle = CisUtility.getInputString("Enter vehicle (Year, Make, Model): ");
        partQuantity = CisUtility.getInputInt("Enter part quantity: ");
        invoiceID = CisUtility.getInputString("Enter invoice ID: ");
        taxRate = CisUtility.getInputDouble("Enter tax rate (e.g. 0.15 for 15%): ");

        // For now, we'll set a placeholder value
        // The actual calculation will be implemented later using TDD
        totalCost = 0.0;
    }
    public void calculateTotal(){
        // Placeholder implementation - will be replaced with actual logic
        // during unit testing phase
        totalCost = 0.0;
    }

    // Getters and Setters
    public String getPartNumber() {return partNumber;}
    public void  setPartNumber(String partNumber) {this.partNumber = partNumber;}

    public double getPartPrice() {return partPrice;}
    public void setPartPrice(double partPrice) {this.partPrice = partPrice;}

    public String getCustName() {return custName;}
    public void setCustName(String custName) {this.custName = custName;}

    public String getVehicle() {return vehicle;}
    public void setVehicle(String vehicle) {this.vehicle = vehicle;}

    public int getPartQuantity() {return partQuantity;}
    public void setPartQuantity(int partQuantity) {this.partQuantity = partQuantity;}

    public String getInvoiceID() {return invoiceID;}
    public void setInvoiceID(String invoiceID) {this.invoiceID = invoiceID;}

    public double getTaxRate() {return taxRate;}
    public void setTaxRate(double taxRate) {this.taxRate = taxRate;}

    public double getTotalCost() {return totalCost;}
    public void setTotalCost(double totalCost) {this.totalCost = totalCost;}

    // JSON Serialization using Gson
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // ToString method for displaying all data
    @Override
    public String toString(){
        double subtotal = partPrice * partQuantity;
        double taxAmount = subtotal * taxRate;
        return "Invoice ID: " + invoiceID +
                "\nCustomer: " + custName +
                "\nVehicle: " + vehicle +
                "\nPart Number: " + partNumber +
                "\nPart Price: $" + String.format("%.2f", partPrice) +
                "\nQuantity: " + partQuantity +
                "\nSubtotal: $" + String.format("%.2f", subtotal) +
                "\nTax (" + (taxRate * 100) + "%): $" + String.format("%.2f", taxAmount) +
                "\nTotal Cost: $" + String.format("%.2f", totalCost);
    }
}





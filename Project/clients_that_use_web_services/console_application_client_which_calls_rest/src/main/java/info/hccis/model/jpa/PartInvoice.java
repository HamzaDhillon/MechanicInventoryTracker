package info.hccis.model.jpa;

import java.util.Scanner;

public class PartInvoice {
    private String invoiceID;
    private String partNumber;
    private double partPrice;
    private String custName;
    private String vehicle;
    private int partQuantity;
    private double taxRate;
    private double totalCost;
    private String transactionDate;

    // Transient field
    private String invoiceCode;

    public PartInvoice() {
    }

    // Console input method like professor's getInformation()
    public void getInformation() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter invoice ID: ");
        invoiceID = input.nextLine();

        System.out.print("Enter part number: ");
        partNumber = input.nextLine();

        System.out.print("Enter customer name: ");
        custName = input.nextLine();

        System.out.print("Enter vehicle: ");
        vehicle = input.nextLine();

        System.out.print("Enter transaction date (YYYY-MM-DD): ");
        transactionDate = input.nextLine();

        System.out.print("Enter part quantity: ");
        partQuantity = Integer.parseInt(input.nextLine());

        System.out.print("Enter part price: ");
        partPrice = Double.parseDouble(input.nextLine());

        System.out.print("Enter tax rate: ");
        taxRate = Double.parseDouble(input.nextLine());

        // Calculate total cost automatically
        calculateTotalCost();
    }

    // Getters and Setters
    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public double getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public int getPartQuantity() {
        return partQuantity;
    }

    public void setPartQuantity(int partQuantity) {
        this.partQuantity = partQuantity;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    // Business logic method
    public void calculateTotalCost() {
        double subtotal = partPrice * partQuantity;
        double taxAmount = subtotal * taxRate;
        this.totalCost = subtotal + taxAmount;
    }

    // toString method with nice formatting like professor's
    @Override
    public String toString() {
        return "PartInvoice\n" +
                "    invoiceID        = '" + invoiceID + "',\n" +
                "    partNumber       = '" + partNumber + "',\n" +
                "    custName         = '" + custName + "',\n" +
                "    vehicle          = '" + vehicle + "',\n" +
                "    transactionDate  = '" + transactionDate + "',\n" +
                "    partQuantity     = " + partQuantity + ",\n" +
                "    partPrice        = " + partPrice + ",\n" +
                "    taxRate          = " + taxRate + ",\n" +
                "    totalCost        = " + totalCost + "\n";
    }

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartInvoice)) return false;
        PartInvoice that = (PartInvoice) o;
        return invoiceID.equals(that.invoiceID);
    }

    @Override
    public int hashCode() {
        return invoiceID.hashCode();
    }
}

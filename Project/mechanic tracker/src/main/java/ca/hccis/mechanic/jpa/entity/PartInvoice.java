package ca.hccis.mechanic.jpa.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "PartInvoice")
public class PartInvoice {

    // Transient field - follow Java naming conventions (camelCase)
    @Transient
    private String invoiceCode; // Changed from InvoiceCode to invoiceCode

    @Id
    @Size(max = 50)
    @NotNull
    @Column(name = "invoiceID", nullable = false, length = 50)
    private String invoiceID;

    @Size(max = 15)
    @NotNull
    @Column(name = "partNumber", nullable = false, length = 15)
    private String partNumber;

    @NotNull
    @Column(name = "partPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal partPrice;

    @Size(max = 100)
    @NotNull
    @Column(name = "custName", nullable = false, length = 100)
    private String custName;

    @Size(max = 100)
    @NotNull
    @Column(name = "vehicle", nullable = false, length = 100)
    private String vehicle;

    @NotNull
    @Column(name = "partQuantity", nullable = false)
    private Integer partQuantity;

    @NotNull
    @Column(name = "taxRate", nullable = false, precision = 5, scale = 3)
    private BigDecimal taxRate;

    @NotNull
    @Column(name = "totalCost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Size(max = 10)
    @NotNull
    @Column(name = "transactionDate", nullable = false, length = 10)
    private String transactionDate;

    // --- Getters and Setters ---
    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

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

    public BigDecimal getPartPrice() {
        return partPrice;
    }

    public void setPartPrice(BigDecimal partPrice) {
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

    public Integer getPartQuantity() {
        return partQuantity;
    }

    public void setPartQuantity(Integer partQuantity) {
        this.partQuantity = partQuantity;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    // --- Important JPA Methods ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartInvoice)) return false;
        PartInvoice that = (PartInvoice) o;
        return Objects.equals(getInvoiceID(), that.getInvoiceID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceID());
    }

    @Override
    public String toString() {
        return "PartInvoice{" +
                "invoiceID='" + invoiceID + '\'' +
                ", partNumber='" + partNumber + '\'' +
                ", custName='" + custName + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", partQuantity=" + partQuantity +
                ", partPrice=" + partPrice +
                ", taxRate=" + taxRate +
                ", totalCost=" + totalCost +
                '}';
    }
}
package org.example.soapmechanic;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for partInvoice complex type.
 * * <p>The following schema fragment specifies the expected content contained within this class.
 * * <pre>
 * &lt;complexType name="partInvoice">
 * &lt;complexContent>
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 * &lt;sequence>
 * &lt;element name="custName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 * &lt;element name="invoiceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 * &lt;element name="invoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 * &lt;element name="partNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 * &lt;element name="partPrice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 * &lt;element name="partQuantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 * &lt;element name="taxRate" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 * &lt;element name="totalCost" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 * &lt;element name="transactionDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 * &lt;element name="vehicle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 * &lt;/sequence>
 * &lt;/restriction>
 * &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * * */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "partInvoice", propOrder = {
        "custName",
        "invoiceCode",
        "invoiceID",
        "partNumber",
        "partPrice",
        "partQuantity",
        "taxRate",
        "totalCost",
        "transactionDate",
        "vehicle"
})
public class PartInvoice {

    protected String custName;
    protected String invoiceCode;
    protected String invoiceID;
    protected String partNumber;
    protected BigDecimal partPrice;
    protected Integer partQuantity;
    protected BigDecimal taxRate;
    protected BigDecimal totalCost;
    protected String transactionDate;
    protected String vehicle;

    /**
     * Gets the value of the custName property.
     * * @return
     * possible object is
     * {@link String }
     * */
    public String getCustName() {
        return custName;
    }

    /**
     * Sets the value of the custName property.
     * * @param value
     * allowed object is
     * {@link String }
     * */
    public void setCustName(String value) {
        this.custName = value;
    }

    /**
     * Gets the value of the invoiceCode property.
     * * @return
     * possible object is
     * {@link String }
     * */
    public String getInvoiceCode() {
        return invoiceCode;
    }

    /**
     * Sets the value of the invoiceCode property.
     * * @param value
     * allowed object is
     * {@link String }
     * */
    public void setInvoiceCode(String value) {
        this.invoiceCode = value;
    }

    /**
     * Gets the value of the invoiceID property.
     * * @return
     * possible object is
     * {@link String }
     * */
    public String getInvoiceID() {
        return invoiceID;
    }

    /**
     * Sets the value of the invoiceID property.
     * * @param value
     * allowed object is
     * {@link String }
     * */
    public void setInvoiceID(String value) {
        this.invoiceID = value;
    }

    /**
     * Gets the value of the partNumber property.
     * * @return
     * possible object is
     * {@link String }
     * */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the value of the partNumber property.
     * * @param value
     * allowed object is
     * {@link String }
     * */
    public void setPartNumber(String value) {
        this.partNumber = value;
    }

    /**
     * Gets the value of the partPrice property.
     * * @return
     * possible object is
     * {@link BigDecimal }
     * */
    public BigDecimal getPartPrice() {
        return partPrice;
    }

    /**
     * Sets the value of the partPrice property.
     * * @param value
     * allowed object is
     * {@link BigDecimal }
     * */
    public void setPartPrice(BigDecimal value) {
        this.partPrice = value;
    }

    /**
     * Gets the value of the partQuantity property.
     * * @return
     * possible object is
     * {@link Integer }
     * */
    public Integer getPartQuantity() {
        return partQuantity;
    }

    /**
     * Sets the value of the partQuantity property.
     * * @param value
     * allowed object is
     * {@link Integer }
     * */
    public void setPartQuantity(Integer value) {
        this.partQuantity = value;
    }

    /**
     * Gets the value of the taxRate property.
     * * @return
     * possible object is
     * {@link BigDecimal }
     * */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the value of the taxRate property.
     * * @param value
     * allowed object is
     * {@link BigDecimal }
     * */
    public void setTaxRate(BigDecimal value) {
        this.taxRate = value;
    }

    /**
     * Gets the value of the totalCost property.
     * * @return
     * possible object is
     * {@link BigDecimal }
     * */
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the value of the totalCost property.
     * * @param value
     * allowed object is
     * {@link BigDecimal }
     * */
    public void setTotalCost(BigDecimal value) {
        this.totalCost = value;
    }

    /**
     * Gets the value of the transactionDate property.
     * * @return
     * possible object is
     * {@link String }
     * */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     * * @param value
     * allowed object is
     * {@link String }
     * */
    public void setTransactionDate(String value) {
        this.transactionDate = value;
    }

    /**
     * Gets the value of the vehicle property.
     * * @return
     * possible object is
     * {@link String }
     * */
    public String getVehicle() {
        return vehicle;
    }

    /**
     * Sets the value of the vehicle property.
     * * @param value
     * allowed object is
     * {@link String }
     * */
    public void setVehicle(String value) {
        this.vehicle = value;
    }

    // ===============================================================
    //                  OVERRIDDEN toString() METHOD
    // ===============================================================
    @Override
    public String toString() {
        // You can adjust the formatting here to your preference
        return "\n--- Part Invoice (" + invoiceID + ") ---\n" +
                "Customer: " + custName + "\n" +
                "Vehicle: " + vehicle + "\n" +
                "Date: " + transactionDate + "\n" +
                "Part: " + partNumber + " (Code: " + invoiceCode + ")\n" +
                "Price: $" + partPrice + " x " + partQuantity + "\n" +
                "Tax Rate: " + taxRate.multiply(new BigDecimal(100)) + "%\n" +
                "Total Cost: $" + totalCost + "\n" +
                "----------------------------";
    }
}
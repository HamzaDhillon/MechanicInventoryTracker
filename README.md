# Mechanic Inventory Management System
---
## Project Description

The **Mechanic Inventory Management System/ Mechanic Tracker ** is designed to help small and medium-sized automotive repair shops manage their parts inventory more efficiently. Currently, many independent mechanics rely on handwritten notes, spreadsheets, or manual lookups to track which parts are in stock, which customers they belong to, and the vehicles they are associated with. This process can be time-consuming, error-prone, and difficult to maintain as the number of customers and parts grows.  

This system provides a simple yet powerful tool that allows mechanics to store essential information about automotive parts and generate cost reports quickly. Each part will be linked to key details such as its part number, price, quantity, the customer requesting it, and the vehicle it is meant for. By digitizing this process, mechanics can save time, reduce mistakes, and maintain accurate records of parts used for each customer.  

One of the system’s key features will be its **calculation functionality**. When parts are added to a customer’s record, the program will automatically calculate the extended price (`partPrice × partQuantity`) and generate a subtotal. Taxes will then be applied to provide a final bill. Additionally, the system will enable shops to view the **total value of all inventory** on hand, giving a clearer picture of overall stock value. Reports can also be generated to check how many units of a specific part number are available, which will help mechanics restock promptly.  

Ultimately, this system will streamline both **customer billing** and **inventory management**, allowing mechanics to spend less time on paperwork and more time on repairs.  

---

## Color Scheme

**Theme:** Midnight Garage

### Color Palette

-- **Main Color:** #000000 (Black) - Primary background  
-- **Secondary Color:** #9c9a9a (Grey) - Navigation and secondary elements  
-- **Accent Color:** #fcba03 (Yellow) - Buttons, interactive elements, highlights
---
## Fields

| Field Name   | Data Type | Description                                                                 |
|--------------|-----------|-----------------------------------------------------------------------------|
| partNumber   | String    | Unique alpha-numeric ID (e.g., 37280-5BA-C15, max length 15).               |
| partPrice    | Double    | Price of the part (two decimal points, e.g., 20.12).                        |
| custName     | String    | Customer’s name in “Firstname, Lastname” format.                           |
| vehicle      | String    | Vehicle in “Year, Make, Model” format.                                     |
| partQuantity | Int       | Number of parts available or purchased.                                    |
| invoiceID    | String    | Unique ID to associate transactions with customers.                        |
| taxRate      | Double    | Applied tax percentage (e.g., 0.15 for 15%).                               |
| totalCost    | Double    | Calculated: `(partPrice × partQuantity) + tax`.                            |

---

## Calculations

### Customer Total
- `partPrice × partQuantity = Subtotal`  
- `Subtotal × (1 + taxRate) = Total with Tax`  
- Breakdown includes subtotal, tax amount, and final total.  

### Inventory Value
- Formula: Sum of `(partPrice × partQuantity)` for all parts in the database.  

---
## Report Details
1. Provide the current sales value for a given part number. The user enters a Part Number and the report will provide the Total Quantity Sold and the Total Pre-Tax Revenue for that part across all transactions.

2. Provide a summary of all transactions within a date range. The user can enter a Start Date and an End Date and the report will provide a summary list of all invoices (including Invoice ID, Customer Name, and Total Cost) created within that range.


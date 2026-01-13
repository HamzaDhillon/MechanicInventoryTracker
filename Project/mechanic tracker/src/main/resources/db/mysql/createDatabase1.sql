DROP DATABASE IF EXISTS cis2232_mechanic_tracker;
CREATE DATABASE cis2232_mechanic_tracker;
USE cis2232_mechanic_tracker;

CREATE TABLE PartInvoice (
    -- Primary Key: Correctly set to invoiceID as you requested (one item per invoice)
                             invoiceID VARCHAR(50) PRIMARY KEY COMMENT 'Unique ID for the transaction, used as primary key.',

                             partNumber VARCHAR(15) NOT NULL COMMENT 'e.g., 37280-5BA-C15',
                             partPrice DECIMAL(10,2) NOT NULL,
                             custName VARCHAR(100) NOT NULL,
                             vehicle VARCHAR(100) NOT NULL,
                             partQuantity INT NOT NULL,
                             taxRate DECIMAL(5,3) NOT NULL,
                             totalCost DECIMAL(10,2) NOT NULL,

    -- *** FIX: ADDED TRANSACTION DATE (REQUIRED FOR REPORT 2) ***
                             transactionDate DATE NOT NULL COMMENT 'The date the invoice was created for date range report.'

) COMMENT 'This table holds part invoice transaction data (one part entry per invoice)';

-- FIX: Added transactionDate to the column list and provided placeholder dates for each row.
INSERT INTO PartInvoice (partNumber, partPrice, custName, vehicle, partQuantity, invoiceID, taxRate, totalCost, transactionDate) VALUES
                                                                                                                                     ('37280-5BA-C15', 45.99, 'John, Smith', '2020,Honda,Civic', 2, 'INV-026', 0.15, 105.78, '2025-10-01'),
                                                                                                                                     ('37280-5BA-C15', 45.99, 'Tariq, Hamza', '2020,Honda,Civic', 2, 'INV-001', 0.15, 105.78, '2025-10-01'),
                                                                                                                                     ('87123-XYZ-789', 129.50, 'Sarah, Johnson', '2022,Ford,Escape', 1, 'INV-002', 0.15, 148.93, '2025-10-01'),
                                                                                                                                     ('55512-ABC-123', 23.75, 'Mike, Davis', '2019,Nissan,Altima', 4, 'INV-003', 0.15, 109.25, '2025-10-02'),
                                                                                                                                     ('88901-DEF-456', 87.30, 'Lisa, Brown', '2021,Toyota,Camry', 1, 'INV-004', 0.15, 100.40, '2025-10-02'),
                                                                                                                                     ('37280-5BA-C15', 45.99, 'Tom, Wilson', '2018,Chevrolet,Malibu', 3, 'INV-005', 0.15, 158.66, '2025-10-03'),
                                                                                                                                     ('66789-GHI-012', 215.00, 'Emily, Chen', '2023,Hyundai,Elantra', 2, 'INV-006', 0.15, 494.50, '2025-10-03'),
                                                                                                                                     ('33456-JKL-345', 12.50, 'David, Miller', '2017,Ford,Fusion', 5, 'INV-007', 0.15, 71.88, '2025-10-04'),
                                                                                                                                     ('77890-MNO-678', 68.75, 'Jessica, Taylor', '2020,Subaru,Outback', 1, 'INV-008', 0.15, 79.06, '2025-10-04'),
                                                                                                                                     ('44567-PQR-901', 155.25, 'Robert, Anderson', '2022,Volkswagen,Jetta', 2, 'INV-009', 0.15, 357.08, '2025-10-05'),
                                                                                                                                     ('11223-STU-234', 89.99, 'Amanda, White', '2019,Mazda,CX-5', 1, 'INV-010', 0.15, 103.49, '2025-10-05'),
                                                                                                                                     ('55678-VWX-567', 32.45, 'Christopher, Lee', '2021,Kia,Sportage', 4, 'INV-011', 0.15, 149.27, '2025-10-06'),
                                                                                                                                     ('99012-YZA-890', 178.80, 'Jennifer, Harris', '2018,Chevrolet,Equinox', 2, 'INV-012', 0.15, 411.24, '2025-10-06'),
                                                                                                                                     ('22345-BCD-123', 45.00, 'Daniel, Martin', '2023,Honda,CR-V', 3, 'INV-013', 0.15, 155.25, '2025-10-07'),
                                                                                                                                     ('66789-EFG-456', 125.75, 'Michelle, Clark', '2020,Toyota,RAV4', 1, 'INV-014', 0.15, 144.61, '2025-10-07'),
                                                                                                                                     ('33456-HIJ-789', 67.30, 'Kevin, Lewis', '2017,Nissan,Rogue', 2, 'INV-015', 0.15, 154.79, '2025-10-08'),
                                                                                                                                     ('88901-KLM-012', 99.95, 'Stephanie, Walker', '2022,Ford,Bronco', 1, 'INV-016', 0.15, 114.94, '2025-10-08'),
                                                                                                                                     ('44567-NOP-345', 28.60, 'Jason, Hall', '2019,Hyundai,Tucson', 6, 'INV-017', 0.15, 197.34, '2025-10-09'),
                                                                                                                                     ('11223-QRS-678', 210.45, 'Melissa, Young', '2021,Chevrolet,Traverse', 2, 'INV-018', 0.15, 484.04, '2025-10-09'),
                                                                                                                                     ('77890-TUV-901', 14.25, 'Brian, King', '2018,Honda,Accord', 8, 'INV-019', 0.15, 131.10, '2025-10-10'),
                                                                                                                                     ('55678-WXY-234', 88.90, 'Nicole, Scott', '2023,Toyota,Highlander', 1, 'INV-020', 0.15, 102.24, '2025-10-10'),
                                                                                                                                     ('99012-ZAB-567', 165.00, 'Eric, Green', '2020,Subaru,Forester', 3, 'INV-021', 0.15, 569.25, '2025-10-11'),
                                                                                                                                     ('22345-CDE-890', 42.75, 'Rachel, Adams', '2017,Ford,Edge', 2, 'INV-022', 0.15, 98.33, '2025-10-11'),
                                                                                                                                     ('66789-FGH-123', 199.99, 'Mark, Nelson', '2022,Jeep,Grand Cherokee', 1, 'INV-023', 0.15, 229.99, '2025-10-12'),
                                                                                                                                     ('33456-IJK-456', 33.25, 'Laura, Carter', '2019,Hyundai,Sonata', 5, 'INV-024', 0.15, 191.19, '2025-10-12'),
                                                                                                                                     ('88901-LMN-789', 76.80, 'Steven, Roberts', '2021,Kia,Sorento', 2, 'INV-025', 0.15, 176.64, '2025-10-13');

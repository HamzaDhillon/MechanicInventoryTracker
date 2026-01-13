package cis.service;

import cis.entity.PartInvoice;
import cis.util.CisUtility;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PartInvoiceService {
    public static final int EXIT = 0;
    public static final String MENU = "1) Add Part Invoice" + System.lineSeparator()
            + "2) Show All Invoices" + System.lineSeparator()
            + EXIT + ") Exit" + System.lineSeparator();
    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_EXIT = "Goodbye";
    public static final String MESSAGE_SUCCESS = "Success";
    public static final String PATH = "c:\\CIS2232\\";
    public static final String FILE_NAME = "data_tariq_hamza.json";

    public void ensureStorage(CisUtility cisUtility) {
        // Create directory if it doesn't exist
        File directory = new File(PATH);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                cisUtility.display("Error: Could not create directory " + PATH);
                return;
            }
            cisUtility.display("Created directory: " + PATH);
        }

        // Create new file if it doesn't already exist
        if (!Files.exists(Paths.get(PATH + FILE_NAME))) {
            File journalFile = new File(PATH + FILE_NAME);
        }
    }

    public void runLoop(CisUtility cisUtility) {
        // Welcome message
        showWelcomeMessage(cisUtility);

        int menuOption;

        do {
            menuOption = cisUtility.getInputInt(MENU);

            switch (menuOption) {
                case EXIT:
                    cisUtility.display(MESSAGE_EXIT);
                    break;
                case 1:
                    processAdd(cisUtility);
                    break;
                case 2:
                    processShow(cisUtility);
                    break;
                default:
                    cisUtility.display(MESSAGE_ERROR);
                    break;
            }
        } while (menuOption != EXIT);
    }

    private void showWelcomeMessage(CisUtility cisUtility) {
        String welcomeMessage =

                        "Welcome to the Mechanic Inventory System!\n\n" +
                        "This system allows you to:\n" +
                        "• Add new part invoices\n" +
                        "• View all existing invoices\n";

        cisUtility.display(welcomeMessage);
    }

    private void processAdd(CisUtility cisUtility) {
        cisUtility.display("Adding New Part Invoice...");

        try {
            // Collecting information from user
            PartInvoice invoice = new PartInvoice(cisUtility);
            invoice.getInformation();

            // Insert new entry into file
            String jsonValue = invoice.toJson();
            cisUtility.display("JSON created successfully!");

            // Open, write, and close immediately to avoid file locking
            try (FileWriter tempWriter = new FileWriter(PATH + FILE_NAME, true)) {
                tempWriter.write(jsonValue + System.lineSeparator());
                tempWriter.flush();
            }

            cisUtility.display(MESSAGE_SUCCESS + " - Invoice added successfully!");

        } catch (Exception e) {
            String errorMessage = "Error adding invoice: " + e.getMessage();
            cisUtility.display(errorMessage);
            // Don't re-throw as RuntimeException - handle gracefully
        }
    }

    private void processShow(CisUtility cisUtility) {
        StringBuilder output = new StringBuilder();
        Gson gson = new Gson();

        try {
            // Check if file exists first
            if (!Files.exists(Paths.get(PATH + FILE_NAME))) {
                cisUtility.display("No invoice file found. Please add some invoices first.");
                return;
            }

            List<String> lines = Files.readAllLines(Paths.get(PATH + FILE_NAME));
            if (lines.isEmpty()) {
                output.append("No invoices found in the system.");
            } else {
                output.append("INVOICE REPORT\n");
                output.append("==============\n\n");
                int invoiceCount = 0;

                for (String current : lines) {
                    if (current.trim().isEmpty()) continue; // Skip empty lines

                    try {
                        PartInvoice invoice = gson.fromJson(current, PartInvoice.class);
                        output.append("Invoice #").append(++invoiceCount).append(":\n");
                        output.append(invoice.toString()).append("\n");
                        output.append("----------------------------------------\n\n");
                    } catch (Exception e) {
                        output.append("Error parsing invoice data: ").append(e.getMessage()).append("\n\n");
                    }
                }

                if (invoiceCount == 0) {
                    output.append("No valid invoices found in the file.");
                } else {
                    output.append("Total invoices: ").append(invoiceCount);
                }
            }
            cisUtility.display(output.toString());

        } catch (IOException e) {
            String errorMessage = "Error reading invoice file: " + e.getMessage();
            cisUtility.display(errorMessage);
            // Don't re-throw as RuntimeException - handle gracefully
        } catch (Exception e) {
            String errorMessage = "Unexpected error while displaying invoices: " + e.getMessage();
            cisUtility.display(errorMessage);
        }
    }
}
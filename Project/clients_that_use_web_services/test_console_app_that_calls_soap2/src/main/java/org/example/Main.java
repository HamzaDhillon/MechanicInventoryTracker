package org.example;

import org.example.soapmechanic.PartInvoice;
import org.example.soapmechanic.PartInvoiceService;
import org.example.soapmechanic.PartInvoiceServiceImplService;
import java.util.Scanner; // <-- NEW IMPORT

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello, I'm calling the SOAP service!");

        // --- 1. SET UP CONSOLE INPUT ---
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the Invoice ID to search for: ");
        String searchId = scanner.nextLine(); // Reads the user's input

        // 2. Instantiate the Service Client
        PartInvoiceServiceImplService partInvoiceServiceImplService = new PartInvoiceServiceImplService();
        PartInvoiceService partInvoiceService = partInvoiceServiceImplService.getPartInvoiceServiceImplPort();

        // -----------------------------------------------------

        try {
            // 3. Call the service method using the ID provided by the user
            PartInvoice foundInvoice = partInvoiceService.getInvoiceById(searchId);

            // 4. Check the result and print
            if (foundInvoice != null) {
                System.out.println("\n--- Successfully Retrieved Invoice ---");
                System.out.println(foundInvoice);
                System.out.println("------------------------------------");
            } else {
                System.out.println("\nInvoice with ID '" + searchId + "' not found.");
            }

        } catch (Exception e) {
            System.err.println("Error calling getInvoiceById service: " + e.getMessage());
        } finally {
            scanner.close(); // Important to close the scanner when finished
        }
    }
}
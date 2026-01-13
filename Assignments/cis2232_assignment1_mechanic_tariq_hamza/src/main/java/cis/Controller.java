package cis;

import cis.entity.PartInvoice;
import cis.util.CisUtility;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controls the overall flow of the program.
 *
 * @author cis1232 BJM
 * @since 2025-01
 */
public class Controller {

    public static final int EXIT = 0;

      public static final String MENU = "1) Add Part Invoice" + System.lineSeparator()
            + "2) Show All Invoices" + System.lineSeparator()
            + EXIT + ") Exit" + System.lineSeparator();

    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_EXIT = "Goodbye";
    public static final String MESSAGE_SUCCESS = "Success";
    public static final String PATH = "c:\\CIS2232\\";
    public static final String FILE_NAME = "data_tariq_hamza.json";
    private static Path journalPath = null;
    private static FileWriter journalWriter = null;

    public static void main(String[] args) {
        // Create directory if it doesn't exist
        File directory = new File(PATH);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                System.out.println("Error: Could not create directory " + PATH);
                return;
            }
            System.out.println("Created directory: " + PATH);
        }

        // Create new file if it doesn't already exist
        journalPath = Paths.get(PATH + FILE_NAME);
        if (!Files.exists(journalPath)) {
            File journalFile = new File(journalPath.toString());
        }
        try {
            journalWriter = new FileWriter(PATH + FILE_NAME, true);
        } catch (IOException e) {
            System.out.println("Error creating file writer");
            throw new RuntimeException(e);
        }

        int menuOption;

        do {
            menuOption = CisUtility.getInputInt(MENU);

            switch (menuOption) {
                case EXIT:
                    System.out.println(MESSAGE_EXIT);
                    break; //Break out of the loop as we're finished.
                case 1:
                    processAdd();
                    break;
                case 2:
                    processShow();
                    break;
                default:
                    System.out.println(MESSAGE_ERROR);
                    break;
            }
        } while (menuOption != EXIT);

        try {
            journalWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add journal entry from user and save to the file
     *
     * @author BJM
     * @since 20201118
     */
    public static void processAdd() {
        System.out.println("Processing option 1");

        // Collecting information from user
        PartInvoice invoice = new PartInvoice();
        invoice.getInformation();


        // Insert new entry into journal.txt and catch possible errors
        try {
            String jsonValue = invoice.toJson();
            System.out.println(jsonValue);
            journalWriter.write(jsonValue + System.lineSeparator());
            journalWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Processing for menu option 2.
     *
     * @author BJM
     * @since 20201118
     */
    public static void processShow() {
        System.out.println();
        Gson gson = new Gson();
        try {
            List<String> lines = Files.readAllLines(Paths.get(PATH + FILE_NAME));
            if (lines.isEmpty()) {
                System.out.println("No assessments found");
            } else {
                System.out.println("Here are the assessments found");
                for (String current : lines) {
                    PartInvoice invoice = gson.fromJson(current,PartInvoice.class);
                    System.out.println(invoice.toString());
                    System.out.println();
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file");
            throw new RuntimeException(e);
        }
    }
}



package info.hccis.main;

import info.hccis.model.jpa.PartInvoice;
import info.hccis.student.util.UtilityRest;
import org.json.JSONArray;

import java.util.Scanner;

public class Controller {

    final public static String MENU = "\nMain Menu \nA) Add\n"
            + "U) Update (FUTURE)\n"
            + "V) View\n"
            + "D) Delete\n"
            + "X) eXit";
    final static Scanner input = new Scanner(System.in);
    private static final String URL_STRING = "http://localhost:8080/api/PartInvoiceService/v1/invoices";

    public static void main(String[] args) {
        boolean endProgram = false;
        do {
            System.out.println(MENU);
            String choice = input.nextLine();
            PartInvoice partInvoice;
            String url;
            switch (choice.toUpperCase()) {
                case "A":
                    partInvoice = create();
                    url = URL_STRING;
                    System.out.println("Url=" + url);
                    PartInvoice partInvoiceTemp = (PartInvoice) UtilityRest.addUsingRest(url, partInvoice);
                    if(partInvoiceTemp != null) {
                        System.out.println("Added new invoice:" + partInvoiceTemp.toString());
                    }
                    break;
//                case "U":
//                    partInvoice = create();
//                    url = URL_STRING;
//                    System.out.println("Url="+url);
//                    UtilityRest.addUsingRest(url, partInvoice);
                case "D":
                    System.out.println("Enter invoice ID to delete");
                    String invoiceId = input.nextLine();
                    UtilityRest.deleteUsingRest(URL_STRING, invoiceId);
                    break;
                case "V":
                    String jsonReturned = UtilityRest.getJsonFromRest(URL_STRING);
                    //**************************************************************
                    //Based on the json string passed back, loop through each json
                    //object which is a json string in an array of json strings.
                    //*************************************************************
                    JSONArray jsonArray = new JSONArray(jsonReturned);
                    //**************************************************************
                    //For each json object in the array, show the invoice details
                    //**************************************************************
                    System.out.println("Here are the invoices:");
                    for (int currentIndex = 0; currentIndex < jsonArray.length(); currentIndex++) {
                        // Convert JSON to PartInvoice object
                        String invoiceJson = jsonArray.getJSONObject(currentIndex).toString();
                        PartInvoice current = new com.google.gson.Gson().fromJson(invoiceJson, PartInvoice.class);
                        System.out.println(current.toString());
                    }
                    break;

                case "X":
                    endProgram = true;
                    break;
                default:
                    System.out.println("INVALID OPTION");
            }
        } while (!endProgram);
    }

    /**
     * Create an invoice by asking user for input.
     *
     * @return PartInvoice object
     */
    public static PartInvoice create() {
        PartInvoice partInvoice = new PartInvoice();
        partInvoice.getInformation();
        return partInvoice;
    }
}
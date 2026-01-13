package ca.hccis.mechanic.rest;

import ca.hccis.mechanic.jpa.entity.PartInvoice;
import ca.hccis.mechanic.repositories.PartInvoiceRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Service class for accessing using REST.
 *
 * @author BJ Mclean
 * @since 202412
 */
@Path("/PartInvoiceService/v1/invoices")
public class PartInvoiceService
{
    private final PartInvoiceRepository _partInvoiceRepository;

    @Autowired
    public PartInvoiceService(PartInvoiceRepository partInvoiceRepository){
        this._partInvoiceRepository = partInvoiceRepository;
    }

    /**
     * Method to get all.
     *
     * @author BJ Mclean
     * @since 202412
     * @return part invoices
     */
    @GET
    @Produces("application/json")
    public Response getAll()
    {
        ArrayList<PartInvoice> invoices = (ArrayList<PartInvoice>) _partInvoiceRepository.findAll();

        if (invoices == null || invoices.isEmpty()) {
            int responseCode = HttpURLConnection.HTTP_NO_CONTENT;
            return Response.status(responseCode).build();
        } else {
            return Response
                    .status(HttpURLConnection.HTTP_OK)
                    .entity(invoices).build();
        }
    }

    /**
     * Method to get resource by their id using REST.
     *
     * @author BJ Mclean
     * @since 202412
     * @return response
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getInvoiceById(@PathParam("id") String id) throws URISyntaxException
    {
        Optional<PartInvoice> theInvoice = _partInvoiceRepository.findById(id);
        if (!theInvoice.isPresent()) {
            return Response.status(HttpURLConnection.HTTP_NO_CONTENT).build();
        } else {
            return Response
                    .status(HttpURLConnection.HTTP_OK)
                    .entity(theInvoice).build();
        }
    }

    /**
     * Method to create using REST.
     *
     * @author BJ Mclean
     * @since 202412
     * @return response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String inJson)
    {
        try{
            System.out.println(inJson);
            String temp = save(inJson);
            return Response.status(HttpURLConnection.HTTP_OK).entity(temp).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
        }catch(Exception e){
            System.out.println("Exception happened adding part invoice.");
            //https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#successful_responses

            return Response.status(HttpURLConnection.HTTP_NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
    }

    /**
     * Method to update a part invoice using REST.
     *
     * @author BJ Mclean
     * @since 202412
     * @return response
     */
    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateInvoice(@javax.ws.rs.PathParam("id") String id, String invoiceJson) throws URISyntaxException
    {

        try{
            String temp = save(invoiceJson);
            return Response.status(201).entity(temp).header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
        }catch(Exception e){
            return Response.status(400).entity(e.getMessage()).build();
        }

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id){

        try {
            Optional<PartInvoice> theInvoiceOptionalObject = _partInvoiceRepository.findById(id);
            if (!theInvoiceOptionalObject.isPresent()) {
                return Response.status(HttpURLConnection.HTTP_NO_CONTENT).build();
            }else{
                _partInvoiceRepository.delete(theInvoiceOptionalObject.get());
            }
        }catch (Exception e) {
            return Response.status(HttpURLConnection.HTTP_NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }
        return Response.status(HttpURLConnection.HTTP_OK).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
    }

    /**
     * Method to make sure all required inputs are present.
     *
     * @author BJ Mclean
     * @since 202412
     * @return string
     */
    public String save(String json) throws Exception {

        Gson gson = new Gson();
        PartInvoice theObject = gson.fromJson(json, PartInvoice.class);

        // Generate invoice ID if not provided
        if(theObject.getInvoiceID() == null || theObject.getInvoiceID().isEmpty()){
            theObject.setInvoiceID(generateNextInvoiceId());
        }

        // Calculate total cost before saving
        calculateTotalCost(theObject);

        theObject = _partInvoiceRepository.save(theObject);

        String temp = "";
        temp = gson.toJson(theObject);

        return temp;

    }

    /**
     * Generate next invoice ID
     */
    private String generateNextInvoiceId() {
        try {
            ArrayList<PartInvoice> allInvoices = (ArrayList<PartInvoice>) _partInvoiceRepository.findAll();
            if (allInvoices.isEmpty()) {
                return "INV-001";
            }

            String highestId = allInvoices.stream()
                    .map(PartInvoice::getInvoiceID)
                    .filter(id -> id != null && id.startsWith("INV-"))
                    .max(String::compareTo)
                    .orElse("INV-000");

            if (highestId.startsWith("INV-")) {
                try {
                    int lastNumber = Integer.parseInt(highestId.substring(4));
                    int nextNumber = lastNumber + 1;
                    return String.format("INV-%03d", nextNumber);
                } catch (NumberFormatException e) {
                    System.out.println("Could not parse numeric part from ID: " + highestId + ", starting from INV-001");
                }
            }
            return "INV-001";
        } catch (Exception e) {
            System.out.println("Error generating next invoice ID: " + e.getMessage());
            return "INV-001";
        }
    }

    /**
     * Calculate total cost
     */
    private void calculateTotalCost(PartInvoice partInvoice) {
        if (partInvoice.getPartPrice() != null &&
                partInvoice.getPartQuantity() != null &&
                partInvoice.getTaxRate() != null) {

            try {
                java.math.BigDecimal quantity = new java.math.BigDecimal(partInvoice.getPartQuantity());
                java.math.BigDecimal price = partInvoice.getPartPrice();
                java.math.BigDecimal taxRate = partInvoice.getTaxRate();

                java.math.BigDecimal subtotal = price.multiply(quantity);
                java.math.BigDecimal taxAmount = subtotal.multiply(taxRate)
                        .divide(new java.math.BigDecimal("100"), 2, java.math.BigDecimal.ROUND_HALF_UP);
                java.math.BigDecimal totalCost = subtotal.add(taxAmount).setScale(2, java.math.BigDecimal.ROUND_HALF_UP);

                partInvoice.setTotalCost(totalCost);
                System.out.println("Calculated total cost: " + totalCost);

            } catch (Exception e) {
                System.out.println("Error calculating total cost: " + e.getMessage());
                partInvoice.setTotalCost(java.math.BigDecimal.ZERO);
            }
        } else {
            partInvoice.setTotalCost(java.math.BigDecimal.ZERO);
        }
    }
}
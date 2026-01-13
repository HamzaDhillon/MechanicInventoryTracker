package ca.hccis.mechanic.service;

import ca.hccis.mechanic.entity.VehicleRecall;
import com.google.gson.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleRecallService {

    private static final String RECALLS_BY_VIN_URL = "https://api.nhtsa.gov/recalls/recallsByVIN";
    private static final String RECALLS_BY_VEHICLE_URL = "https://api.nhtsa.gov/recalls/recallsByVehicle";
    private static final String VIN_DECODER_URL = "https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues";

    /**
     * Get vehicle recalls by VIN number
     */
    public List<VehicleRecall> getRecallsByVIN(String vin) {
        try {
            // Clean VIN - remove spaces and convert to uppercase
            vin = vin.trim().toUpperCase().replaceAll("[^A-Z0-9]", "");

            if (vin.length() != 17) {
                throw new IllegalArgumentException("VIN must be 17 characters long");
            }

            URL url = new URL(RECALLS_BY_VIN_URL + "?vin=" + vin);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            System.out.println("NHTSA API Call: " + url.toString());

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode != 200) {
                throw new RuntimeException("NHTSA API Request Failed: HTTP error code : " + responseCode);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            System.out.println("NHTSA API Response received");
            return parseRecallResponseVIN(response.toString(), vin);

        } catch (Exception e) {
            System.err.println("Error in VehicleRecallService.getRecallsByVIN: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Get recalls by vehicle make, model, and year
     */
    public List<VehicleRecall> getRecallsByMakeModelYear(String make, String model, Integer year) {
        try {
            StringBuilder urlBuilder = new StringBuilder(RECALLS_BY_VEHICLE_URL);

            if (make != null && !make.trim().isEmpty()) {
                urlBuilder.append("?make=").append(java.net.URLEncoder.encode(make.trim(), "UTF-8"));
            }
            if (model != null && !model.trim().isEmpty()) {
                urlBuilder.append("&model=").append(java.net.URLEncoder.encode(model.trim(), "UTF-8"));
            }
            if (year != null) {
                urlBuilder.append("&modelYear=").append(year);
            }

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            System.out.println("NHTSA API Call: " + url.toString());

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode != 200) {
                throw new RuntimeException("NHTSA API Request Failed: HTTP error code : " + responseCode);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            return parseRecallResponseVehicle(response.toString());

        } catch (Exception e) {
            System.err.println("Error in VehicleRecallService.getRecallsByMakeModelYear: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Decode VIN to get vehicle details
     */
    public VehicleInfo decodeVIN(String vin) {
        try {
            vin = vin.trim().toUpperCase().replaceAll("[^A-Z0-9]", "");

            URL url = new URL(VIN_DECODER_URL + "/" + vin + "?format=json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            return parseVINResponse(response.toString());

        } catch (Exception e) {
            System.err.println("Error decoding VIN: " + e.getMessage());
            return null;
        }
    }

    /**
     * Parse VIN decoder response - FIXED for older Gson
     */
    private VehicleInfo parseVINResponse(String jsonResponse) {
        try {
            // FIX: Use JsonParser() instead of JsonParser.parseString()
            JsonParser parser = new JsonParser();
            JsonObject responseObj = parser.parse(jsonResponse).getAsJsonObject();
            JsonArray resultsArray = responseObj.getAsJsonArray("Results");

            if (resultsArray.size() > 0) {
                JsonObject vehicleObj = resultsArray.get(0).getAsJsonObject();
                VehicleInfo info = new VehicleInfo();

                info.setMake(getStringOrNull(vehicleObj, "Make"));
                info.setModel(getStringOrNull(vehicleObj, "Model"));
                info.setYear(getStringOrNull(vehicleObj, "ModelYear"));
                info.setManufacturer(getStringOrNull(vehicleObj, "Manufacturer"));
                info.setVehicleType(getStringOrNull(vehicleObj, "VehicleType"));

                return info;
            }
        } catch (Exception e) {
            System.err.println("Error parsing VIN response: " + e.getMessage());
        }
        return null;
    }

    /**
     * Parse recall response for VIN lookup - FIXED for older Gson
     */
    private List<VehicleRecall> parseRecallResponseVIN(String jsonResponse, String vin) {
        List<VehicleRecall> recalls = new ArrayList<>();

        try {
            // FIX: Use JsonParser() instead of JsonParser.parseString()
            JsonParser parser = new JsonParser();
            JsonObject responseObj = parser.parse(jsonResponse).getAsJsonObject();

            if (!responseObj.has("results") || responseObj.get("results").getAsJsonArray().size() == 0) {
                System.out.println("No recall results found for VIN: " + vin);
                return recalls;
            }

            JsonArray resultsArray = responseObj.getAsJsonArray("results");
            System.out.println("Found " + resultsArray.size() + " recall results for VIN: " + vin);

            for (JsonElement resultElement : resultsArray) {
                JsonObject resultObj = resultElement.getAsJsonObject();
                VehicleRecall recall = parseRecallObject(resultObj);
                recall.setVin(vin);
                recalls.add(recall);
            }

        } catch (Exception e) {
            System.err.println("Error parsing VIN recall response: " + e.getMessage());
            e.printStackTrace();
        }

        return recalls;
    }

    /**
     * Parse recall response for vehicle lookup - FIXED for older Gson
     */
    private List<VehicleRecall> parseRecallResponseVehicle(String jsonResponse) {
        List<VehicleRecall> recalls = new ArrayList<>();

        try {
            // FIX: Use JsonParser() instead of JsonParser.parseString()
            JsonParser parser = new JsonParser();
            JsonObject responseObj = parser.parse(jsonResponse).getAsJsonObject();

            if (!responseObj.has("results") || responseObj.get("results").getAsJsonArray().size() == 0) {
                System.out.println("No recall results found for vehicle search");
                return recalls;
            }

            JsonArray resultsArray = responseObj.getAsJsonArray("results");
            System.out.println("Found " + resultsArray.size() + " recall results for vehicle search");

            for (JsonElement resultElement : resultsArray) {
                JsonObject resultObj = resultElement.getAsJsonObject();
                VehicleRecall recall = parseRecallObject(resultObj);
                recalls.add(recall);
            }

        } catch (Exception e) {
            System.err.println("Error parsing vehicle recall response: " + e.getMessage());
            e.printStackTrace();
        }

        return recalls;
    }

    /**
     * Parse individual recall object
     */
    private VehicleRecall parseRecallObject(JsonObject recallObj) {
        VehicleRecall recall = new VehicleRecall();

        // Parse basic recall information
        recall.setManufacturer(getStringOrNull(recallObj, "Manufacturer"));
        recall.setNhtsaCampaignNumber(getStringOrNull(recallObj, "NHTSACampaignNumber"));
        recall.setReportReceivedDate(getStringOrNull(recallObj, "ReportReceivedDate"));
        recall.setComponent(getStringOrNull(recallObj, "Component"));
        recall.setSummary(getStringOrNull(recallObj, "Summary"));
        recall.setConsequence(getStringOrNull(recallObj, "Conequence")); // Note: API has typo "Conequence"
        recall.setRemedy(getStringOrNull(recallObj, "Remedy"));
        recall.setNotes(getStringOrNull(recallObj, "Notes"));

        // Parse vehicle information
        if (recallObj.has("ModelYear")) {
            String yearStr = getStringOrNull(recallObj, "ModelYear");
            if (yearStr != null && yearStr.matches("\\d+")) {
                recall.setModelYear(Integer.parseInt(yearStr));
            }
        }

        recall.setMake(getStringOrNull(recallObj, "Make"));
        recall.setModel(getStringOrNull(recallObj, "Model"));

        return recall;
    }

    /**
     * Helper method to safely get string values from JSON
     */
    private String getStringOrNull(JsonObject obj, String key) {
        if (obj.has(key)) {
            JsonElement element = obj.get(key);
            if (!element.isJsonNull() && element.getAsString() != null && !element.getAsString().isEmpty()) {
                return element.getAsString();
            }
        }
        return null;
    }

    /**
     * Validate VIN format
     */
    public boolean validateVIN(String vin) {
        if (vin == null) return false;
        String cleanVIN = vin.trim().toUpperCase().replaceAll("[^A-Z0-9]", "");
        return cleanVIN.length() == 17;
    }

    /**
     * Inner class for vehicle information
     */
    public static class VehicleInfo {
        private String make;
        private String model;
        private String year;
        private String manufacturer;
        private String vehicleType;

        // Getters and Setters
        public String getMake() { return make; }
        public void setMake(String make) { this.make = make; }

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }

        public String getYear() { return year; }
        public void setYear(String year) { this.year = year; }

        public String getManufacturer() { return manufacturer; }
        public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

        public String getVehicleType() { return vehicleType; }
        public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    }
}
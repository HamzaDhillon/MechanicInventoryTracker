package ca.hccis.mechanic.controllers;

import ca.hccis.mechanic.entity.VehicleRecall;
import ca.hccis.mechanic.service.VehicleRecallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VehicleRecallController {

    @Autowired
    private VehicleRecallService vehicleRecallService;

    @GetMapping("/recalls")
    public String showRecallsPage(Model model) {
        model.addAttribute("showResults", false);
        return "recalls/recalls";
    }

    @PostMapping("/recalls/vin")
    public String getRecallsByVIN(@RequestParam String vin, Model model) {
        // Validate VIN
        if (!vehicleRecallService.validateVIN(vin)) {
            model.addAttribute("error", "Invalid VIN format. VIN must be 17 characters long.");
            model.addAttribute("showResults", false);
            return "recalls/recalls";
        }

        long startTime = System.currentTimeMillis();

        // First decode VIN to get vehicle info
        VehicleRecallService.VehicleInfo vehicleInfo = vehicleRecallService.decodeVIN(vin);

        // Then get recalls
        List<VehicleRecall> recalls = vehicleRecallService.getRecallsByVIN(vin);
        long endTime = System.currentTimeMillis();

        model.addAttribute("recalls", recalls);
        model.addAttribute("vehicleInfo", vehicleInfo);
        model.addAttribute("searchType", "VIN");
        model.addAttribute("searchTerm", vin);
        model.addAttribute("totalRecalls", recalls.size());
        model.addAttribute("executionTime", endTime - startTime);
        model.addAttribute("showResults", true);

        return "recalls/recalls";
    }

    @PostMapping("/recalls/make-model")
    public String getRecallsByMakeModel(
            @RequestParam String make,
            @RequestParam String model,
            @RequestParam(required = false) Integer year,
            Model modelAttr) {

        long startTime = System.currentTimeMillis();
        List<VehicleRecall> recalls = vehicleRecallService.getRecallsByMakeModelYear(make, model, year);
        long endTime = System.currentTimeMillis();

        StringBuilder searchDescription = new StringBuilder();
        searchDescription.append(make);
        if (model != null && !model.isEmpty()) {
            searchDescription.append(" ").append(model);
        }
        if (year != null) {
            searchDescription.append(" ").append(year);
        }

        modelAttr.addAttribute("recalls", recalls);
        modelAttr.addAttribute("searchType", "Make/Model");
        modelAttr.addAttribute("searchTerm", searchDescription.toString());
        modelAttr.addAttribute("totalRecalls", recalls.size());
        modelAttr.addAttribute("executionTime", endTime - startTime);
        modelAttr.addAttribute("showResults", true);

        return "recalls/recalls";
    }
}
package ca.hccis.mechanic.controllers;

import ca.hccis.mechanic.bo.PartInvoiceBO;
import ca.hccis.mechanic.jpa.entity.PartInvoice;
import ca.hccis.mechanic.repositories.PartInvoiceRepository;
import ca.hccis.mechanic.bo.PartInvoiceBusinessValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/partinvoice")
public class PartInvoiceController {

    private final PartInvoiceRepository partInvoiceRepository;
    private final PartInvoiceBusinessValidator businessValidator;
    private static final Logger logger = LoggerFactory.getLogger(PartInvoiceController.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public PartInvoiceController(PartInvoiceRepository partInvoiceRepository,
                                 PartInvoiceBusinessValidator businessValidator) { // Update constructor
        this.partInvoiceRepository = partInvoiceRepository;
        this.businessValidator = businessValidator;
    }

    @RequestMapping("")
    public String home(Model model) {
        List<PartInvoice> invoices = partInvoiceRepository.findAll();
        model.addAttribute("invoices", invoices);
        model.addAttribute("invoice", new PartInvoice());
        return "partinvoice/list";

    }

    @RequestMapping("/add")
    public String add(Model model) {
        PartInvoice partInvoice = new PartInvoice();
        // **CORRECTION: REMOVE THE ID GENERATION FROM THE 'ADD' METHOD**
        // partInvoice.setInvoiceID(generateNextInvoiceId()); <-- This line is removed
        partInvoice.setTransactionDate(LocalDate.now().format(DATE_FORMATTER));
        model.addAttribute("partInvoice", partInvoice);
        return "partinvoice/add";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Optional<PartInvoice> partInvoice = partInvoiceRepository.findById(id);
        if (partInvoice.isPresent()) {
            // Recalculate total cost when loading for edit to ensure consistency
            PartInvoiceBO.calculateTotalCost(partInvoice.get()); // Using static method from BO
            model.addAttribute("partInvoice", partInvoice.get());
            return "partinvoice/add";
        }
        model.addAttribute("messageError", "Could not load the invoice");
        return "redirect:/partinvoice";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable String id, Model model) {
        try {
            partInvoiceRepository.deleteById(id);
            model.addAttribute("messageSuccess", "Invoice deleted successfully");
        } catch (Exception e) {
            model.addAttribute("messageError", "Error deleting invoice: " + e.getMessage());
        }
        return "redirect:/partinvoice";
    }

    /**
     * Handle form submission with JPA validation
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(Model model,
                         @Valid @ModelAttribute("partInvoice") PartInvoice partInvoice,
                         BindingResult bindingResult) {

        // If validation errors exist, return to the form
        if (bindingResult.hasErrors()) {
            logger.debug("Validation errors found:");
            bindingResult.getAllErrors().forEach(error ->
                    logger.debug("Error: {}", error.toString())
            );
            return "partinvoice/add";
        }
        // Applying business validation rules
        businessValidator.validate(partInvoice, bindingResult);
        if (bindingResult.hasErrors()) {
            logger.debug("Business validation errors found:");
            bindingResult.getAllErrors().forEach(error ->
                    logger.debug("Error: {}", error.toString())
            );
            return "partinvoice/add";
        }


        try {
            // Set transaction date to today if not provided or empty
            if (partInvoice.getTransactionDate() == null || partInvoice.getTransactionDate().isEmpty()) {
                partInvoice.setTransactionDate(LocalDate.now().format(DATE_FORMATTER));
            }

            // FOR NEW INVOICES: Generate ID
            if (partInvoice.getInvoiceID() == null || partInvoice.getInvoiceID().isEmpty()) {
                List<PartInvoice> allInvoices = partInvoiceRepository.findAll();
                String nextId = PartInvoiceBO.generateNextInvoiceId(allInvoices);
                partInvoice.setInvoiceID(nextId);
            }

            // Calculate total cost before saving using static method from BO
            PartInvoiceBO.calculateTotalCost(partInvoice);

            partInvoiceRepository.save(partInvoice);
            model.addAttribute("messageSuccess", "Invoice saved successfully");

        } catch (Exception e) {
            logger.error("Error saving invoice: {}", e.getMessage());
            model.addAttribute("messageError", "Error saving invoice: " + e.getMessage());
            return "partinvoice/add";
        }
        return "redirect:/partinvoice";
    }

    @RequestMapping("/search/customer")
    public String searchByCustomer(Model model, @ModelAttribute("invoice") PartInvoice invoice) {
        List<PartInvoice> invoices = partInvoiceRepository.findByCustNameContaining(invoice.getCustName());
        model.addAttribute("invoices", invoices);
        logger.debug("Searched for customer name: {}", invoice.getCustName());
        return "partinvoice/list";
    }

    @RequestMapping("/search/vehicle")
    public String searchByVehicle(Model model, @ModelAttribute("invoice") PartInvoice invoice) {
        List<PartInvoice> invoices = partInvoiceRepository.findByVehicleContaining(invoice.getVehicle());
        model.addAttribute("invoices", invoices);
        logger.debug("Searched for vehicle: {}", invoice.getVehicle());
        return "partinvoice/list";
    }

}
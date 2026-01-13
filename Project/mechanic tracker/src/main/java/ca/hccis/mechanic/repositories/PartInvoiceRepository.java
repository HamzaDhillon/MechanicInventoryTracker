package ca.hccis.mechanic.repositories;

import ca.hccis.mechanic.jpa.entity.PartInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartInvoiceRepository extends JpaRepository<PartInvoice, String> {
    /**
     * Use Spring Data JPA functionality to find invoices where customer name contains
     * the string passed in as a parameter.
     *
     * @param name The customer name to find
     * @return The list of invoices matching the criteria
     */
    List<PartInvoice> findByCustNameContaining(String name);

    /**
     * Use Spring Data JPA functionality to find invoices where vehicle description contains
     * the string passed in as a parameter.
     *
     * @param vehicle The vehicle description to find
     * @return The list of invoices matching the criteria
     */
    List<PartInvoice> findByVehicleContaining(String vehicle);

}
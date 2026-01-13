// PartInvoiceServiceImpl.java (updated)
package ca.hccis.mechanic.soap;

import ca.hccis.mechanic.dao.PartInvoiceDAO;
import ca.hccis.mechanic.jpa.entity.PartInvoice;
import java.sql.SQLException;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebService(endpointInterface = "ca.hccis.mechanic.soap.PartInvoiceService")
public class PartInvoiceServiceImpl implements PartInvoiceService {

    @Override
    public List<PartInvoice> getInvoices() {
        PartInvoiceDAO partInvoiceDAO = new PartInvoiceDAO();
        try {
            ArrayList<PartInvoice> invoices = partInvoiceDAO.selectAll();
            return invoices;
        } catch (SQLException e) {
            e.printStackTrace();
            // Return empty list or handle appropriately
            return new ArrayList<>();
        }
    }

    @Override
    public PartInvoice getInvoiceById(String id) {
        PartInvoiceDAO partInvoiceDAO = new PartInvoiceDAO();
        try {
            Optional<PartInvoice> invoice = partInvoiceDAO.selectById(id);

            if (invoice.isPresent()) {
                return invoice.get();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

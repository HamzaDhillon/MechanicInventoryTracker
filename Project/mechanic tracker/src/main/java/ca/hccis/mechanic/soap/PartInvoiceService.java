package ca.hccis.mechanic.soap;

import ca.hccis.mechanic.jpa.entity.PartInvoice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface PartInvoiceService {
    @WebMethod
    List<PartInvoice> getInvoices();

    @WebMethod
    PartInvoice getInvoiceById(String id);
}

package org.example.soapmechanic;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.example.soapmechanic package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetInvoicesResponse_QNAME = new QName("http://soap.mechanic.hccis.ca/", "getInvoicesResponse");
    private final static QName _GetInvoiceByIdResponse_QNAME = new QName("http://soap.mechanic.hccis.ca/", "getInvoiceByIdResponse");
    private final static QName _GetInvoices_QNAME = new QName("http://soap.mechanic.hccis.ca/", "getInvoices");
    private final static QName _GetInvoiceById_QNAME = new QName("http://soap.mechanic.hccis.ca/", "getInvoiceById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.example.soapmechanic
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetInvoices }
     * 
     */
    public GetInvoices createGetInvoices() {
        return new GetInvoices();
    }

    /**
     * Create an instance of {@link GetInvoiceById }
     * 
     */
    public GetInvoiceById createGetInvoiceById() {
        return new GetInvoiceById();
    }

    /**
     * Create an instance of {@link GetInvoicesResponse }
     * 
     */
    public GetInvoicesResponse createGetInvoicesResponse() {
        return new GetInvoicesResponse();
    }

    /**
     * Create an instance of {@link GetInvoiceByIdResponse }
     * 
     */
    public GetInvoiceByIdResponse createGetInvoiceByIdResponse() {
        return new GetInvoiceByIdResponse();
    }

    /**
     * Create an instance of {@link PartInvoice }
     * 
     */
    public PartInvoice createPartInvoice() {
        return new PartInvoice();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInvoicesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mechanic.hccis.ca/", name = "getInvoicesResponse")
    public JAXBElement<GetInvoicesResponse> createGetInvoicesResponse(GetInvoicesResponse value) {
        return new JAXBElement<GetInvoicesResponse>(_GetInvoicesResponse_QNAME, GetInvoicesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInvoiceByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mechanic.hccis.ca/", name = "getInvoiceByIdResponse")
    public JAXBElement<GetInvoiceByIdResponse> createGetInvoiceByIdResponse(GetInvoiceByIdResponse value) {
        return new JAXBElement<GetInvoiceByIdResponse>(_GetInvoiceByIdResponse_QNAME, GetInvoiceByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInvoices }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mechanic.hccis.ca/", name = "getInvoices")
    public JAXBElement<GetInvoices> createGetInvoices(GetInvoices value) {
        return new JAXBElement<GetInvoices>(_GetInvoices_QNAME, GetInvoices.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInvoiceById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.mechanic.hccis.ca/", name = "getInvoiceById")
    public JAXBElement<GetInvoiceById> createGetInvoiceById(GetInvoiceById value) {
        return new JAXBElement<GetInvoiceById>(_GetInvoiceById_QNAME, GetInvoiceById.class, null, value);
    }

}

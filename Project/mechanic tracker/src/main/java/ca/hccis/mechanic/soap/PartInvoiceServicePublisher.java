package ca.hccis.mechanic.soap;

import javax.xml.ws.Endpoint;

public class PartInvoiceServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish(
                "http://0.0.0.0:8083/partinvoiceservice",
                new PartInvoiceServiceImpl());
        //wsdl --> http://localhost:8083/partinvoiceservice?wsdl
    }
}

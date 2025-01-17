package org.korolev.dens.productservice.exceptions;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

    private static final QName CODE = new QName("code");
    private static final QName MESSAGE = new QName("message");

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        logger.warn("Exception processed: ", ex);
        SoapFaultDetail soapFaultDetail = fault.addFaultDetail();
        if (ex instanceof InvalidParamsException) {
            soapFaultDetail.addFaultDetailElement(CODE).addText("400");
        } else if (ex instanceof ProductNotFoundException) {
            soapFaultDetail.addFaultDetailElement(CODE).addText("404");
        } else if (ex instanceof ViolationOfUniqueFieldException) {
            soapFaultDetail.addFaultDetailElement(CODE).addText("409");
        } else {
            soapFaultDetail.addFaultDetailElement(CODE).addText("500");
        }
        soapFaultDetail.addFaultDetailElement(MESSAGE).addText(ex.getMessage());
    }

}

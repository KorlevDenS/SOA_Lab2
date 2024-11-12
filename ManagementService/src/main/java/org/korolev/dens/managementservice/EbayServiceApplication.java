package org.korolev.dens.managementservice;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.korolev.dens.managementservice.exceptions.DefaultExceptionHandler;
import org.korolev.dens.managementservice.exceptions.InvalidParamsExceptionHandler;
import org.korolev.dens.managementservice.exceptions.ProductNotFoundExceptionHandler;
import org.korolev.dens.managementservice.exceptions.ProductServiceExceptionHandler;
import org.korolev.dens.managementservice.resources.FilterResource;
import org.korolev.dens.managementservice.resources.PriceResource;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/ebay")
public class EbayServiceApplication extends Application {

    private final Set<Class<?>> classes = new HashSet<>();

    public EbayServiceApplication() {
        classes.add(FilterResource.class);
        classes.add(PriceResource.class);
        classes.add(ProductServiceExceptionHandler.class);
        classes.add(DefaultExceptionHandler.class);
        classes.add(InvalidParamsExceptionHandler.class);
        classes.add(ProductNotFoundExceptionHandler.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

}

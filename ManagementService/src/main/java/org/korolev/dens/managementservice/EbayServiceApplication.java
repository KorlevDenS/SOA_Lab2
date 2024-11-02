package org.korolev.dens.managementservice;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.korolev.dens.managementservice.exceptions.DefaultExceptionHandler;
import org.korolev.dens.managementservice.exceptions.ProductServiceExceptionHandler;
import org.korolev.dens.managementservice.resources.ProductsResource;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/ebay")
public class EbayServiceApplication extends Application {

    private final Set<Class<?>> classes = new HashSet<>();

    public EbayServiceApplication() {
        classes.add(ProductsResource.class);
        classes.add(ProductServiceExceptionHandler.class);
        classes.add(DefaultExceptionHandler.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

}

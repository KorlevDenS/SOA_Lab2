package ru.soa.web;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import ru.soa.web.filters.CORSRequestFilter;
import ru.soa.web.resources.FilterResource;
import ru.soa.web.resources.PingResource;
import ru.soa.web.resources.PriceResource;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class HelloApplication extends Application {
    private final Set<Class<?>> classes = new HashSet<>();

    public HelloApplication() {
        classes.add(CORSRequestFilter.class);
        classes.add(FilterResource.class);
        classes.add(PingResource.class);
        classes.add(PriceResource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}



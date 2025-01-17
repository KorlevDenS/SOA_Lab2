package org.korolev.dens.productservice.configuration;

import org.korolev.dens.productservice.exceptions.DetailSoapFaultDefinitionExceptionResolver;
import org.korolev.dens.productservice.exceptions.InvalidParamsException;
import org.korolev.dens.productservice.exceptions.ProductNotFoundException;
import org.korolev.dens.productservice.exceptions.ViolationOfUniqueFieldException;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Properties;

@EnableWs
@EnableJpaAuditing
@Configuration
public class AppConfig {

    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver() {
        SoapFaultMappingExceptionResolver resolver = new DetailSoapFaultDefinitionExceptionResolver();
        SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
        faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
        resolver.setDefaultFault(faultDefinition);
        Properties errorMappings = new Properties();

        errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
        errorMappings.setProperty(InvalidParamsException.class.getName(), SoapFaultDefinition.CLIENT.toString());
        errorMappings.setProperty(ProductNotFoundException.class.getName(), SoapFaultDefinition.SERVER.toString());
        errorMappings.setProperty(ViolationOfUniqueFieldException.class.getName(), SoapFaultDefinition.CLIENT.toString());

        resolver.setExceptionMappings(errorMappings);
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/products/*");
    }

    @Bean(name = "schema")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("SchemaPort");
        wsdl11Definition.setLocationUri("/products");
        wsdl11Definition.setTargetNamespace("http://org/korolev/dens/productservice/jaxb");
        wsdl11Definition.setSchema(schema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema xsdSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schema.xsd"));
    }

}

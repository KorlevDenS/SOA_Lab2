package org.korolev.dens.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
//        System.setProperty("javax.net.ssl.trustStore", "./jetty.truststore");
//        System.setProperty("javax.net.ssl.trustStorePassword", "secret");
//        System.setProperty("jsse.enableSNIExtension", "false");
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}

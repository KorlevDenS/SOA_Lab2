package org.korolev.dens.productservice;

import lombok.NonNull;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.ServerConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        System.setProperty("jsse.enableSNIExtension", "false");
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*");
            }
        };
    }

    @Bean
    JettyServerCustomizer disableSniHostCheck() {
        return (server) -> {
            for (Connector connector : server.getConnectors()) {
                if (connector instanceof ServerConnector serverConnector) {
                    HttpConnectionFactory connectionFactory = serverConnector
                            .getConnectionFactory(HttpConnectionFactory.class);
                    if (connectionFactory != null) {
                        SecureRequestCustomizer secureRequestCustomizer = connectionFactory.getHttpConfiguration()
                                .getCustomizer(SecureRequestCustomizer.class);
                        if (secureRequestCustomizer != null) {
                            secureRequestCustomizer.setSniHostCheck(false);
                        }
                    }
                }
            }
        };
    }

}

package org.korolev.dens.managementservice;

//import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.korolev.dens.managementservice.exceptions.DefaultExceptionHandler;
import org.korolev.dens.managementservice.exceptions.InvalidParamsExceptionHandler;
import org.korolev.dens.managementservice.exceptions.ProductNotFoundExceptionHandler;
import org.korolev.dens.managementservice.exceptions.ProductServiceExceptionHandler;
import org.korolev.dens.managementservice.filters.CORSRequestFilter;
import org.korolev.dens.managementservice.filters.CORSResponseFilter;
import org.korolev.dens.managementservice.resources.FilterResource;
import org.korolev.dens.managementservice.resources.PriceResource;

//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManagerFactory;
//import java.io.FileInputStream;
//import java.security.KeyStore;
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
        classes.add(CORSResponseFilter.class);
        classes.add(CORSRequestFilter.class);
    }

//    @PostConstruct
//    public void init() {
//        try {
//            String keyStorePath = "/home/studs/s333165/ProductService/keystore.jks"; // Укажите полный путь к файлу
//            String password = "s333165"; // Укажите пароль к keystore
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            KeyStore keyStore = KeyStore.getInstance("JKS");
//            try (FileInputStream keyStoreStream = new FileInputStream(keyStorePath)) {
//                keyStore.load(keyStoreStream, password.toCharArray());
//            }
//            tmf.init(keyStore);
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, tmf.getTrustManagers(), null);
//            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

}

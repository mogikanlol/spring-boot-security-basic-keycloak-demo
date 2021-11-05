package com.example.client;

import feign.Client;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Slf4j
public class HelloClientConfiguration {

    @Bean
    public Client feignClient() {
        return new Client.Default(getSSLSocketFactory(), new NoopHostnameVerifier());
    }

    private SSLSocketFactory getSSLSocketFactory() {


        try {

            /*
//https://stackoverflow.com/questions/9921548/sslsocketfactory-in-java

//https://stackoverflow.com/questions/57418919/using-different-ssl-context-for-rest-soap-jdbc-in-spring-boot

            TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            SSLContext sslContext1 = SSLContext
                    .getInstance("TLS");
            sslContext1
                    .init(null, null, null);
            sslContext1.getSocketFactory();
*/

            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(new ClassPathResource("clienttruststore.p12").getFile(), "changeit".toCharArray())
                    .build();
            return sslContext.getSocketFactory();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }
}

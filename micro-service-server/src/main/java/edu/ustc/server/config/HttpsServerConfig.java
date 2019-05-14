package edu.ustc.server.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

//@Configuration
public class HttpsServerConfig {

    // keytool -genkey -alias tomcat -keypass 123456 -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore /root/.keystore -validity 3650
    @Bean
    public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {

        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setSsl(getSsl());
        factory.setPort(8443);
        factory.addAdditionalTomcatConnectors(getHttpConnector());

        factory.addContextCustomizers(context -> {
            addSecurityConstraint(context);
        });

//        factory.addConnectorCustomizers(connector -> {
//            connector.setAllowTrace(true);
//        });

        return factory;
    }

    private Ssl getSsl() {
        Ssl ssl = new Ssl();
        ssl.setKeyStore("/root/.keystore");
        ssl.setKeyStorePassword("123456");
        ssl.setKeyStoreType("PKCS12");
        ssl.setKeyAlias("tomcat");
        return ssl;
    }

    private Connector getHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }

    private void addSecurityConstraint(Context context) {
        SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        collection.addMethod("HEAD");
        collection.addMethod("PUT");
        collection.addMethod("DELETE");
        collection.addMethod("OPTIONS");
        collection.addMethod("TRACE");
        collection.addMethod("COPY");
        collection.addMethod("SEARCH");
        collection.addMethod("PROPFIND");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
    }
}

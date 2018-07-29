package org.springframework.samples.petclinic.util;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.XsdSchemaCollection;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "owner")
    public DefaultWsdl11Definition ownerWsdl11Definition(XsdSchema ownersSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("OwnerPort");
        wsdl11Definition.setLocationUri("/ws/owner");
        wsdl11Definition.setTargetNamespace("http://petclinic.samples.springframework.org/soap");
        wsdl11Definition.setSchema(ownersSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema ownersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/owner.xsd"));
    }

    @Bean(name = "pet")
    public DefaultWsdl11Definition petWsdl11Definition(XsdSchema petSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PetPort");
        wsdl11Definition.setLocationUri("/ws/pet");
        wsdl11Definition.setTargetNamespace("http://petclinic.samples.springframework.org/soap/pet");
        wsdl11Definition.setSchema(petSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema petSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/pet.xsd"));
    }


//    @Bean
//    public XsdSchemaCollection quotesSchemaCollection() {
//        return new XsdSchemaCollection() {
//
//            public XsdSchema[] getXsdSchemas() {
//                return new XsdSchema[]{new SimpleXsdSchema(new ClassPathResource("xsd/pet.xsd")), new SimpleXsdSchema(new ClassPathResource("xsd/owner.xsd"))};
//            }
//
//            public XmlValidator createValidator() {
//                throw new UnsupportedOperationException();
//            }
//        };
//    }
}

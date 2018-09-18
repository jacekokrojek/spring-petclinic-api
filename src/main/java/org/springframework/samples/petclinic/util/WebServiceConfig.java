package org.springframework.samples.petclinic.util;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.samples.petclinic.soap.pet.Pet;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurationSupport;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.endpoint.adapter.DefaultMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.XsdSchemaCollection;

import java.util.ArrayList;
import java.util.List;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurationSupport {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean
    @Override
    public DefaultMethodEndpointAdapter defaultMethodEndpointAdapter() {
        List<MethodArgumentResolver> argumentResolvers =
            new ArrayList<MethodArgumentResolver>();
        argumentResolvers.add(methodProcessor());

        List<MethodReturnValueHandler> returnValueHandlers =
            new ArrayList<MethodReturnValueHandler>();
        returnValueHandlers.add(methodProcessor());

        DefaultMethodEndpointAdapter adapter = new DefaultMethodEndpointAdapter();
        adapter.setMethodArgumentResolvers(argumentResolvers);
        adapter.setMethodReturnValueHandlers(returnValueHandlers);

        return adapter;
    }

    @Bean
    public MarshallingPayloadMethodProcessor methodProcessor() {
        return new MarshallingPayloadMethodProcessor(marshaller());
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("org.springframework.samples.petclinic.soap.pet");
        marshaller.setMtomEnabled(true);
        return marshaller;
    }

    @Bean(name = "owner")
    public DefaultWsdl11Definition ownerWsdl11Definition(XsdSchema ownersSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("OwnerPort");
        wsdl11Definition.setLocationUri("/ws/owner");
        wsdl11Definition.setTargetNamespace("http://petclinic.samples.springframework.org/soap/owner");
        wsdl11Definition.setSchema(ownersSchema);
        return wsdl11Definition;
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

    @Bean(name = "visit")
    public DefaultWsdl11Definition visitWsdl11Definition(XsdSchema visitSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("VisitPort");
        wsdl11Definition.setLocationUri("/ws/visit");
        wsdl11Definition.setTargetNamespace("http://petclinic.samples.springframework.org/soap/pet");
        wsdl11Definition.setSchema(visitSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema ownersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/owner.xsd"));
    }

    @Bean
    public XsdSchema petSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/pet.xsd"));
    }

    @Bean
    public XsdSchema visitSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/visit.xsd"));
    }

}

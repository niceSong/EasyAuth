package com.tyytogether.configuretion;

import com.tyytogether.filter.CertifaicationGlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ComponentScan
@Order(1)
public class CertificationConfiguration {

    @Bean
    public CertifaicationGlobalFilter gateWay(){
        return new CertifaicationGlobalFilter();
    }

}

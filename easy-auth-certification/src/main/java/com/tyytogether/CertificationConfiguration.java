package com.tyytogether;

import com.tyytogether.filter.CertifaicationGlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class CertificationConfiguration {

    @Bean
    public CertifaicationGlobalFilter gateWay(){
        return new CertifaicationGlobalFilter();
    }

}

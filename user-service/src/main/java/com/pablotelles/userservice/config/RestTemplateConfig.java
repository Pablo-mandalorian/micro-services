package com.pablotelles.userservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    
    //1.
    @Bean //Para poder injectarlo
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}

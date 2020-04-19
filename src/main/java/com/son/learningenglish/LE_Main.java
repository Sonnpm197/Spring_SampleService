package com.son.learningenglish;

import com.son.learningenglish.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication

// the trigger for Spring Cloud to enable
// the application to use the DiscoveryClient and Ribbon libraries
@EnableDiscoveryClient // you don't need this when register with eureka

// Call to service using netflix feign
@EnableFeignClients

// Manually refresh when you push something to repo config: call /refresh
// works on an @Configuration class
@RefreshScope

// Handle error with hystrix
@EnableCircuitBreaker
public class LE_Main {

    // @EnableDiscoveryClient + @EnableFeignClients
    // are not needed when using the Ribbon backed RestTemplate and can be removed
    @LoadBalanced // @LoadBalanced tells Spring Cloud to create a Ribbon backed RestTemplate
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        } else {
            interceptors.add(new UserContextInterceptor());
            template.setInterceptors(interceptors);
        }
        return template;
    }

    public static void main(String[] arguments) {
        SpringApplication.run(LE_Main.class, arguments);
    }
}

package com.son.learningenglish;

import com.son.learningenglish.utils.UserContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
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

// enforces a filter that intercepts all incoming calls to the service, checks to see
// if there’s an OAuth2 access token present in the incoming call’s HTTP header,
// and then calls back to the callback URL defined in the
// security.oauth2.resource.userInfoUri to see if the token is valid.
// Once it knows the token is valid, the @EnableResourceServer annotation
// also applies any access control rules over who and what can access a service
@EnableResourceServer
public class LE_Main {

    // @EnableDiscoveryClient + @EnableFeignClients
    // are not needed when using the Ribbon backed RestTemplate and can be removed
    @LoadBalanced // @LoadBalanced tells Spring Cloud to create a Ribbon backed RestTemplate

    // OAuth2RestTemplate class doesn’t propagate JWT-based tokens
    // UserContextInterceptor will auto inject Authorization header
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

    @Bean
    public OAuth2ProtectedResourceDetails defaultOAuth2ProtectedResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    public OAuth2ClientContext defaultOauth2ClientContext() {
        return new DefaultOAuth2ClientContext();
    }

    // Authorization HTTP header is injected into the application call out to other services
    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(
            @Qualifier("defaultOauth2ClientContext") OAuth2ClientContext oauth2ClientContext,
            @Qualifier("defaultOAuth2ProtectedResourceDetails") OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }

    public static void main(String[] arguments) {
        SpringApplication.run(LE_Main.class, arguments);
    }
}

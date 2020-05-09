package com.son.learningenglish;

import com.son.learningenglish.stream.QuizletChangeModel;
import com.son.learningenglish.utils.UserContextInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
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

// tells the service to the use the channels defined in the Sink
// interface to listen for incoming messages (default is input channel)
// If you want to use your custom channel, comment this and move to
// CustomQuizletChangeHandler

// enable this line to use with "input" channel
// Also enable QuizletChangeListener
//@EnableBinding(Sink.class)
@Slf4j
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

    // Set up redis db connection
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName("localhost");
        jedisConFactory.setPort(6379);
        return jedisConFactory;
    }

    // Carry out action against redis server
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    public static void main(String[] arguments) {
        SpringApplication.run(LE_Main.class, arguments);
    }
}

package com.son.learningenglish.controller;

import com.son.learningenglish.model.Quizlet;
import com.son.learningenglish.redis.QuizletRestTemplateClient;
import com.son.learningenglish.service.SampleService;
import com.son.learningenglish.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SampleController {
    @Value("${user.name}")
    private String userName;

    // Test mapping props from config server
    @GetMapping
    public String getUserName() {
        return userName;
    }

    @Value("${mySecretWeapon:sword}") // set default to sword
    private String mySecretWeapon;

    // Test decrypt by app (not auto decrypt by server)
    @GetMapping("/mySecretWeapon")
    public String getMySecretWeapon() {
        return mySecretWeapon;
    }

    @Autowired
    private SampleService sampleService;

    @GetMapping("quizlet-discovery-client")
    public String callQuizlet() {
        return sampleService.callQuizlet();
    }

    // To pass UserContextHolder.getContext() from parent thread to thread own by
    // hystrix, you have to set up classes in hystrix package or set
    // @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE")
    // in child thread of hystrix
    @GetMapping("quizlet-ribbon-resttemplate")
    public String callQuizletRibbonRestTemplate() {
        log.info("Calling from parent thread: " +
                "Logging tmx-correlation-id: {}", UserContextHolder.getContext().getCorrelationId());
        return sampleService.callQuizletRibbonRestTemplate();
    }

    @GetMapping("quizlet-feign")
    public String callQuizletFeign() {
        return sampleService.callQuizletFeign();
    }

    // Test OAuth2 + return access_denied if ROLE_USER
    @DeleteMapping("sampleDelete")
    public String sampleDelete() {
        return "OK";
    }

    // Calling to test redis caching
    @Autowired
    QuizletRestTemplateClient quizletRestTemplateClient;

    @GetMapping("quizlet-redis-sample")
    public Quizlet callQuizletAndCacheToRedis() {
        return quizletRestTemplateClient.getQuizlet("1234");
    }
}

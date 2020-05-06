package com.son.learningenglish.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.son.learningenglish.model.Quizlet;
import com.son.learningenglish.utils.UserContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@FeignClient("quizlet")
interface CallQuizletUsingFeign {
    @GetMapping(value = "/")
    String getQuizletByFeign();
}

@Service
//@AllArgsConstructor

// Class level hystrix properties
//@DefaultProperties(commandProperties = {
//        @HystrixProperty(
//                name = "execution.isolation.thread.timeoutInMilliseconds",
//                value = "10000")
//})

@Slf4j
public class SampleService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    @Qualifier("getRestTemplate") // get bean by name
    private RestTemplate restTemplate; // Using Ribbon-backed restTemplate

    @Autowired
    private CallQuizletUsingFeign callQuizletUsingFeign;

    public String callQuizlet() {
        RestTemplate restTemplate = new RestTemplate();
        // eureka-client registered under whatever name you specify in the spring.application.name property
        List<ServiceInstance> serviceInstances = this.discoveryClient.getInstances("quizlet");

        if (!CollectionUtils.isEmpty(serviceInstances)) {
            ServiceInstance quizlet = serviceInstances.get(0);
            String url = quizlet.getUri().toString();
            ResponseEntity<String> restExchange =
                    restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            return restExchange.getBody() + " discovery-client";
        }
        return "No value";
    }

    // ByDefault: The circuit breaker will interrupt any call to the
    // method any time the call takes longer than 1,000 milliseconds
    // when you specify a @HystrixCommand annotation without properties,
    // the annotation will place all remote service calls under the same thread pool
//    @HystrixCommand
    // Using bulkhead : Each remote resource call is placed in its own thread pool. Each thread pool
    // has a maximum number of threads that can be used to process a request.
//    @HystrixCommand(
//            fallbackMethod = "handleQuizletFailure",
//            threadPoolKey = "callQuizletRibbonRestTemplateThreadPool",
//            threadPoolProperties = {
//                    // coreSize: Size of the thread pool
//                    @HystrixProperty(name = "coreSize", value = "30"),
//
//                    // maxQueueSize: control how many requests will be allowed to back up when the threads in the thread pool are busy
//                    // Once the number of requests exceeds the queue size, any additional requests to the
//                    // thread pool will fail until there is room in the queue.
//
//                    // NOTE TWO THINGS about the maxQueueSize attribute. First, if you set the value to -1,
//                    // a Java SynchronousQueue will be used to hold all incoming requests. A synchronous
//                    // queue will essentially enforce that you can never have more requests in process then
//                    // the number of threads available in the thread pool. Setting the maxQueueSize to a
//                    // value greater than one will cause Hystrix to use a Java LinkedBlockingQueue. The
//                    // use of a LinkedBlockingQueue allows the developer to queue up requests even if
//                    // all threads are busy processing requests.
//                    @HystrixProperty(name = "maxQueueSize", value = "10"),
//            },
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"), // 3 seconds
//
//                    // circuitBreaker.requestVolumeTheshold, controls the
//                    // amount of consecutive calls that must occur within a 10-second window before Hystrix
//                    // will consider tripping the circuit breaker for the call
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
//
//                    // circuitBreaker.errorThresholdPercentage, is the percentage of calls that must fail
//                    // (due to timeouts, an exception being thrown, or a HTTP 500 being returned) after the
//                    // circuitBreaker.requestVolumeThreshold value has been passed before the circuit breaker it tripped
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
//
//                    // circuitBreaker.sleepWindowInMilliseconds, is the amount of time Hystrix will sleep
//                    // once the circuit breaker is tripped before Hystrix will allow another call through to
//                    // see if the service is healthy again
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
//
//                    // The second property, metrics.rollingStats.numBuckets, controls the number of times
//                    // statistics are collected in the window you’ve defined. Hystrix collects metrics in
//                    // buckets during this window and checks the stats in those buckets to determine
//                    // if the remote resource call is failing.
//                    //
//                    // Required: metrics.rollingStats.timeInMilliseconds % metrics.rollingStats.numBuckets == 0
//                    //
//                    // For example, in your custom settings in the previous listing, Hystrix
//                    // will use a 15-second window and collect statistics data into five buckets of three seconds in length
//                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5"),
//                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
//
//                    // When an @HystrixCommand is executed, it can be run with two different isolation
//                    // strategies: THREAD and SEMAPHORE.
//                    // By default, Hystrix runs with a THREAD isolation.
//                    // Each Hystrix command used to protect a call runs in an isolated thread pool that
//                    // doesn’t share its context with the parent thread making the call
//
//                    // With SEMAPHORE-based isolation, Hystrix manages the distributed call protected
//                    // by the @HystrixCommand annotation without starting a new thread and will interrupt
//                    // the parent thread if the call times out
//                    @HystrixProperty(name="execution.isolation.strategy", value="THREAD")
//            }
//    )
    public String callQuizletRibbonRestTemplate() {
        // Simulate querying takes 2 seconds
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        log.info("Calling from child thread: " +
                "Logging tmx-correlation-id: {}", UserContextHolder.getContext().getCorrelationId());

        // The Ribbon-enabled RestTemplate will parse the URL passed into it and use whatever is passed
        // in as the server name as the key to query Ribbon for an instance of a service.
        // Ribbon will round-robin load balance all requests among all the service instances.
        ResponseEntity<Quizlet> restExchange =
                restTemplate.exchange("http://legateway/api/quiz/sampleQuizlet",
                        HttpMethod.GET, null, Quizlet.class);
        return (restExchange.getBody() == null ? "Something is wrong " :
                restExchange.getBody().getQuizletName()) + " ribbon-resttemplate";
    }

    private String handleQuizletFailure() {
        return "quizletFailure";
    }

    @GetMapping("call-quizlet-feign")
    public String callQuizletFeign() {
        return callQuizletUsingFeign.getQuizletByFeign() + " feign";
    }
}

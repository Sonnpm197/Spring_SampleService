package com.son.learningenglish.hystrix;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

// Now that you have your HystrixConcurrencyStrategy via the ThreadLocalAwareStrategy
// class and your Callable class defined via the DelegatingUserContextCallable class,
// you need to hook them in Spring Cloud and Hystrix.
@Configuration
public class ThreadLocalConfiguration {
    @Autowired(required = false)
    private HystrixConcurrencyStrategy existingConcurrencyStrategy;

    // rebuilds the Hystrix plugin that manages all
    // the different components running within your service. In the init() method, you’re
    // grabbing references to all the Hystrix components used by the plugin. You then
    // register your custom HystrixConcurrencyStrategy (ThreadLocalAwareStrategy)
    @PostConstruct
    public void init() {
        // Because you’re registering a new concurrency strategy, you’re going to grab
        // all the other Hystrix components and then reset the Hystrix plugin.
        HystrixPlugins hystrixPlugins = HystrixPlugins.getInstance();
        // Keeps references of existing Hystrix plugins.
        HystrixEventNotifier eventNotifier = hystrixPlugins.getEventNotifier();
        HystrixMetricsPublisher metricsPublisher = hystrixPlugins.getMetricsPublisher();
        HystrixPropertiesStrategy propertiesStrategy = hystrixPlugins.getPropertiesStrategy();
        HystrixCommandExecutionHook commandExecutionHook = hystrixPlugins.getCommandExecutionHook();

        HystrixPlugins.reset();

        // You now register your HystrixConcurrencyStrategy
        // (ThreadLocalAwareStrategy) with the Hystrix plugin
        hystrixPlugins.registerConcurrencyStrategy(
                new ThreadLocalAwareStrategy(existingConcurrencyStrategy));
        hystrixPlugins.registerEventNotifier(eventNotifier);
        hystrixPlugins.registerMetricsPublisher(metricsPublisher);
        hystrixPlugins.registerPropertiesStrategy(propertiesStrategy);
        hystrixPlugins.registerCommandExecutionHook(commandExecutionHook);
    }
}

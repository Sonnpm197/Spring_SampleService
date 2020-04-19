package com.son.learningenglish.hystrix;

import com.son.learningenglish.utils.UserContext;
import com.son.learningenglish.utils.UserContextHolder;

import java.util.concurrent.Callable;

// propagating the thread context of the parent thread to your Hystrix
// command is to implement the Callable class that will do the propagation
public final class DelegatingUserContextCallable<V> implements Callable<V> {
    private final Callable<V> delegate;
    private UserContext originalUserContext;

    // Custom Callable class will be passed the original Callable
    // class that will invoke your Hystrix protected code and
    // UserContext coming in from the parent thread
    public DelegatingUserContextCallable(Callable<V> delegate, UserContext userContext) {
        this.delegate = delegate;
        this.originalUserContext = userContext;
    }

    // The call() function is invoked before the method
    // protected by the @HystrixCommand annotation
    public V call() throws Exception {
        UserContextHolder.setContext(originalUserContext);
        try {
            // Once the UserContext is set invoke the call() method on the Hystrix protected method
            return delegate.call();
        } finally {
            this.originalUserContext = null;
        }
    }

    public static <V> Callable<V> create(Callable<V> delegate,
                                         UserContext userContext) {
        return new DelegatingUserContextCallable<V>(delegate, userContext);
    }
}

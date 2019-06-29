package edu.ustc.server.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;

public class HystrixObservableCommandThatFailsSilently extends HystrixObservableCommand<String> {

    private final boolean throwException;

    public HystrixObservableCommandThatFailsSilently(boolean throwException) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.throwException = throwException;
    }

    @Override
    protected Observable<String> construct() {
        return null;
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        return Observable.empty();
    }
}
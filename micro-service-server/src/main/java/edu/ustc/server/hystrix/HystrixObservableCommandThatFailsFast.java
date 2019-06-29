package edu.ustc.server.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;

public class HystrixObservableCommandThatFailsFast extends HystrixObservableCommand<String> {

    private final boolean throwException;

    public HystrixObservableCommandThatFailsFast(boolean throwException) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.throwException = throwException;
    }

    @Override
    protected Observable<String> construct() {
        return null;
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        if (throwException) {
            return Observable.error(new Throwable("failure from HystrixObservableCommandThatFailsFast"));
        } else {
            return Observable.just("success");
        }
    }
}
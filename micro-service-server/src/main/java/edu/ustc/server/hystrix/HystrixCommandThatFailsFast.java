package edu.ustc.server.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixCommandThatFailsFast extends HystrixCommand<String> {

    private final boolean throwException;

    public HystrixCommandThatFailsFast(boolean throwException) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.throwException = throwException;
    }

    @Override
    protected String run() {
        if (throwException) {
            throw new RuntimeException("failure from HystrixCommandThatFailsFast");
        } else {
            return "success";
        }
    }
}
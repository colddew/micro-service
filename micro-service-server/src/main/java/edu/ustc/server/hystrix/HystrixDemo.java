package edu.ustc.server.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.Future;

public class HystrixDemo {

    public static void main(String[] args) throws Exception {

        String s = new HystrixCommandHelloWorld("World").execute();

        Future<String> future = new HystrixCommandHelloWorld("World").queue();
        String fs = future.get();

        Observable<String> observable = new HystrixCommandHelloWorld("World").observe();
//        Observable<String> toObservable = new HystrixCommandHelloWorld("World").toObservable();

        // blocking
        String os = observable.toBlocking().single();

        // non-blocking
        observable.subscribe(new Action1<String>() {

            @Override
            public void call(String v) {
                System.out.println("onNext: " + v);
            }
        });

        observable.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                // nothing needed here
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String v) {
                System.out.println("onNext: " + v);
            }
        });

        // Java 8 lambdas / closures
        observable.subscribe((v) -> {
            System.out.println("onNext: " + v);
        });

        observable.subscribe((v) -> {
            System.out.println("onNext: " + v);
        }, (exception) -> {
            exception.printStackTrace();
        });

        // fallback
        new HystrixCommandHelloFailure("World").execute();

        // request cache
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            HystrixCommandUsingRequestCache cache = new HystrixCommandUsingRequestCache(2);
            cache.execute();
            System.out.println(cache.isResponseFromCache());
            cache.execute();
            System.out.println(cache.isResponseFromCache());
        } finally {
            context.shutdown();
        }

        // request-scoped collapser
        context = HystrixRequestContext.initializeContext();
        try {
            Future<String> f = new HystrixCommandCollapserGetValueForKey(1).queue();
            System.out.println(f.get());
            System.out.println(HystrixRequestLog.getCurrentRequest().getExecutedCommands().size());

            HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
            System.out.println(command.getCommandKey().name());
            System.out.println(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
            System.out.println(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
        } finally {
            context.shutdown();
        }

        // fail fast
        new HystrixCommandThatFailsFast(true).execute();

        // fail silent
        new HystrixCommandThatFailsSilently(true).execute();
    }
}

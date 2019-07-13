package edu.ustc.server.rxjava;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RxJavaStart {

    private static final Logger logger = LoggerFactory.getLogger(RxJavaStart.class);

    public static void main(String[] args) throws Exception {

//        sync();
//        async();
//        backpressure();
//        delayError();
        window();

        // 当调用observeOn()或者subscribeOn()后代码运行在子线程，如果子线程还没来得及调用map()和subscribe()主线程就执行完了，有可能是看不到运行结果
        Thread.sleep(10000);
    }

    private static void sync() {

        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("subscribe one ...");
            emitter.onNext("subscribe two ...");
            emitter.onNext("subscribe three ...");
            emitter.onComplete();
        });

        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                logger.info("subscribe, disposable: {}", d.isDisposed());
            }

            @Override
            public void onNext(String s) {
                logger.info("next, {}", s);
            }

            @Override
            public void onError(Throwable e) {
                logger.info("error, {}", e.getMessage());
            }

            @Override
            public void onComplete() {
                logger.info("complete");
            }
        };

        observable.subscribe(observer);
    }

    private static void async() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                logger.info("send thread name: {}", Thread.currentThread().getName());
                emitter.onNext("async subscribe one ...");
                emitter.onNext("async subscribe two ...");
                emitter.onNext("async subscribe three ...");
                emitter.onComplete();
            }
        })

        // observable thread
//        .subscribeOn(Schedulers.io())
        .subscribeOn(Schedulers.computation())
//        .subscribeOn(Schedulers.newThread())

        // observer thread, allow more than twice
//        .observeOn(Schedulers.trampoline())
        .observeOn(Schedulers.newThread())
//        .observeOn(AndroidSchedulers.mainThread())

//        .subscribe(s -> {
//            logger.info("accept, {}", s);
//        })

        .subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                logger.info("async subscribe, disposable: {}", d.isDisposed());
            }

            @Override
            public void onNext(String s) {
                logger.info("receive thread name: {}", Thread.currentThread().getName());
                logger.info("async next, {}", s);
            }

            @Override
            public void onError(Throwable e) {
                logger.info("async error, {}", e.getMessage());
            }

            @Override
            public void onComplete() {
                logger.info("async complete");
            }
        });
    }

    private static void backpressure() {

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.computation())
                .map(i -> i + 1)
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
//                        s.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logger.info("next, {}", integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logger.info(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        logger.info("complete");
                    }
                });
    }

    private static void delayError() throws Exception {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Scheduler androidMainThread = Schedulers.from(executor);

        Observable<String> observable = Observable.create(subscriber -> {
            subscriber.onNext("text");
            Thread.sleep(1000);
            subscriber.onError(new RuntimeException());
        });

        executor.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.info(e.getMessage());
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(androidMainThread, true)
                .subscribe(s -> {
                    logger.info("onNext, {}" + s);
                }, throwable -> {
                    logger.info(throwable.getMessage());
                });

        executor.awaitTermination(3, TimeUnit.SECONDS);
        executor.shutdown();
    }

    private static void window() {

        Observable.interval(1, TimeUnit.SECONDS)
                .take(15) // the maximum number of items to emit
                .window(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(Observable<Long> longObservable) throws Exception {
                        logger.info("Sub Divide ...");
                        longObservable.subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.newThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        logger.info("Next, {}", aLong);
                                    }
                                });
                    }
                });
    }
}

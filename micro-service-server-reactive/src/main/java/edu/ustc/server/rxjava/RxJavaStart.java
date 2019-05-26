package edu.ustc.server.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RxJavaStart {

    private static final Logger logger = LoggerFactory.getLogger(RxJavaStart.class);

    public static void main(String[] args) {
//        sync();
        async();
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

        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            logger.info("send thread name: {}", Thread.currentThread().getName());
            emitter.onNext("async subscribe one ...");
            emitter.onNext("async subscribe two ...");
            emitter.onNext("async subscribe three ...");
            emitter.onComplete();
        })

        // observable thread
//        .subscribeOn(Schedulers.io())

        // observer thread, allow more than twice
        .observeOn(Schedulers.trampoline())
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
}

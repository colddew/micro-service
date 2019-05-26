package edu.ustc.server.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RxJavaStart {

    private static final Logger logger = LoggerFactory.getLogger(RxJavaStart.class);

    public static void main(String[] args) {

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
}

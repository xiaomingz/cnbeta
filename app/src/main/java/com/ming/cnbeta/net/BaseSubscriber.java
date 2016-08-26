package com.ming.cnbeta.net;

import rx.Subscriber;

/**
 * Created by ming on 16/8/4.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    public BaseSubscriber() {
    }

    public BaseSubscriber(Subscriber<?> subscriber) {
        super(subscriber);
    }

    public BaseSubscriber(Subscriber<?> subscriber, boolean shareSubscriptions) {
        super(subscriber, shareSubscriptions);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
}

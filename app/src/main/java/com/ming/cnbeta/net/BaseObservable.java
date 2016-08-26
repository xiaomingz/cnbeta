package com.ming.cnbeta.net;


import rx.Observable;

/**
 * Created by ming on 16/8/4.
 */
public class BaseObservable<T> extends Observable<T> {

    public BaseObservable(OnSubscribe<T> f) {
        super(f);
    }

}

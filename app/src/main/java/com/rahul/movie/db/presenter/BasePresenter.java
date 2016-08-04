package com.rahul.movie.db.presenter;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * \tCreated by Rahul on 8/3/2016.
 */

abstract class BasePresenter implements Presenter {
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {
        createCompositeSubscriptionConfig();
    }


    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        unSubscribeAll();

    }

    private CompositeSubscription createCompositeSubscriptionConfig() {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
        return mCompositeSubscription;
    }

    void unSubscribeAll() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.isUnsubscribed();
            mCompositeSubscription.clear();
        }
    }

    <M> void subscribe(Observable<M> observable, Observer<M> observer) {
        Subscription subscriptions = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.computation())
                .subscribe(observer);
        createCompositeSubscriptionConfig().add(subscriptions);

    }
}

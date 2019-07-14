package com.samsung.samsungcodingtest.netwrork;

import android.util.Log;

import com.samsung.samsungcodingtest.model.PhotoDetails;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class PhotoApiService {
    private static final String TAG  = PhotoApiService.class.getCanonicalName();
    private PhotoService mPhotoService;

    public PhotoApiService(PhotoService eventService){
        this.mPhotoService = eventService;
    }

    public Subscription getPhotoList(final GetPhotoListCallback getPhotoListCallback){
        return mPhotoService.getPhotoList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ArrayList<PhotoDetails>>>() {
                    @Override
                    public Observable<? extends ArrayList<PhotoDetails>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ArrayList<PhotoDetails>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG,"HTTP Response complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,e.getMessage());
                        getPhotoListCallback.onHttpResponseError(e);
                    }

                    @Override
                    public void onNext(ArrayList<PhotoDetails> photoDetailsArrayList) {
                        getPhotoListCallback.onHttpRequestComplete(photoDetailsArrayList);
                    }
                });
    }
}

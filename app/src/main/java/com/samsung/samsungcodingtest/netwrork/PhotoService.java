package com.samsung.samsungcodingtest.netwrork;

import com.samsung.samsungcodingtest.model.PhotoDetails;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface PhotoService {
    @GET("photos?")
    Observable<ArrayList<PhotoDetails>> getPhotoList();
}

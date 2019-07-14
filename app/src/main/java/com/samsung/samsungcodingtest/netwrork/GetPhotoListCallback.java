package com.samsung.samsungcodingtest.netwrork;

import com.samsung.samsungcodingtest.model.PhotoDetails;

import java.util.ArrayList;

public interface GetPhotoListCallback {
    void onHttpResponseError(Throwable exception);

    void onHttpRequestComplete(ArrayList<PhotoDetails> photoDetailsArrayList);
}

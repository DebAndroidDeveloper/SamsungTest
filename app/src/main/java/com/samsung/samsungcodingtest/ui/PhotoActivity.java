package com.samsung.samsungcodingtest.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.samsung.samsungcodingtest.R;
import com.samsung.samsungcodingtest.adapter.PhotoListAdapter;
import com.samsung.samsungcodingtest.database.PhotoDBRepository;
import com.samsung.samsungcodingtest.model.PhotoDetails;


import com.samsung.samsungcodingtest.netwrork.DaggerNetworkComponent;
import com.samsung.samsungcodingtest.netwrork.GetPhotoListCallback;
import com.samsung.samsungcodingtest.netwrork.NetworkComponent;
import com.samsung.samsungcodingtest.netwrork.NetworkModule;
import com.samsung.samsungcodingtest.netwrork.PhotoApiService;
import com.samsung.samsungcodingtest.util.CommonUtils;
import com.samsung.samsungcodingtest.util.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class PhotoActivity extends AppCompatActivity implements GetPhotoListCallback,PhotoListAdapter.Listener {
    private static final String TAG = PhotoActivity.class.getCanonicalName();

    private PhotoListAdapter photoListAdapter;
    private ProgressDialog mProgressDialog;
    NetworkComponent networkComponent;
    @Inject
    PhotoApiService mPhotoApiService;
    private CompositeSubscription subscriptions;
    private PhotoDBRepository photoDBRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_photo);
        this.photoDBRepository = new PhotoDBRepository();
        //this.mBinding = DataBindingUtil.setContentView(this,R.layout.activity_photo);
        networkComponent = DaggerNetworkComponent.builder().networkModule(new NetworkModule()).build();
        networkComponent.inject(this);
        RecyclerView photoListView = findViewById(R.id.recyclerView_Photo);
        this.photoListAdapter = new PhotoListAdapter(this,this);
        photoListView.setAdapter(photoListAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        ((GridLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        photoListView.setLayoutManager(layoutManager);
        photoListView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        photoListView.addItemDecoration(itemDecoration);
        //int spacingInPixels = getResources().getDimensionPixelSize(25);
        photoListView.addItemDecoration(new SpacesItemDecoration(25));
        this.subscriptions = new CompositeSubscription();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("One moment please...");
        mProgressDialog.setCancelable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(CommonUtils.isNetworkAvailable(this)) {
            Subscription subscription = mPhotoApiService.getPhotoList(this);
            subscriptions.add(subscription);
        }else {
            CommonUtils.showErrorDialog(this,"Internet connection not available");
        }
    }

    @Override
    public void onRowItemClicked(@NonNull PhotoDetails photoDetails) {
        Intent intent = new Intent(this,PhotoDetailsActivity.class);
        intent.putExtra(Constants.PHOTO_TITLE,photoDetails.getTitle());
        intent.putExtra(Constants.PHOTO_URL,photoDetails.getUrl());
        intent.putExtra(Constants.PHOTO_ID,photoDetails.getId());
        this.startActivity(intent);
    }

    @Override
    public void onHttpRequestComplete(ArrayList<PhotoDetails> photoDetailsArrayList) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        this.photoListAdapter.setPhotoList(photoDetailsArrayList);
        this.photoDBRepository.savePhotos(photoDetailsArrayList);
    }

    @Override
    public void onHttpResponseError(Throwable exception) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        Log.e(TAG, exception.getMessage());
        CommonUtils.showErrorDialog(this,exception.getMessage());
    }

    @Override
    public void onStop() {
        super.onStop();
        subscriptions.unsubscribe();
    }
}

package com.samsung.samsungcodingtest.netwrork;

import com.samsung.samsungcodingtest.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Retrofit provideCall(){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.PHOTO_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public PhotoService providesPhotoService(
            Retrofit retrofit) {
        return retrofit.create(PhotoService.class);
    }
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public PhotoApiService providesPhotoApiService(
            PhotoService photoService) {
        return new PhotoApiService(photoService);
    }
}

package com.samsung.samsungcodingtest.netwrork;

import com.samsung.samsungcodingtest.ui.PhotoActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface NetworkComponent {
    void inject(PhotoActivity mainActivity);
}

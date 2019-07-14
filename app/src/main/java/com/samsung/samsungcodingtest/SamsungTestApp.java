package com.samsung.samsungcodingtest;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SamsungTestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("samsungtest")
                .build();

        Realm.setDefaultConfiguration(config);
    }
}

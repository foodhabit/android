package com.example.hdavidzhu.foodhabit;

import android.app.Application;

import com.example.hdavidzhu.foodhabit.providers.BackendProvider;

import timber.log.Timber;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        BackendProvider.getInstance().setContext(this);
    }
}

package com.leafmoment.app.foodhabit;

import android.app.Application;

import com.leafmoment.app.foodhabit.providers.BackendProvider;

import timber.log.Timber;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        BackendProvider.getInstance().setContext(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        BackendProvider.getInstance().destroy();
    }
}

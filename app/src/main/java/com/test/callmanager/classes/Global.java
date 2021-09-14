package com.test.callmanager.classes;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class Global extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(getApplicationContext());

    }
}

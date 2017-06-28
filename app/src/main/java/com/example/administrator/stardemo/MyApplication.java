package com.example.administrator.stardemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by Administrator on 10/28/2016.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
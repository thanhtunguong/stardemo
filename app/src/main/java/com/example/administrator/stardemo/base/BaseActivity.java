package com.example.administrator.stardemo.base;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.androidnetworking.AndroidNetworking;
import com.example.administrator.stardemo.global.Global;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

/**
 * Created by Administrator on 06/18/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAndroidNetwordkingWithProtocol();
    }

    public void initAndroidNetwordkingWithProtocol() {
        List<Protocol> protocolList = new ArrayList<>();
        protocolList.add(Protocol.HTTP_1_1);
        protocolList.add(Protocol.HTTP_2);
        protocolList.add(Protocol.SPDY_3);

        /*OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .protocols(protocolList)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();*/

        OkHttpClient okHttpClient = Global.getUnsafeOkHttpClient();

        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

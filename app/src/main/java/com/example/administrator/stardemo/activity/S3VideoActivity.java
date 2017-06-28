package com.example.administrator.stardemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.VideoView;

import com.example.administrator.stardemo.R;
import com.example.administrator.stardemo.base.BaseActivity;
import com.example.administrator.stardemo.interfaces.S3VideoImpl;

public class S3VideoActivity extends BaseActivity {

    private Toolbar toolbar;
    private VideoView videoView;
    private S3VideoImpl s3VideoImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3_video);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        videoView = (VideoView) findViewById(R.id.videoView);

        String url = this.getResources().getString(R.string.test_video_url);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            url = bundle.getString("url");
        }
        s3VideoImpl = new S3VideoImpl(this, videoView);
        s3VideoImpl.playUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.blank_menu, menu);
        return true;
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

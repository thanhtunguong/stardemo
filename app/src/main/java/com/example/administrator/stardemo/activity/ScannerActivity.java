package com.example.administrator.stardemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.stardemo.R;
import com.example.administrator.stardemo.base.BaseActivity;
import com.example.administrator.stardemo.controller.FragmentController;
import com.example.administrator.stardemo.fragment.PlayVideoFragment;
import com.example.administrator.stardemo.fragment.ScanFragment;
import com.example.administrator.stardemo.fragment.YoutubeVideoFragment;
import com.example.administrator.stardemo.global.Const;
import com.example.administrator.stardemo.global.Global;
import com.example.administrator.stardemo.interfaces.ScannerInterface;
import com.example.administrator.stardemo.interfaces.StarDemoAPICaller;

import static com.example.administrator.stardemo.global.Const.RC_HANDLE_CAMERA_PERM;

/**
 * Created by Administrator on 06/18/2017.
 */

public class ScannerActivity extends BaseActivity implements ScannerInterface {

    private Toolbar toolbar;
    private ScannerActivity activity;
    public String scanningResult;
    private ScanFragment scanFragment;
    private StarDemoAPICaller starDemoAPICaller;

    private FrameLayout frameLayoutPlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        activity = this;

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        frameLayoutPlaceHolder = (FrameLayout) findViewById(R.id.frameLayoutPlaceHolder);
        starDemoAPICaller = new StarDemoAPICaller(activity);

        Global.requestCameraPermission(this, frameLayoutPlaceHolder);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(Const.TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(Const.TAG, "Camera permission granted - initialize the camera source");
            scanFragment = new ScanFragment();
            FragmentController.replaceDontAddToBackStack(this, ScanFragment.class.toString(), scanFragment);
//            FragmentController.addAddToBackStackUsingRightEnterAnimation(this, YoutubeVideoFragment.class.toString(), new YoutubeVideoFragment());
            return;
        }

        Log.e(Const.TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multitracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok_cap, listener)
                .show();
    }

    @Override
    public void onScanningResult(String scanningResult) {
//        this.scanningResult = scanningResult;
//        FragmentController.addAddToBackStackUsingRightEnterAnimation(this, PlayVideoFragment.class.toString(), new PlayVideoFragment());
        starDemoAPICaller.getUrlFromScanningResult(scanningResult);
    }

    public void onUrlReceived(String url){
        this.scanningResult = url;
        if (url.contains("amazonaws.com")){
            Intent intent = new Intent(ScannerActivity.this, S3VideoActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        } else {
            FragmentController.addAddToBackStackUsingRightEnterAnimation(this, PlayVideoFragment.class.toString(), new PlayVideoFragment());
        }
    }

    @Override
    public void restartCameraOnBackStack() {
        if (scanFragment != null){
            scanFragment.onResume();
        }
    }

    public String getScanningResult() {
        return scanningResult;
    }

    public void setScanningResult(String scanningResult) {
        this.scanningResult = scanningResult;
    }
}
package com.example.administrator.stardemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.stardemo.R;
import com.example.administrator.stardemo.base.BaseActivity;
import com.example.administrator.stardemo.controller.FragmentController;
import com.example.administrator.stardemo.fragment.MenuScreenFragment;
import com.example.administrator.stardemo.global.Const;
import com.example.administrator.stardemo.global.Global;

import static com.example.administrator.stardemo.global.Const.RC_HANDLE_CAMERA_PERM;

public class MainActivity extends BaseActivity {

    private MainActivity activity;
    private Context context;
    private FrameLayout frameLayoutPlaceHolder;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        context = this;

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        frameLayoutPlaceHolder = (FrameLayout) findViewById(R.id.frameLayoutPlaceHolder);

        Global.requestCameraPermission(this, frameLayoutPlaceHolder);
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
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentController.replaceDontAddToBackStackUsingAnimation(activity, MenuScreenFragment.class.toString(), new MenuScreenFragment());
                }
            }, 1000);
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
}

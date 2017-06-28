package com.example.administrator.stardemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.stardemo.activity.ScannerActivity;
import com.example.administrator.stardemo.base.BaseFragment;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Administrator on 06/18/2017.
 */

public class ScanFragment extends BaseFragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private Context context;
    private ScannerActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        context = this.getContext();
        activity = (ScannerActivity) this.getActivity();
        return mScannerView = new ZXingScannerView(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult != null && !rawResult.equals("")){
            releaseCamera();
            Toast.makeText(context, rawResult.getText(), Toast.LENGTH_SHORT).show();
            activity.onScanningResult(rawResult.getText());
        }
    }

    private void setupCamera(){
        if (mScannerView == null){
            mScannerView = new ZXingScannerView(context);
        }
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.resumeCameraPreview(this);
    }

    private void releaseCamera(){
        if (mScannerView != null) {
            mScannerView.stopCameraPreview();
            mScannerView.stopCamera();
            mScannerView.removeAllViews();
        }
    }
}
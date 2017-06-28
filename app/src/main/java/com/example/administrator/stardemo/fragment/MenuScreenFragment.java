package com.example.administrator.stardemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.stardemo.R;
import com.example.administrator.stardemo.activity.MainActivity;
import com.example.administrator.stardemo.activity.S3VideoActivity;
import com.example.administrator.stardemo.activity.ScannerActivity;
import com.example.administrator.stardemo.activity.VirtualVideoActivity;
import com.example.administrator.stardemo.activity.WebViewActivity;
import com.example.administrator.stardemo.base.BaseFragment;
import com.example.administrator.stardemo.controller.FragmentController;

/**
 * Created by Administrator on 06/22/2017.
 */

public class MenuScreenFragment extends BaseFragment {

    private Context context;
    private MainActivity activity;
    private Button btnScanner, btnS3Video, btnVRView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        return inflater.inflate(R.layout.fragment_menu_screen, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        activity = (MainActivity) this.getActivity();

        btnScanner = (Button) view.findViewById(R.id.btnYoutubeScanner);
        btnS3Video = (Button) view.findViewById(R.id.btnS3VideoPlayer);
        btnVRView = (Button) view.findViewById(R.id.btnVRViewer);

        setEvent();
    }

    private void setEnableStateAllButton(Boolean state){
        btnScanner.setEnabled(state);
        btnS3Video.setEnabled(state);
        btnVRView.setEnabled(state);
    }

    private void setEvent(){
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnableStateAllButton(false);
                Intent intent = new Intent(activity, ScannerActivity.class);
                startActivity(intent);
                setEnableStateAllButton(true);
            }
        });

        btnS3Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnableStateAllButton(false);
                Intent intent = new Intent(activity, WebViewActivity.class);
                startActivity(intent);
                setEnableStateAllButton(true);
            }
        });

        btnVRView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnableStateAllButton(false);
                Intent intent = new Intent(activity, VirtualVideoActivity.class);
                startActivity(intent);
                setEnableStateAllButton(true);
            }
        });
    }
}

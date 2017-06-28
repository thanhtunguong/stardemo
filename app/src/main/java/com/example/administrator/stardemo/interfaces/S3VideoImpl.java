package com.example.administrator.stardemo.interfaces;

import android.content.Context;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by Administrator on 06/22/2017.
 */

public class S3VideoImpl implements S3VideoInterface {

    private Context context;
    private VideoView videoView;
    private MediaController mediaController;

    public S3VideoImpl(Context context, VideoView videoView){
        this.context = context;
        this.videoView = videoView;
    }

    @Override
    public void playUrl(String stringUrl) {
        Uri uri = Uri.parse(stringUrl);
        setupMediaController();
        videoView.setVideoURI(uri);
        videoView.start();
    }

    private void setupMediaController(){
        mediaController = new MediaController(context);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }
}

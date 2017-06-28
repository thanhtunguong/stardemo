package com.example.administrator.stardemo.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.stardemo.R;
import com.example.administrator.stardemo.activity.ScannerActivity;
import com.example.administrator.stardemo.base.BaseFragment;
import com.example.administrator.stardemo.global.Global;
import com.example.administrator.stardemo.interfaces.CheckUrlConnectionInterface;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.YTParams;

import java.util.ArrayList;

/**
 * Created by Administrator on 06/18/2017.
 */

public class PlayVideoFragment extends BaseFragment implements CheckUrlConnectionInterface {

    private Context context;
    private YoutubePlayerView youtubePlayerView;
    private ScannerActivity activity;

    private ArrayList<AsyncTask> asyncTaskArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        return inflater.inflate(R.layout.fragment_play_video, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        activity = (ScannerActivity) this.getActivity();

        youtubePlayerView = (YoutubePlayerView) view.findViewById(R.id.youtubePlayerView);

        asyncTaskArrayList = new ArrayList<>();

        new Global.AsyncCheckUrlConnection(this, activity.getScanningResult()).execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (youtubePlayerView != null){
            youtubePlayerView.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (youtubePlayerView != null){
            youtubePlayerView.play();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youtubePlayerView.removeAllViews();
        activity.restartCameraOnBackStack();
        for (AsyncTask asyncTask: asyncTaskArrayList){
            asyncTask.cancel(true);
        }
    }

    private void setupYoutubePlayer(String videoId){
        videoId = Global.returnYoutubeVideoIdFromUrl(videoId);
        YTParams params = new YTParams();
        // params.setControls(0); // hide control
        // params.setVolume(100); // volume control
        // params.setPlaybackQuality(PlaybackQuality.small); // video quality control


        // initialize YoutubePlayerCallBackListener with Params and VideoID
        // youtubePlayerView.initialize("WCchr07kLPE", params, new YoutubePlayerView.YouTubeListener())


        // make auto height of youtube. if you want to use 'wrap_content'
        youtubePlayerView.setAutoPlayerHeight(context);
        // initialize YoutubePlayerCallBackListener and VideoID
        youtubePlayerView.initialize(videoId, new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                /**
                 * YoutubePlayerView.STATE
                 *
                 * UNSTARTED, ENDED, PLAYING, PAUSED, BUFFERING, CUED, NONE
                 *
                 */
            }

            @Override
            public void onPlaybackQualityChange(String arg) {
            }

            @Override
            public void onPlaybackRateChange(String arg) {
            }

            @Override
            public void onError(String error) {
            }

            @Override
            public void onApiChange(String arg) {
            }

            @Override
            public void onCurrentSecond(double second) {
                // currentTime callback
            }

            @Override
            public void onDuration(double duration) {
                // total duration
            }

            @Override
            public void logs(String log) {
                // javascript debug log. you don't need to use it.
            }
        });


        /*// psuse video
        youtubePlayerView.pause();*/
        // play video when it's ready
        youtubePlayerView.play();
    }

    @Override
    public void onCheckUrlConnection(int responseCode) {
        setupYoutubePlayer(Global.returnYoutubeVideoIdFromUrl(activity.getScanningResult()));
    }
}

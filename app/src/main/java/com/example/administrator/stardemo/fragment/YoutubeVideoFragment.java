package com.example.administrator.stardemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.stardemo.R;
import com.example.administrator.stardemo.activity.ScannerActivity;
import com.example.administrator.stardemo.base.BaseFragment;
import com.pierfrancescosoffritti.youtubeplayer.AbstractYouTubeListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;

/**
 * Created by Administrator on 06/23/2017.
 */

public class YoutubeVideoFragment extends BaseFragment {

    private ScannerActivity activity;
    private Context context;

    private YouTubePlayerView youTubePlayerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        return inflater.inflate(R.layout.fragment_youtube_video, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        activity = (ScannerActivity) this.getActivity();

        youTubePlayerView = (YouTubePlayerView) view.findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(new AbstractYouTubeListener() {
            @Override
            public void onReady() {
                youTubePlayerView.loadVideo("6JYIGclVQdw", 0);
            }
        }, true);
    }
}

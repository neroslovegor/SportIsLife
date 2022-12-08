package com.example.sportislife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sportislife.Config;
import com.example.sportislife.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubePlayer extends YouTubeBaseActivity implements com.google.android.youtube.player.YouTubePlayer.OnInitializedListener, com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener, com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener {

    Intent intent;
    String videoId;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.youtube_player);
        intent = getIntent();
        videoId = intent.getStringExtra("VIDEO_ID");
        youTubePlayerView = findViewById(R.id.youtubePlayer);
        youTubePlayerView.initialize(Config.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(com.google.android.youtube.player.YouTubePlayer.Provider provider, com.google.android.youtube.player.YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);

        if (!b) {
            youTubePlayer.cueVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(com.google.android.youtube.player.YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(com.google.android.youtube.player.YouTubePlayer.ErrorReason errorReason) {

    }
}

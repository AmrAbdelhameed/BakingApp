package com.example.amr.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.amr.bakingapp.Models.BakingResponse;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BakingStepDetailsActivity extends AppCompatActivity {

    int position, stepsBeanSize;
    String videoURL, stepsBean;
    boolean landscapeMood;
    Button buttonNext, buttonPrevious;
    Gson gson;
    List<BakingResponse.StepsBean> stepsBeen;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private BandwidthMeter bandwidthMeter;
    private boolean shouldAutoPlay;
    private int currentWindow;
    private long playbackPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_step_details);

        setTitle("Steps");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonPrevious = (Button) findViewById(R.id.buttonPrevious);

        landscapeMood = getResources().getBoolean(R.bool.landscapeMood);

        Intent sentIntent = getIntent();
        Bundle sentBundle = sentIntent.getExtras();

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
            videoURL = savedInstanceState.getString("videoURL");
            stepsBean = savedInstanceState.getString("stepsBean");
            stepsBeanSize = savedInstanceState.getInt("stepsBeanSize");

            shouldAutoPlay = savedInstanceState.getBoolean("shouldAutoPlay");
            currentWindow = savedInstanceState.getInt("currentWindow");
            playbackPosition = savedInstanceState.getLong("playbackPosition");
        } else {
            position = sentBundle.getInt("position");
            videoURL = sentBundle.getString("videoURL");
            stepsBean = sentBundle.getString("stepsBean");
            stepsBeanSize = sentBundle.getInt("stepsBeanSize");
        }

        gson = new Gson();
        Type type = new TypeToken<List<BakingResponse.StepsBean>>() {
        }.getType();
        stepsBeen = gson.fromJson(stepsBean, type);

        if (!landscapeMood) {

            UpdateFragment(position);

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = position + 1;
                    UpdateFragment(position);
                }
            });

            buttonPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = position - 1;
                    UpdateFragment(position);
                }
            });
        }
    }

    public void UpdateFragment(int pos) {
        BakingStepDetailsFragment mDetailsFragment = new BakingStepDetailsFragment();

        Bundle sentBundle = new Bundle();
        sentBundle.putString("videoURL", stepsBeen.get(pos).getVideoURL());
        sentBundle.putString("Description", stepsBeen.get(pos).getDescription());
        sentBundle.putString("thumbnailURL", stepsBeen.get(pos).getThumbnailURL());

        sentBundle.putBoolean("shouldAutoPlay", shouldAutoPlay);
        sentBundle.putInt("currentWindow", currentWindow);
        sentBundle.putLong("playbackPosition", playbackPosition);

        mDetailsFragment.setArguments(sentBundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.flDetails, mDetailsFragment, "").commit();

        shouldAutoPlay = !shouldAutoPlay;
        currentWindow = 0;
        playbackPosition = 0;

        CheckVisibility_Next_Previous(pos);
    }

    public void CheckVisibility_Next_Previous(int pos) {
        if (pos == 0)
            buttonPrevious.setVisibility(View.GONE);
        else if (pos == stepsBeanSize - 1)
            buttonNext.setVisibility(View.GONE);
        else {
            buttonPrevious.setVisibility(View.VISIBLE);
            buttonNext.setVisibility(View.VISIBLE);
        }
    }

    private void initializePlayer() {

        if (landscapeMood) {
            simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);

            shouldAutoPlay = true;
            bandwidthMeter = new DefaultBandwidthMeter();
            mediaDataSourceFactory = new DefaultDataSourceFactory(BakingStepDetailsActivity.this, Util.getUserAgent(BakingStepDetailsActivity.this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
            window = new Timeline.Window();

            simpleExoPlayerView.requestFocus();

            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);

            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(BakingStepDetailsActivity.this, trackSelector);

            simpleExoPlayerView.setPlayer(player);

            player.setPlayWhenReady(shouldAutoPlay);
            player.seekTo(currentWindow, playbackPosition);

            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL),
                    mediaDataSourceFactory, extractorsFactory, null, null);

            player.prepare(mediaSource, true, false);
        }
    }

    private void releasePlayer() {
        if (landscapeMood) {
            if (player != null) {
                playbackPosition = player.getCurrentPosition();
                currentWindow = player.getCurrentWindowIndex();
                shouldAutoPlay = player.getPlayWhenReady();
                player.release();
                player = null;
                trackSelector = null;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
        outState.putString("videoURL", stepsBeen.get(position).getVideoURL());
        outState.putString("Description", stepsBeen.get(position).getDescription());
        outState.putString("thumbnailURL", stepsBeen.get(position).getThumbnailURL());
        outState.putString("stepsBean", stepsBean);
        outState.putInt("stepsBeanSize", stepsBeanSize);

        outState.putBoolean("shouldAutoPlay", shouldAutoPlay);
        outState.putInt("currentWindow", currentWindow);
        outState.putLong("playbackPosition", playbackPosition);
    }
}

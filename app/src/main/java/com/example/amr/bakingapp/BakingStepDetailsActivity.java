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
    boolean landscapeMood, tabletMood;
    Button buttonNext, buttonPrevious;
    Gson gson;
    List<BakingResponse.StepsBean> stepsBeen;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_step_details);

        setTitle("Steps");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent sentIntent = getIntent();
        Bundle sentBundle = sentIntent.getExtras();

        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
            videoURL = savedInstanceState.getString("videoURL");
        } else {
            position = sentBundle.getInt("position");
            videoURL = sentBundle.getString("videoURL");
        }

        stepsBean = sentBundle.getString("stepsBean");
        stepsBeanSize = sentBundle.getInt("stepsBeanSize");

        gson = new Gson();
        Type type = new TypeToken<List<BakingResponse.StepsBean>>() {
        }.getType();
        stepsBeen = gson.fromJson(stepsBean, type);

        landscapeMood = getResources().getBoolean(R.bool.landscapeMood);
        tabletMood = getResources().getBoolean(R.bool.tabletMood);

        if (landscapeMood && !tabletMood) {
            shouldAutoPlay = true;
            bandwidthMeter = new DefaultBandwidthMeter();
            mediaDataSourceFactory = new DefaultDataSourceFactory(BakingStepDetailsActivity.this, Util.getUserAgent(BakingStepDetailsActivity.this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
            window = new Timeline.Window();
        } else {
            if (savedInstanceState == null) {
                BakingStepDetailsFragment mDetailsFragment = new BakingStepDetailsFragment();
                mDetailsFragment.setArguments(sentBundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.flDetails, mDetailsFragment, "").commit();
            }
            buttonNext = (Button) findViewById(R.id.buttonNext);
            buttonPrevious = (Button) findViewById(R.id.buttonPrevious);
            CheckVisibility_Next_Previous(position);

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
        Bundle sentBundle2 = new Bundle();
        sentBundle2.putString("videoURL", stepsBeen.get(pos).getVideoURL());
        sentBundle2.putString("Description", stepsBeen.get(pos).getDescription());
        sentBundle2.putString("thumbnailURL", stepsBeen.get(pos).getThumbnailURL());

        mDetailsFragment.setArguments(sentBundle2);
        getSupportFragmentManager().beginTransaction().replace(R.id.flDetails, mDetailsFragment, "").commit();
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
        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);

        simpleExoPlayerView.requestFocus();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(BakingStepDetailsActivity.this, trackSelector);

        simpleExoPlayerView.setPlayer(player);

        player.setPlayWhenReady(shouldAutoPlay);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL),
                mediaDataSourceFactory, extractorsFactory, null, null);

        player.prepare(mediaSource);
    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
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
    }
}

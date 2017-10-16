package com.example.amr.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.amr.bakingapp.Models.TabletMood;

public class BakingStepsActivity extends AppCompatActivity implements TabletMood {
    boolean mIsTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_steps);

        setTitle("Ingredients");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent sentIntent = getIntent();
        Bundle sentBundle = sentIntent.getExtras();

        BakingStepsFragment mMainFragment = new BakingStepsFragment();

        mMainFragment.setNameListener(BakingStepsActivity.this);
        mMainFragment.setArguments(sentBundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.flMain, mMainFragment, "").commit();

        if (null != findViewById(R.id.flDetails)) {
            mIsTwoPane = true;
        }
    }

    @Override
    public void setSelectedBaking(String videoURL, String Description, String stepsBean, int position, int stepsBeanSize) {

        if (!mIsTwoPane) {
            Intent i = new Intent(this, BakingStepDetailsActivity.class);
            i.putExtra("videoURL", videoURL);
            i.putExtra("Description", Description);
            i.putExtra("stepsBean", stepsBean);
            i.putExtra("position", position);
            i.putExtra("stepsBeanSize", stepsBeanSize);
            startActivity(i);
        } else {
            BakingStepDetailsFragment mDetailsFragment = new BakingStepDetailsFragment();
            Bundle extras = new Bundle();
            extras.putString("videoURL", videoURL);
            extras.putString("Description", Description);
            extras.putString("stepsBean", stepsBean);
            extras.putInt("position", position);
            extras.putInt("stepsBeanSize", stepsBeanSize);
            mDetailsFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().replace(R.id.flDetails, mDetailsFragment, "").commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

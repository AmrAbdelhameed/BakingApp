package com.example.amr.bakingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.amr.bakingapp.Adapters.MainAdapter;
import com.example.amr.bakingapp.Models.BakingResponse;
import com.example.amr.bakingapp.RetrofitAPIs.APIService;
import com.example.amr.bakingapp.RetrofitAPIs.ApiUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIService mAPIService;
    Gson gson;
    ProgressDialog pdialog;
    RecyclerView recycler_view;
    MainAdapter adapter;
    String BakingData = "";
    boolean tabletMood = false;
    int noItems = 1;
    List<BakingResponse> bakingResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);

        tabletMood = getResources().getBoolean(R.bool.tabletMood);
        if (tabletMood)
            noItems = 2;
        else
            noItems = 1;

        gson = new Gson();
        mAPIService = ApiUtils.getAPIService();

        pdialog = new ProgressDialog(MainActivity.this);
        pdialog.setIndeterminate(true);
        pdialog.setCancelable(false);
        pdialog.setMessage("Loading. Please wait...");

        getBakingGET();
    }

    public void getBakingGET() {
        pdialog.show();
        mAPIService.getBaking().enqueue(new Callback<List<BakingResponse>>() {

            @Override
            public void onResponse(Call<List<BakingResponse>> call, Response<List<BakingResponse>> response) {

                if (response.isSuccessful()) {

                    BakingData = gson.toJson(response.body());
                    SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("BakingData", BakingData);
                    editor.apply();

                    adapter = new MainAdapter(MainActivity.this, response.body());
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, noItems);
                    recycler_view.setLayoutManager(mLayoutManager);
                    recycler_view.setItemAnimator(new DefaultItemAnimator());
                    recycler_view.setAdapter(adapter);

                    recycler_view.addOnItemTouchListener(
                            new RecyclerItemClickListener(MainActivity.this, recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent i = new Intent(MainActivity.this, BakingStepsActivity.class);
                                    i.putExtra("bakingIndex", position);
                                    startActivity(i);
                                }

                                @Override
                                public void onLongItemClick(View view, int position) {
                                    // do whatever
                                }
                            })
                    );
                }
                pdialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<BakingResponse>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
                BakingData = sharedPreferences.getString("BakingData", "");

                Type type = new TypeToken<List<BakingResponse>>() {
                }.getType();
                bakingResponses = gson.fromJson(BakingData, type);

                adapter = new MainAdapter(MainActivity.this, bakingResponses);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, noItems);
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(adapter);

                recycler_view.addOnItemTouchListener(
                        new RecyclerItemClickListener(MainActivity.this, recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent i = new Intent(MainActivity.this, BakingStepsActivity.class);
                                i.putExtra("bakingIndex", position);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );

                pdialog.dismiss();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }
}

package com.example.amr.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amr.bakingapp.Adapters.StepsAdapter;
import com.example.amr.bakingapp.Models.BakingResponse;
import com.example.amr.bakingapp.Models.TabletMood;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BakingStepsFragment extends Fragment {

    String BakingData;
    int bakingIndex;
    Gson gson, gson2;
    List<BakingResponse> bakingResponses;
    TextView textingredients;
    BakingResponse bakingResponse;
    RecyclerView recycler_view;
    StepsAdapter adapter;
    private TabletMood mListener;

    public BakingStepsFragment() {
    }

    void setNameListener(TabletMood tabletMood) {
        this.mListener = tabletMood;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_baking_steps, container, false);

        recycler_view = (RecyclerView) v.findViewById(R.id.lvNames);
        textingredients = (TextView) v.findViewById(R.id.textingredients);
        textingredients.setText("Ingredients: \n");

        Bundle sentBundle = getArguments();
        bakingIndex = sentBundle.getInt("bakingIndex");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences_name", Context.MODE_PRIVATE);
        BakingData = sharedPreferences.getString("BakingData", "");

        gson = new Gson();
        gson2 = new Gson();
        Type type = new TypeToken<List<BakingResponse>>() {
        }.getType();
        bakingResponses = gson.fromJson(BakingData, type);

        bakingResponse = bakingResponses.get(bakingIndex);

        for (int i = 0; i < bakingResponse.getIngredients().size(); i++) {
            textingredients.append(bakingResponse.getIngredients().get(i).getIngredient() + " : " + bakingResponse.getIngredients().get(i).getQuantity() + " " + bakingResponse.getIngredients().get(i).getMeasure() + "\n");
        }

        adapter = new StepsAdapter(getActivity(), bakingResponse.getSteps());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(adapter);

        recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recycler_view, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String VideoURL = bakingResponse.getSteps().get(position).getVideoURL();
                        String Description = bakingResponse.getSteps().get(position).getDescription();
                        String stepsBean = gson2.toJson(bakingResponse.getSteps());
                        int stepsBeanSize = bakingResponse.getSteps().size();
                        mListener.setSelectedBaking(VideoURL, Description, stepsBean, position, stepsBeanSize);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        return v;
    }
}

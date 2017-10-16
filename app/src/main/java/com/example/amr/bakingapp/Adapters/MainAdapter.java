package com.example.amr.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amr.bakingapp.Models.BakingResponse;
import com.example.amr.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by amr on 06/10/17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context mContext;
    private List<BakingResponse> bakingResponses;

    public MainAdapter(Context mContext, List<BakingResponse> bakingResponses) {
        this.mContext = mContext;
        this.bakingResponses = bakingResponses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_in_activity_main, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        BakingResponse bakingResponse = bakingResponses.get(position);
        holder.name.setText(bakingResponse.getName());
        holder.servings.setText(String.valueOf(bakingResponse.getServings()) + " servings");

        String imag = bakingResponse.getImage();
        if (!imag.isEmpty())
            Picasso.with(mContext).load(imag).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return bakingResponses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, servings;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            servings = (TextView) view.findViewById(R.id.servings);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }
}
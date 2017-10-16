package com.example.amr.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amr.bakingapp.Models.BakingResponse;
import com.example.amr.bakingapp.R;

import java.util.List;

/**
 * Created by amr on 12/10/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    private Context mContext;
    private List<BakingResponse.StepsBean> stepsBeen;

    public StepsAdapter(Context mContext, List<BakingResponse.StepsBean> bakingResponses) {
        this.mContext = mContext;
        this.stepsBeen = bakingResponses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_in_steps, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        BakingResponse.StepsBean bakingResponse = stepsBeen.get(position);
        holder.Desc.setText(bakingResponse.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepsBeen.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Desc;

        public MyViewHolder(View view) {
            super(view);
            Desc = (TextView) view.findViewById(R.id.Desc);
        }
    }
}
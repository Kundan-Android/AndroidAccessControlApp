package com.widas.demo_ac.MainScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.widas.demo_ac.MainScreen.model.AssetsListData;
import com.widas.demo_ac.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AssetsListAdapter extends RecyclerView.Adapter<AssetsListAdapter.ViewHolder> {

    ArrayList<AssetsListData> assetsListData;
    Context context;
    RecyclerViewClickListener mRecyclerViewClickListener;

    public AssetsListAdapter(ArrayList<AssetsListData> assetsListData, Context context) {
        this.assetsListData = assetsListData;
        this.context = context;
        mRecyclerViewClickListener = (RecyclerViewClickListener) context;
    }

    @NonNull
    @Override
    public AssetsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.devicelist_row, parent, false);
        ViewHolder vh = new  ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.device_name.setText(assetsListData.get(position).getResource_name());
        holder.device_type.setText(assetsListData.get(position).getResource_type());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerViewClickListener.onClick(view,position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return assetsListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView device_name, device_type;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            device_name = itemView.findViewById(R.id.device_name);
            device_type = itemView.findViewById(R.id.device_type);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

 interface RecyclerViewClickListener {
        void onClick(View View, int position);
 }
}

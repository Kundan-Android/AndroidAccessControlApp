package com.widas.demo_ac.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.widas.demo_ac.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by widasrnarayanan on 13/3/18.
 */

public class ItemviewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView email;
    TextView time;
    TextView count;
    ImageView img_provider;
    ImageView img_copy_cont;
    ImageView imgarrow;
    ProgressBar progressBar;
    RelativeLayout rl_img,rl_time;

    ItemviewHolder(View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.home_tv_name);
        this.email = itemView.findViewById(R.id.home_tv_email);
        this.time = itemView.findViewById(R.id.home_tv_time);
        this.img_provider = itemView.findViewById(R.id.img_provider);
        this.img_copy_cont = itemView.findViewById(R.id.img_copy_cont);
        this.imgarrow = itemView.findViewById(R.id.imgarrow);
        this.progressBar = itemView.findViewById(R.id.progress_state);
        this.count = itemView.findViewById(R.id.lbl_count);
        this.rl_img = itemView.findViewById(R.id.rl_img);
        this.rl_time = itemView.findViewById(R.id.rl_time);
    }
}

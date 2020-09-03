package com.widas.demo_ac.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseDataEntity;
import com.widas.demo_ac.R;
import com.widas.demo_ac.helper.AuthenticationType;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MFAAdapter extends RecyclerView.Adapter<ItemviewHolder> {
    private List<MFAListResponseDataEntity> list;
    private RecyclerViewClickListener clickListener;

    public MFAAdapter(List<MFAListResponseDataEntity> list, RecyclerViewClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ItemviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mfa_listitem, parent, false);
        return new ItemviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemviewHolder holder, int position) {
        MFAListResponseDataEntity entity = list.get(position);
        holder.name.setText("kundan kishore");
        holder.email.setText("kundan.kishore.widas.in");
        holder.rl_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view, position);
            }
        });
        //Get Icon
        if (entity.getVerificationType().equalsIgnoreCase(AuthenticationType.touch)) {
            holder.img_provider.setImageResource(R.drawable.v_fingerprint);
        } else if (entity.getVerificationType().equalsIgnoreCase(AuthenticationType.pattern)) {
            holder.img_provider.setImageResource(R.drawable.vd_pattern);
        } else if (entity.getVerificationType().equalsIgnoreCase(AuthenticationType.push)) {
            holder.img_provider.setImageResource(R.drawable.vd_push);
        } else if (entity.getVerificationType().equalsIgnoreCase(AuthenticationType.face)) {
            holder.img_provider.setImageResource(R.drawable.vd_face);
        } else if (entity.getVerificationType().equalsIgnoreCase(AuthenticationType.voice)) {
            holder.img_provider.setImageResource(R.drawable.vd_voice);
        } else if (entity.getVerificationType().equalsIgnoreCase(AuthenticationType.fido)) {
            holder.img_provider.setImageResource(R.drawable.vd_nfc);
        } else
            holder.img_provider.setImageResource(R.drawable.user);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }
}

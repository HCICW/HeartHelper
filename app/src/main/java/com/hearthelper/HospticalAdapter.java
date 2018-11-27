package com.hearthelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HospticalAdapter extends RecyclerView.Adapter<HospticalAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    private List<HospitalData.ResultData.InfoData> list;

    public HospticalAdapter(@NonNull Context context) {

    }

    HospticalAdapter(@NonNull Context context, List<HospitalData.ResultData.InfoData> dataList) {
        inflater = LayoutInflater.from(context);
        list = dataList;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.item_hosptical, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            if (i >= 0 && i < getItemCount()) {

                HospitalData.ResultData.InfoData data = list.get(i);
                viewHolder.mTv1.setText(data.getLocation());
                viewHolder.mTv2.setText(data.getLocationName());
                viewHolder.mTv3.setText(data.getAddressLine());
                viewHolder.mTv4.setText(data.getPostcode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv1;
        private TextView mTv2;
        private TextView mTv3;
        private TextView mTv4;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv1 = itemView.findViewById(R.id.tv1);
            mTv2 = itemView.findViewById(R.id.tv2);
            mTv3 = itemView.findViewById(R.id.tv3);
            mTv4 = itemView.findViewById(R.id.tv4);
        }
    }
}

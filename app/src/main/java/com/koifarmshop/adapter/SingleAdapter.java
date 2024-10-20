package com.koifarmshop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.koifarmshop.R;
import com.koifarmshop.model.NewKoi;

import java.text.DecimalFormat;
import java.util.List;

public class SingleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<NewKoi> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public SingleAdapter(Context context, List<NewKoi> array) {
        this.context = context;
        this.array = array;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            NewKoi koi = array.get(position);

            myViewHolder.tenCa.setText(koi.getTenCa());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.gia.setText("Giá: " + decimalFormat.format(Double.parseDouble(koi.getGia())) + " vnd");
            myViewHolder.mota.setText(koi.getMota());
            myViewHolder.id.setText(koi.getId() + "");

            Glide.with(context).load(koi.getHinhAnh()).into(myViewHolder.hinhanh);
        } else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single, parent, false);
//        return new MyViewHolder(view);
//    }
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        NewKoi koi = array.get(position);
//
//        holder.tenCa.setText(koi.getTenCa());
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.gia.setText("Giá: " + decimalFormat.format(Double.parseDouble(koi.getGia())) + " vnd");
//        holder.mota.setText(koi.getMota());
//        Glide.with(context).load(koi.getHinhAnh()).into(holder.hinhanh);
//
//    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tenCa, gia, mota, id;
        ImageView hinhanh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tenCa = itemView.findViewById(R.id.itemsing_ten);
            gia = itemView.findViewById(R.id.itemsing_gia);
            mota = itemView.findViewById(R.id.itemsing_mota);
            id = itemView.findViewById(R.id.itemsing_id);
            hinhanh = itemView.findViewById(R.id.itemsing_image);
        }
    }

}

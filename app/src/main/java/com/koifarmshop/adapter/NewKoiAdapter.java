package com.koifarmshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.koifarmshop.R;
import com.koifarmshop.model.NewKoi;

import java.text.DecimalFormat;
import java.util.List;

public class NewKoiAdapter extends RecyclerView.Adapter<NewKoiAdapter.MyViewHolder> {
    Context context;
    List<NewKoi> array;

    public NewKoiAdapter(Context context, List<NewKoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_koi_moi, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewKoi newKoi = array.get(position);

        holder.txtten.setText(newKoi.getTenCa());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá: " + decimalFormat.format(Double.parseDouble(newKoi.getGia())) + " vnd" );
        Glide.with(context).load(newKoi.getHinhAnh()).into(holder.imghinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtgia, txtten;
        ImageView imghinhanh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgia = itemView.findViewById(R.id.itemca_gia);
            txtten = itemView.findViewById(R.id.itemca_ten);
            imghinhanh = itemView.findViewById(R.id.itemca_image);
        }
    }


}

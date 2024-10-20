package com.koifarmshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.koifarmshop.Interface.IImageClickListenner;
import com.koifarmshop.R;
import com.koifarmshop.model.Cart;
import com.koifarmshop.model.EventBus.SumEvent;
import com.koifarmshop.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.item_giohang_tenca.setText(cart.getTenCa());
        holder.item_giohang_soluong.setText(cart.getSoluong() + " ");
        Glide.with(context).load(cart.getHinhCa()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText("Giá: " + decimalFormat.format((cart.getGiaCa())));
        long gia = cart.getSoluong() * cart.getGiaCa();
        holder.item_giohang_gia2.setText(decimalFormat.format(gia));
        holder.setListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giaTri) {
                Log.d("CheckClickCongTru", "onImageClick: " + pos + "..." + giaTri);
                if (giaTri == 1) {
                    if (cartList.get(pos).getSoluong() > 1) {
                        int newQuantity = cartList.get(pos).getSoluong() - 1;
                        cartList.get(pos).setSoluong(newQuantity);

                        holder.item_giohang_soluong.setText(cartList.get(pos).getSoluong() + " ");
                        long gia = cartList.get(pos).getSoluong() * cartList.get(pos).getGiaCa();
                        holder.item_giohang_gia2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new SumEvent());

                    } else if (cartList.get(pos).getSoluong() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng không");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Utils.cartArray.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new SumEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                } else if (giaTri == 2) {
                    if (cartList.get(pos).getSoluong() < 11) {
                        int newQuantity = cartList.get(pos).getSoluong() + 1;
                        cartList.get(pos).setSoluong(newQuantity);
                    }
                    holder.item_giohang_soluong.setText(cartList.get(pos).getSoluong() + " ");
                    long gia = cartList.get(pos).getSoluong() * cartList.get(pos).getGiaCa();
                    holder.item_giohang_gia2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new SumEvent());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, imgTru, imgCong;
        TextView item_giohang_tenca, item_giohang_gia, item_giohang_soluong, item_giohang_gia2;
        IImageClickListenner listenner;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tenca = itemView.findViewById(R.id.item_giohang_tenca);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_gia2 = itemView.findViewById(R.id.item_giohang_gia2);
            imgTru = itemView.findViewById(R.id.item_giohang_tru);
            imgCong = itemView.findViewById(R.id.item_giohang_cong);

            //event click
            imgCong.setOnClickListener(this);
            imgTru.setOnClickListener(this);
        }

        public void setListenner(IImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == imgTru) {
                listenner.onImageClick(view, getAdapterPosition(), 1);
            } else if (view == imgCong) {
                listenner.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }
}

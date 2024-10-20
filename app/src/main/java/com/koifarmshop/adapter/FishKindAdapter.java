package com.koifarmshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koifarmshop.R;
import com.koifarmshop.model.FishKind;

import java.util.List;

public class FishKindAdapter extends BaseAdapter {
    List<FishKind> array;
    Context context;

    public FishKindAdapter(Context context, List<FishKind> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder {
        TextView textTenCa;
        ImageView imgHinhAnh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_cakoi, null);

            viewHolder.textTenCa = view.findViewById(R.id.item_tenca);
            viewHolder.imgHinhAnh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textTenCa.setText(array.get(i).getTenCa());
        Glide.with(context).load(array.get(i).getHinhAnh()).into(viewHolder.imgHinhAnh);
        return view;
    }
}

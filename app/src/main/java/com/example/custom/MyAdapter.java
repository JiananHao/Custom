package com.example.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private ItemClickListener mItemClickListener;
    private Context mContext;
    private int[] mColors;

    public MyAdapter(Context context,int[] mColors) {
        super();
        this.mContext = context;
        this.mColors = mColors;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.mCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.setOnItemClickListener(holder.mCustomView,holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mColors.length;
    }
    //声明一个item点击监听接口
    public interface ItemClickListener {
        void setOnItemClickListener(View view,int position);
    }
    //注入item监听方法
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CustomView mCustomView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCustomView = itemView.findViewById(R.id.cv);
        }
    }
}


package com.yc.cn.ycgallery;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yc.cn.ycgallerylib.zoom.view.ZoomImageView;

import java.util.ArrayList;
import java.util.List;

public class SnapAdapter extends RecyclerView.Adapter <SnapAdapter.MyViewHolder>{


    private Context mContext;
    SnapAdapter(Context context){
        this.mContext =context;
    }

    private List<Integer> urlList = new ArrayList<>();
    public void setData(List<Integer> list) {
        urlList.clear();
        this.urlList = list;
    }

    public List<Integer> getData() {
        return urlList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_snap, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (urlList == null || urlList.isEmpty())
            return;
        Integer url = urlList.get(position%urlList.size());
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), url);
        holder.imageView.setImageBitmap(bitmap);

        //holder.imageView.setBackgroundResource(url);
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        ZoomImageView imageView;
        MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
        }

    }

    @Override
    public int getItemCount() {
        /*if (urlList.size() != 1) {
            Log.e("getItemCount","getItemCount---------");
            return Integer.MAX_VALUE; // 无限轮播
        } else {
            Log.e("getItemCount","getItemCount++++----");
            return urlList.size();
        }*/

        return urlList!=null ? urlList.size() : 0;
    }

}

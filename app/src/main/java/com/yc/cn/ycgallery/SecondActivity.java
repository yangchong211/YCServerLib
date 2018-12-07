package com.yc.cn.ycgallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yc.cn.ycgallerylib.zoom.view.ZoomImageView;
import com.yc.cn.ycgallerylib.zoom.listener.OnZoomClickListener;
import com.yc.cn.ycgallerylib.zoom.listener.OnZoomLongClickListener;


public class SecondActivity extends AppCompatActivity {

    private ZoomImageView imageView;
    private static final long ANIM_TIME = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView = findViewById(R.id.image);
        imageView.setOnZoomClickListener(new OnZoomClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnZoomLongClickListener(new OnZoomLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        imageView.setMaxScale(4);
        imageView.setImageResource(R.drawable.image1);
        //注意不要使用setBackground设置图片，它不支持缩放
        //imageView.setBackground();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imageView!=null){
            //重置所有状态，清空mask，停止所有手势，停止所有动画
            imageView.reset();
        }
    }
}

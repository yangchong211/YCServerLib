package com.yc.cn.ycgallery;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yc.cn.ycgallerylib.zoom.view.ZoomImageView;

public class ThirdActivity extends AppCompatActivity {

    private ZoomImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        imageView = findViewById(R.id.image);
        Uri parse = Uri.parse("file:///android_asset/yc.png");
        Picasso.with(this)
                .load(parse)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });


    }
}

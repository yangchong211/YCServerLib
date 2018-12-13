package com.yc.cn.ycgallery;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView tv_5;

    /**
     * 学习案例：
     * https://github.com/boycy815/PinchImageView
     * https://github.com/bm-x/PhotoView
     * https://github.com/ongakuer/PhotoDraweeView
	 https://github.com/panpf/sketch
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = findViewById(R.id.tv_1);
        tv_5 = findViewById(R.id.tv_5);
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_1:
                startActivity(new Intent(this, FirstActivity.class));
                break;
            case R.id.tv_2:
                Intent intent2 = new Intent(this, SecondActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                break;
            case R.id.tv_3:
                Intent intent3 = new Intent(this, ThirdActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent3, ActivityOptions.makeSceneTransitionAnimation(
                            MainActivity.this).toBundle());
                }else {
                    startActivity(intent3);
                }
                break;
            case R.id.tv_4:
                startActivity(new Intent(this, FourActivity.class));
                break;
            case R.id.tv_5:
                Intent intent5 = new Intent(this, FiveActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent5, ActivityOptions.makeSceneTransitionAnimation(
                            MainActivity.this, tv_5, "YangChong").toBundle());
                }
                break;
        }
    }


}

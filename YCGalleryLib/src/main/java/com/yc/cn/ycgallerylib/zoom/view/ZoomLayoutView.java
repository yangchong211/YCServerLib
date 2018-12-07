package com.yc.cn.ycgallerylib.zoom.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yc.cn.ycgallerylib.R;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/05/30
 *     desc  : 一款支持缩放与滑动处理的图片控件，这个是支持加载大图前显示loading控件
 *     revise:
 * </pre>
 */
public class ZoomLayoutView extends FrameLayout {

    private ZoomImageView imageView;
    private LinearLayout llLoading;

    public ZoomLayoutView(Context context) {
        this(context,null);
    }

    public ZoomLayoutView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZoomLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_zoom_load, this, true);
        imageView = findViewById(R.id.imageView);
        llLoading = findViewById(R.id.ll_loading);
    }

    public void setZoomViewVisibility(boolean visible){
        if (visible){
            imageView.setVisibility(View.VISIBLE);
            llLoading.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.GONE);
            llLoading.setVisibility(View.VISIBLE);
        }
    }

    public void setLoadingVisibility(boolean visible){
        if (visible){
            imageView.setVisibility(View.GONE);
            llLoading.setVisibility(View.VISIBLE);
        }else {
            imageView.setVisibility(View.VISIBLE);
            llLoading.setVisibility(View.GONE);
        }
    }

    public ZoomImageView getImageView(){
        return imageView;
    }

}

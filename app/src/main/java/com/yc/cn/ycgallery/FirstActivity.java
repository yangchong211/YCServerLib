package com.yc.cn.ycgallery;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.yc.cn.ycgallerylib.gallery.GalleryImageView;
import java.util.ArrayList;
import java.util.Arrays;

public class FirstActivity extends FragmentActivity {

    private GalleryImageView scrollGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);



        scrollGalleryView = findViewById(R.id.scroll_gallery_view);
        scrollGalleryView
                //设置viewPager底部缩略图大小尺寸
                .setThumbnailSize(200)
                //设置是否支持缩放
                .setZoom(true)
                //设置是否隐藏底部缩略图
                .hideThumbnails(false)
                //设置FragmentManager
                .setFragmentManager(getSupportFragmentManager())
                //添加滑动事件，也可以不用添加
                .addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        scrollGalleryView.setCurrentItem(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                })
                //添加单张图片
                .addBitmap(toBitmap(R.drawable.image1))
                .addBitmap(toBitmap(R.drawable.image2))
                .addBitmap(toBitmap(R.drawable.image3))
                .addBitmap(toBitmap(R.drawable.image4))
                .addBitmap(toBitmap(R.drawable.image5))
                .addBitmap(toBitmap(R.drawable.image6))
                .addBitmap(toBitmap(R.drawable.image7));
        //获取当前索引处
        int currentItem = scrollGalleryView.getCurrentItem();
        //获取控件Viewpager
        ViewPager viewPager = scrollGalleryView.getViewPager();
    }

    private Bitmap toBitmap(int image) {
        return ((BitmapDrawable) getResources().getDrawable(image)).getBitmap();
    }
}

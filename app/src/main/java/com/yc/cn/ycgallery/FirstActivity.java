package com.yc.cn.ycgallery;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.yc.cn.ycgallery.animations.EnterScreenAnimations;
import com.yc.cn.ycgallery.animations.ExitScreenAnimations;
import com.yc.gallerylib.gallery.GalleryImageView;

public class FirstActivity extends FragmentActivity {

    private GalleryImageView scrollGalleryView;
    private ImageView mTransitionImage;
    private EnterScreenAnimations mEnterScreenAnimations;
    private ExitScreenAnimations mExitScreenAnimations;
    private TextView tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        scrollGalleryView = findViewById(R.id.scroll_gallery_view);
        final RelativeLayout mainContainer = findViewById(R.id.main_container);
        tag = findViewById(R.id.tag);
        FrameLayout androidContent = getWindow().getDecorView().findViewById(android.R.id.content);
        mTransitionImage = new ImageView(this);
        androidContent.addView(mTransitionImage);

        mEnterScreenAnimations = new EnterScreenAnimations(mTransitionImage, scrollGalleryView, mainContainer);
        mExitScreenAnimations = new ExitScreenAnimations(mTransitionImage, scrollGalleryView, mainContainer);


        final int[] finalLocationOnTheScreen = new int[2];
        scrollGalleryView.getLocationOnScreen(finalLocationOnTheScreen);
        init();
        mEnterScreenAnimations.playEnteringAnimation(
                finalLocationOnTheScreen[0], // left
                finalLocationOnTheScreen[1], // top
                scrollGalleryView.getWidth(),
                scrollGalleryView.getHeight());
    }

    @SuppressLint("SetTextI18n")
    private void init(){
        tag.setText(1+  "/" + 16);
        scrollGalleryView
                //设置viewPager底部缩略图大小尺寸
                .setThumbnailSize(200)
                //设置是否支持缩放
                .setZoom(true)
                //设置缩放的倍数
                .setZoomSize(3)
                //设置是否隐藏底部缩略图
                .hideThumbnails(false)
                //设置FragmentManager
                .setFragmentManager(getSupportFragmentManager())
                //添加滑动事件，也可以不用添加
                .addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPageSelected(int position) {
                        scrollGalleryView.setCurrentItem(position);
                        tag.setText((position + 1) + "/" + 16);
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
                .addBitmap(toBitmap(R.drawable.image7))
                .addBitmap(toBitmap(R.drawable.beauty1))
                .addBitmap(toBitmap(R.drawable.beauty2))
                .addBitmap(toBitmap(R.drawable.beauty3))
                .addBitmap(toBitmap(R.drawable.beauty4))
                .addBitmap(toBitmap(R.drawable.beauty5))
                .addBitmap(toBitmap(R.drawable.beauty6))
                .addBitmap(toBitmap(R.drawable.beauty7))
                .addBitmap(toBitmap(R.drawable.beauty8))
                .addBitmap(toBitmap(R.drawable.beauty9));
    }

    private Bitmap toBitmap(int image) {
        return ((BitmapDrawable) getResources().getDrawable(image)).getBitmap();
    }
}

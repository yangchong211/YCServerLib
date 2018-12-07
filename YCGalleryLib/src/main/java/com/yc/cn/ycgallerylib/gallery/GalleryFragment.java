package com.yc.cn.ycgallerylib.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.cn.ycgallerylib.R;
import com.yc.cn.ycgallerylib.zoom.view.ZoomImageView;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/05/30
 *     desc  : 支持滑动viewpager图片浏览控件，使用轻量级fragment
 *     revise:
 * </pre>
 */
public class GalleryFragment extends Fragment {

    private Bitmap mBitmap;
    private CustomViewPager viewPager;
    private ZoomImageView backgroundImage;
    private FragmentActivity activity;
    private static final String IMAGE = "image";
    public static final String ZOOM = "zoom";
    private static final String IS_LOCKED = "isLocked";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(IS_LOCKED, viewPager.isLocked());
        }
        if (isBackgroundImageActive()) {
            outState.putParcelable(IMAGE, ((BitmapDrawable) backgroundImage.getDrawable()).getBitmap());
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_image_gallery, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backgroundImage = view.findViewById(R.id.backgroundImage);
        viewPager = activity.findViewById(R.id.viewPager);
        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(IS_LOCKED, false);
            viewPager.setLocked(isLocked);
            if (savedInstanceState.containsKey(IMAGE)) {
                backgroundImage.setImageBitmap((Bitmap) savedInstanceState.getParcelable(IMAGE));
            }
            createViewAttache(savedInstanceState);
        }
        loadImageToView();
    }


    private void loadImageToView() {
        backgroundImage.setImageBitmap(mBitmap);
        //注意不要设置setBackground
        //backgroundImage.setBitmap(mBitmap);
        //backgroundImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void createViewAttache(Bundle savedInstanceState) {
        if (savedInstanceState.getBoolean(ZOOM)) {
            backgroundImage.setMaxScale(2);
        } else {
            backgroundImage.setMaxScale(1);
        }
    }

    private boolean isViewPagerActive() {
        return viewPager != null;
    }

    private boolean isBackgroundImageActive() {
        return backgroundImage != null && backgroundImage.getDrawable() != null;
    }

}

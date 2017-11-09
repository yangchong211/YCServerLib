package com.yc.cn.ycgallerylib.gallery;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yc.cn.ycgallerylib.R;
import com.yc.cn.ycgallerylib.gallery.loader.MediaLoader;
import com.yc.cn.ycgallerylib.gallery.model.Constants;
import com.yc.cn.ycgallerylib.gallery.model.MediaInfo;

import uk.co.senab.photoview.PhotoViewAttacher;


public class ImageFragment extends Fragment {

    private MediaInfo mMediaInfo;

    private CustomViewPager viewPager;
    private ImageView backgroundImage;
    private PhotoViewAttacher photoViewAttacher;

    public void setMediaInfo(MediaInfo mediaInfo) {
        mMediaInfo = mediaInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_image_gallery, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backgroundImage = (ImageView) view.findViewById(R.id.backgroundImage);
        viewPager = (CustomViewPager) getActivity().findViewById(R.id.viewPager);
        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(Constants.IS_LOCKED, false);
            viewPager.setLocked(isLocked);
            if (savedInstanceState.containsKey(Constants.IMAGE)) {
                backgroundImage.setImageBitmap((Bitmap) savedInstanceState.getParcelable(Constants.IMAGE));
            }
            createViewAttache(savedInstanceState);
        }
        loadImageToView();
    }


    private void loadImageToView() {
        if (mMediaInfo != null) {
            mMediaInfo.getLoader().loadMedia(getActivity(), backgroundImage, new MediaLoader.SuccessCallback() {
                @Override
                public void onSuccess() {
                    createViewAttache(getArguments());
                }
            });
        }
    }

    private void createViewAttache(Bundle savedInstanceState) {
        if (savedInstanceState.getBoolean(Constants.ZOOM)) {
            photoViewAttacher = new PhotoViewAttacher(backgroundImage);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(Constants.IS_LOCKED, viewPager.isLocked());
        }
        if (isBackgroundImageActive()) {
            outState.putParcelable(Constants.IMAGE, ((BitmapDrawable) backgroundImage.getDrawable()).getBitmap());
        }
        outState.putBoolean(Constants.ZOOM, photoViewAttacher != null);
        super.onSaveInstanceState(outState);
    }

    private boolean isViewPagerActive() {
        return viewPager != null;
    }

    private boolean isBackgroundImageActive() {
        return backgroundImage != null && backgroundImage.getDrawable() != null;
    }
}

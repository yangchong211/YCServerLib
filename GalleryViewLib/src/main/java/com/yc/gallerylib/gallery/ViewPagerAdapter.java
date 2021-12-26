package com.yc.gallerylib.gallery;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/05/30
 *     desc  : 滑动viewpager的adapter
 *     revise:
 * </pre>
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Bitmap> mListOfMedia;
    private boolean isZoom = false;
    private int zoomSize;

    ViewPagerAdapter(FragmentManager fm, List<Bitmap> listOfMedia, boolean isZoom, int zoomSize) {
        super(fm);
        this.mListOfMedia = listOfMedia;
        this.isZoom = isZoom;
        this.zoomSize = zoomSize;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position < mListOfMedia.size()) {
            Bitmap bitmap = mListOfMedia.get(position);
            fragment = loadImageFragment(bitmap);
        }
        return fragment;
    }

    private Fragment loadImageFragment(Bitmap bitmap) {
        GalleryFragment fragment = new GalleryFragment();
        fragment.setBitmap(bitmap);
        Bundle bundle = new Bundle();
        bundle.putBoolean(GalleryFragment.ZOOM, isZoom);
        bundle.putInt(GalleryFragment.ZOOM_SIZE,zoomSize);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mListOfMedia!=null ? mListOfMedia.size() : 0;
    }
}

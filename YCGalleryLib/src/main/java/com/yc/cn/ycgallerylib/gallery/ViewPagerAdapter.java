package com.yc.cn.ycgallerylib.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yc.cn.ycgallerylib.gallery.model.Constants;
import com.yc.cn.ycgallerylib.gallery.model.MediaInfo;

import java.util.List;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<MediaInfo> mListOfMedia;
    private boolean isZoom = false;

    ViewPagerAdapter(FragmentManager fm, List<MediaInfo> listOfMedia,
                     boolean isZoom) {
        super(fm);
        this.mListOfMedia = listOfMedia;
        this.isZoom = isZoom;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position < mListOfMedia.size()) {
            MediaInfo mediaInfo = mListOfMedia.get(position);
            fragment = loadImageFragment(mediaInfo);
        }
        return fragment;
    }

    private Fragment loadImageFragment(MediaInfo mediaInfo) {
        ImageFragment fragment = new ImageFragment();
        fragment.setMediaInfo(mediaInfo);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ZOOM, isZoom);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mListOfMedia.size();
    }
}

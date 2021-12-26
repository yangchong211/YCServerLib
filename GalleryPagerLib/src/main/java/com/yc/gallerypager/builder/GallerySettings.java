package com.yc.gallerypager.builder;


import androidx.fragment.app.FragmentManager;

import com.yc.gallerypager.impl.SettingsBuilderImpl;
import com.yc.gallerypager.inter.ISettingsBuilder;

public class GallerySettings {
    private int thumbnailSize;
    private boolean isZoomEnabled;
    private FragmentManager fragmentManager;

    public int getThumbnailSize() {
        return thumbnailSize;
    }

    public void setThumbnailSize(int thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

    public boolean isZoomEnabled() {
        return isZoomEnabled;
    }

    public void setZoomEnabled(boolean zoomEnabled) {
        isZoomEnabled = zoomEnabled;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public static ISettingsBuilder from(FragmentManager fm) {
        ISettingsBuilder builder = new SettingsBuilderImpl();
        builder.withFragmentManager(fm);
        return builder;
    }
}

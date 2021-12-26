package com.yc.gallerypager.impl;

import androidx.fragment.app.FragmentManager;

import com.yc.gallerypager.builder.GallerySettings;
import com.yc.gallerypager.inter.ISettingsBuilder;

public class SettingsBuilderImpl implements ISettingsBuilder {
    private GallerySettings gallerySettings;

    public SettingsBuilderImpl() {
        this.gallerySettings = new GallerySettings();
    }

    @Override
    public ISettingsBuilder thumbnailSize(int thumbnailSize) {
        gallerySettings.setThumbnailSize(thumbnailSize);
        return this;
    }

    @Override
    public ISettingsBuilder enableZoom(boolean isZoomEnabled) {
        gallerySettings.setZoomEnabled(isZoomEnabled);
        return this;
    }

    @Override
    public ISettingsBuilder withFragmentManager(FragmentManager fragmentManager) {
        gallerySettings.setFragmentManager(fragmentManager);
        return this;
    }

    @Override
    public GallerySettings build() {
        return gallerySettings;
    }
}

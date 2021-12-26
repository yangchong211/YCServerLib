package com.yc.gallerypager.inter;

import androidx.fragment.app.FragmentManager;
import com.yc.gallerypager.builder.GallerySettings;

public interface ISettingsBuilder {
    ISettingsBuilder thumbnailSize(int thumbnailSize );
    ISettingsBuilder enableZoom(boolean isZoomEnabled);
    ISettingsBuilder withFragmentManager(FragmentManager fragmentManager);
    GallerySettings build();
}

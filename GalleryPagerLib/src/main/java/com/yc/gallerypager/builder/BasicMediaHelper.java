package com.yc.gallerypager.builder;

import com.yc.gallerypager.MediaInfo;
import com.yc.gallerypager.inter.IMediaHelper;
import com.yc.gallerypager.loader.DefaultVideoLoader;


public abstract class BasicMediaHelper implements IMediaHelper {
    @Override
    public MediaInfo video(String url, int placeholderViewId) {
        return MediaInfo.mediaLoader(new DefaultVideoLoader(url, placeholderViewId));
    }
}

package com.yc.gallerypager;

import com.yc.gallerypager.inter.IMediaLoader;


public class MediaInfo {

    private IMediaLoader mLoader;

    public static MediaInfo mediaLoader(IMediaLoader mediaLoader) {
        return new MediaInfo().setLoader(mediaLoader);
    }

    public IMediaLoader getLoader() {
        return mLoader;
    }

    public MediaInfo setLoader(IMediaLoader loader) {
        mLoader = loader;
        return this;
    }
}

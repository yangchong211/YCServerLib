package com.yc.cn.ycgallerylib.gallery.model;


import com.yc.cn.ycgallerylib.gallery.loader.MediaLoader;

public class MediaInfo {

    private MediaLoader mLoader;

    public static MediaInfo mediaLoader(MediaLoader mediaLoader) {
        return new MediaInfo().setLoader(mediaLoader);
    }

    public MediaLoader getLoader() {
        return mLoader;
    }

    public MediaInfo setLoader(MediaLoader loader) {
        mLoader = loader;
        return this;
    }
}

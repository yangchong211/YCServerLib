package com.yc.gallerypager.inter;

import com.yc.gallerypager.MediaInfo;

import java.util.List;


public interface IMediaHelper {

    MediaInfo image(String url);

    List<MediaInfo> images(List<String> urls);

    List<MediaInfo> images(String... urls);

    MediaInfo video(String url, int placeholderViewId);
}

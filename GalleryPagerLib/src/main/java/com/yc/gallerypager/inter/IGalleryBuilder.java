package com.yc.gallerypager.inter;

import com.yc.gallerypager.MediaInfo;
import com.yc.gallerypager.ScrollGalleryView;
import com.yc.gallerypager.builder.GallerySettings;

import java.util.List;

public interface IGalleryBuilder {

    IGalleryBuilder settings(GallerySettings settings);

    IGalleryBuilder onImageClickListener(ScrollGalleryView.OnImageClickListener listener);

    IGalleryBuilder onImageLongClickListener(ScrollGalleryView.OnImageLongClickListener listener);

    IGalleryBuilder add(MediaInfo media);

    IGalleryBuilder add(List<MediaInfo> medias);

    ScrollGalleryView build();
}

package com.yc.gallerypager.impl;

import com.yc.gallerypager.MediaInfo;
import com.yc.gallerypager.ScrollGalleryView;
import com.yc.gallerypager.builder.GallerySettings;
import com.yc.gallerypager.inter.IGalleryBuilder;

import java.util.ArrayList;
import java.util.List;

public class GalleryBuilderImpl implements IGalleryBuilder {
    private ScrollGalleryView galleryView;
    private GallerySettings settings;

    private ScrollGalleryView.OnImageClickListener onImageClickListener;
    private ScrollGalleryView.OnImageLongClickListener onImageLongClickListener;

    private List<MediaInfo> medias;

    public GalleryBuilderImpl(ScrollGalleryView galleryView) {
        this.galleryView = galleryView;
        this.medias = new ArrayList<>();
    }

    @Override
    public IGalleryBuilder settings(GallerySettings settings) {
        this.settings = settings;
        return this;
    }

    @Override
    public IGalleryBuilder onImageClickListener(ScrollGalleryView.OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
        return this;
    }

    @Override
    public IGalleryBuilder onImageLongClickListener(ScrollGalleryView.OnImageLongClickListener onImageLongClickListener) {
        this.onImageLongClickListener = onImageLongClickListener;
        return this;
    }

    @Override
    public IGalleryBuilder add(MediaInfo media) {
        this.medias.add(media);
        return this;
    }

    @Override
    public IGalleryBuilder add(List<MediaInfo> medias) {
        this.medias.addAll(medias);
        return this;
    }

    @Override
    public ScrollGalleryView build() {
        // check here all parameters

        return galleryView
                .setThumbnailSize(settings.getThumbnailSize())
                .setZoom(settings.isZoomEnabled())
                .addOnImageClickListener(onImageClickListener)
                .addOnImageLongClickListener(onImageLongClickListener)
                .setFragmentManager(settings.getFragmentManager())
                .addMedia(medias);
    }
}

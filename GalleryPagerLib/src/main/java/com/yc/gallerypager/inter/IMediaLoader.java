package com.yc.gallerypager.inter;

import android.content.Context;
import android.widget.ImageView;


public interface IMediaLoader {

    boolean isImage();

    void loadMedia(Context context, ImageView imageView, SuccessCallback callback);

    void loadThumbnail(Context context, ImageView thumbnailView, SuccessCallback callback);

    interface SuccessCallback {
        void onSuccess();
    }
}
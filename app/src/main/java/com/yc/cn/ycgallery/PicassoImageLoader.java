package com.yc.cn.ycgallery;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yc.gallerypager.inter.IMediaLoader;

public class PicassoImageLoader implements IMediaLoader {

    private String url;
    private Integer thumbnailWidth;
    private Integer thumbnailHeight;
    private int image;
    private int type;
    public static final int LOCAL_IMAGE = 1;
    public static final int NETWORK_IMAGE = 2;


    public PicassoImageLoader(String url , int type) {
        this.url = url;
        this.type = type;
    }

    public PicassoImageLoader(int image ,int type) {
        this.image = image;
        this.type = type;
    }

    public PicassoImageLoader(String url, Integer thumbnailWidth, Integer thumbnailHeight) {
        this.url = url;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }

    public PicassoImageLoader(int image, Integer thumbnailWidth, Integer thumbnailHeight) {
        this.image = image;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }


    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public void loadMedia(Context context, final ImageView imageView, final IMediaLoader.SuccessCallback callback) {
        if (type==LOCAL_IMAGE){
            Picasso.with(context)
                    .load(image)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView, new ImageCallback(callback));
        } else {
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView, new ImageCallback(callback));
        }

    }

    @Override
    public void loadThumbnail(Context context, final ImageView thumbnailView, final IMediaLoader.SuccessCallback callback) {
        if (type==LOCAL_IMAGE){
            Picasso.with(context)
                    .load(image)
                    .resize(thumbnailWidth == null ? 100 : thumbnailWidth,
                            thumbnailHeight == null ? 100 : thumbnailHeight)
                    .placeholder(R.drawable.placeholder_image)
                    .centerInside()
                    .into(thumbnailView, new ImageCallback(callback));
        } else {
            Picasso.with(context)
                    .load(url)
                    .resize(thumbnailWidth == null ? 100 : thumbnailWidth,
                            thumbnailHeight == null ? 100 : thumbnailHeight)
                    .placeholder(R.drawable.placeholder_image)
                    .centerInside()
                    .into(thumbnailView, new ImageCallback(callback));
        }
    }

    private static class ImageCallback implements Callback {
        private final IMediaLoader.SuccessCallback callback;

        public ImageCallback(SuccessCallback callback) {
            this.callback = callback;
        }

        @Override public void onSuccess() {
            callback.onSuccess();
        }

        @Override
        public void onError() {

        }
    }
}

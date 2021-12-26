package com.yc.gallerypager.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.yc.gallerypager.inter.IMediaLoader;


public class DefaultImageLoader implements IMediaLoader {

    private int mId;
    private Bitmap mBitmap;

    public DefaultImageLoader(int id) {
        mId = id;
    }

    public DefaultImageLoader(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public void loadMedia(Context context, ImageView imageView, SuccessCallback callback) {
        imageView.setImageBitmap(mBitmap);
        if (callback != null) {
            callback.onSuccess();
        }
    }

    @Override
    public void loadThumbnail(Context context, ImageView thumbnailView, SuccessCallback callback) {
        loadBitmap(context);
        thumbnailView.setImageBitmap(mBitmap);
        if (callback != null) {
            callback.onSuccess();
        }
    }

    private void loadBitmap(Context context) {
        if (mBitmap == null) {
            mBitmap = ((BitmapDrawable) context.getResources().getDrawable(mId)).getBitmap();
        }
    }
}
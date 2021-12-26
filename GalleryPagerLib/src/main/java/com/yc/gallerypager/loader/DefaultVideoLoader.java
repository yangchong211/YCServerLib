package com.yc.gallerypager.loader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.yc.gallerypager.Constants;
import com.yc.gallerypager.VideoPlayActivity;
import com.yc.gallerypager.inter.IMediaLoader;

public class DefaultVideoLoader implements IMediaLoader {

    private String url;
    private int mId;
    private Bitmap mBitmap;


    public DefaultVideoLoader(String url, int mId) {
        this.url = url;
        this.mId = mId;
    }

    @Override
    public boolean isImage() {
        return false;
    }

    @Override
    public void loadMedia(final Context context, ImageView imageView, SuccessCallback callback) {
        loadBitmap(context);
        imageView.setImageBitmap(mBitmap);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayVideo(context, url);
            }
        });
    }

    @Override
    public void loadThumbnail(Context context, ImageView thumbnailView, SuccessCallback callback) {
        loadBitmap(context);
        thumbnailView.setImageBitmap(mBitmap);
        callback.onSuccess();
    }

    private void displayVideo(Context context, String url) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(Constants.URL, url);
        context.startActivity(intent);
    }

    private void loadBitmap(Context context) {
        if (mBitmap == null) {
            mBitmap = ((BitmapDrawable) context.getResources().getDrawable(mId)).getBitmap();
        }
    }
}

package com.yc.cn.ycgallerylib.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yc.cn.ycgallerylib.R;
import com.yc.cn.ycgallerylib.gallery.loader.MediaLoader;
import com.yc.cn.ycgallerylib.gallery.model.MediaInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryImageView extends LinearLayout {

    private FragmentManager fragmentManager;
    private Context context;
    private Point displayProps;
    private PagerAdapter pagerAdapter;
    private List<MediaInfo> mListOfMedia;

    // Options
    private int thumbnailSize; // width and height in pixels
    private boolean zoomEnabled;
    private boolean thumbnailsHiddenEnabled;

    // Views
    private LinearLayout thumbnailsContainer;
    private HorizontalScrollView horizontalScrollView;
    private ViewPager viewPager;

    // Listeners
    private final ViewPager.SimpleOnPageChangeListener viewPagerChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            scroll(thumbnailsContainer.getChildAt(position));
        }
    };

    private final OnClickListener thumbnailOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            scroll(v);
            viewPager.setCurrentItem((int) v.getTag(), true);
        }
    };

    public GalleryImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        mListOfMedia = new ArrayList<>();
        setOrientation(VERTICAL);
        displayProps = getDisplaySize();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_scroll_gallery, this, true);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.thumbnails_scroll_view);
        thumbnailsContainer = (LinearLayout) findViewById(R.id.thumbnails_container);
        thumbnailsContainer.setPadding(displayProps.x / 2, 0, displayProps.x / 2, 0);
    }


    public GalleryImageView setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        initializeViewPager();
        return this;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }


    public void addOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                scroll(thumbnailsContainer.getChildAt(position));
                listener.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                listener.onPageScrollStateChanged(state);
            }
        });
    }

    public GalleryImageView addMedia(MediaInfo mediaInfo) {
        if (mediaInfo == null) {
            throw new NullPointerException("Info may not be null!");
        }

        return addMedia(Collections.singletonList(mediaInfo));
    }

    public GalleryImageView addMedia(List<MediaInfo> infos) {
        if (infos == null) {
            throw new NullPointerException("Info may not be null!");
        }
        for (MediaInfo info : infos) {
            mListOfMedia.add(info);

            final ImageView thumbnail = addThumbnail(getDefaultThumbnail());
            info.getLoader().loadThumbnail(getContext(), thumbnail, new MediaLoader.SuccessCallback() {
                @Override
                public void onSuccess() {
                    thumbnail.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            });

            pagerAdapter.notifyDataSetChanged();
        }
        return this;
    }


    public GalleryImageView setCurrentItem(int i) {
        viewPager.setCurrentItem(i, false);
        return this;
    }

    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }

    public GalleryImageView setThumbnailSize(int thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
        return this;
    }

    public GalleryImageView setZoom(boolean zoomEnabled) {
        this.zoomEnabled = zoomEnabled;
        return this;
    }

    public GalleryImageView hideThumbnails(boolean thumbnailsHiddenEnabled) {
        this.thumbnailsHiddenEnabled = thumbnailsHiddenEnabled;
        horizontalScrollView.setVisibility(GONE);
        return this;
    }

    private Bitmap getDefaultThumbnail() {
        return ((BitmapDrawable) getContext().getResources().getDrawable(R.drawable.placeholder_image)).getBitmap();
    }

    private Point getDisplaySize() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(point);
        } else {
            point.set(display.getWidth(), display.getHeight());
        }
        return point;
    }

    private ImageView addThumbnail(Bitmap image) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
        lp.setMargins(10, 10, 10, 10);
        Bitmap thumbnail = createThumbnail(image);

        ImageView thumbnailView = createThumbnailView(lp, thumbnail);
        thumbnailsContainer.addView(thumbnailView);
        return thumbnailView;
    }

    private ImageView createThumbnailView(LinearLayout.LayoutParams lp, Bitmap thumbnail) {
        ImageView thumbnailView = new ImageView(context);
        thumbnailView.setLayoutParams(lp);
        thumbnailView.setImageBitmap(thumbnail);
        thumbnailView.setTag(mListOfMedia.size() - 1);
        thumbnailView.setOnClickListener(thumbnailOnClickListener);
        thumbnailView.setScaleType(ImageView.ScaleType.CENTER);
        return thumbnailView;
    }

    private Bitmap createThumbnail(Bitmap image) {
        return ThumbnailUtils.extractThumbnail(image, thumbnailSize, thumbnailSize);
    }

    private void initializeViewPager() {
        viewPager = (CustomViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new ViewPagerAdapter(fragmentManager, mListOfMedia, zoomEnabled);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerChangeListener);
    }

    private void scroll(View thumbnail) {
        int thumbnailCords[] = new int[2];
        thumbnail.getLocationOnScreen(thumbnailCords);
        int thumbnailCenterX = thumbnailCords[0] + thumbnailSize / 2;
        int thumbnailDelta = displayProps.x / 2 - thumbnailCenterX;
        horizontalScrollView.smoothScrollBy(-thumbnailDelta, 0);
    }

    private int calculateInSampleSize(int imgWidth, int imgHeight, int maxWidth, int maxHeight) {
        int inSampleSize = 1;
        while (imgWidth / inSampleSize > maxWidth || imgHeight / inSampleSize > maxHeight) {
            inSampleSize *= 2;
        }
        return inSampleSize;
    }

}

package com.yc.cn.ycgallerylib.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/05/30
 *     desc  : 支持滑动viewpager图片浏览控件
 *     revise:
 * </pre>
 */
public class GalleryImageView extends LinearLayout {

    private FragmentManager fragmentManager;
    private Context context;
    private Point displayProps;
    private PagerAdapter pagerAdapter;
    private List<Bitmap> mListOfMedia;
    private int thumbnailSize;
    private boolean zoomEnabled;
    private LinearLayout thumbnailsContainer;
    private HorizontalScrollView horizontalScrollView;
    private ViewPager viewPager;

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
        horizontalScrollView = findViewById(R.id.thumbnails_scroll_view);
        thumbnailsContainer = findViewById(R.id.thumbnails_container);
        thumbnailsContainer.setPadding(displayProps.x / 2, 0, displayProps.x / 2, 0);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }

    public GalleryImageView setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        initializeViewPager();
        return this;
    }

    public GalleryImageView addOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
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
        return this;
    }

    public GalleryImageView addBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new NullPointerException("bitmap may not be null!");
        }
        return addBitmap(Collections.singletonList(bitmap));
    }

    public GalleryImageView addBitmap(List<Bitmap> bitmapList) {
        if (bitmapList == null) {
            throw new NullPointerException("bitmapList may not be null!");
        }
        for (Bitmap bitmap : bitmapList) {
            mListOfMedia.add(bitmap);
            addThumbnail(bitmap);
            pagerAdapter.notifyDataSetChanged();
        }
        return this;
    }


    public GalleryImageView setCurrentItem(int i) {
        viewPager.setCurrentItem(i, false);
        return this;
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
        if (thumbnailsHiddenEnabled){
            horizontalScrollView.setVisibility(GONE);
        }else {
            horizontalScrollView.setVisibility(VISIBLE);
        }
        return this;
    }


    private Point getDisplaySize() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display ;
        Point point = new Point();
        if (windowManager != null) {
            display = windowManager.getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                display.getSize(point);
            } else {
                point.set(display.getWidth(), display.getHeight());
            }
        }
        return point;
    }

    private ImageView addThumbnail(Bitmap bitmap) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
        lp.setMargins(10, 10, 10, 10);
        Bitmap image = ThumbnailUtils.extractThumbnail(bitmap, thumbnailSize, thumbnailSize);
        ImageView thumbnailView = createThumbnailView(lp, image);
        thumbnailsContainer.addView(thumbnailView);
        return thumbnailView;
    }

    private ImageView createThumbnailView(LinearLayout.LayoutParams lp, Bitmap bitmap) {
        ImageView thumbnailView = new ImageView(context);
        thumbnailView.setLayoutParams(lp);
        thumbnailView.setImageBitmap(bitmap);
        thumbnailView.setTag(mListOfMedia.size() - 1);
        thumbnailView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll(v);
                viewPager.setCurrentItem((int) v.getTag(), true);
            }
        });
        thumbnailView.setScaleType(ImageView.ScaleType.CENTER);
        return thumbnailView;
    }

    private void initializeViewPager() {
        viewPager = (CustomViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new ViewPagerAdapter(fragmentManager, mListOfMedia, zoomEnabled);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(thumbnailsContainer.getChildAt(position));
            }
        });
    }

    private void scroll(View thumbnail) {
        int thumbnailCords[] = new int[2];
        thumbnail.getLocationOnScreen(thumbnailCords);
        int thumbnailCenterX = thumbnailCords[0] + thumbnailSize / 2;
        int thumbnailDelta = displayProps.x / 2 - thumbnailCenterX;
        horizontalScrollView.smoothScrollBy(-thumbnailDelta, 0);
    }

}

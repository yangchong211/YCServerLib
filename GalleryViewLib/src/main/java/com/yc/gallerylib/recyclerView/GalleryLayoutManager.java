package com.yc.gallerylib.recyclerView;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/12/10
 *     desc  : 自定义LinearLayoutManager
 *     revise:
 * </pre>
 */
public class GalleryLayoutManager extends LinearLayoutManager {

    private PagerSnapHelper mSnapHelper;
    private OnGalleryListener mOnGalleryListener;
    private int mDrift;     //位移，用来判断移动方向

    public GalleryLayoutManager(Context context, int orientation) {
        super(context, orientation, false);
        init();
    }

    private void init() {
        mSnapHelper = new PagerSnapHelper();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mSnapHelper.attachToRecyclerView(view);
        view.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener);
    }

    /**
     * 滑动状态的改变
     * 缓慢拖拽-> SCROLL_STATE_DRAGGING
     * 快速滚动-> SCROLL_STATE_SETTLING
     * 空闲状态-> SCROLL_STATE_IDLE
     * @param state                     状态
     */
    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            //空闲状态
            case RecyclerView.SCROLL_STATE_IDLE:
                View snap = mSnapHelper.findSnapView(this);
                int positionIdle ;
                if (snap != null) {
                    positionIdle = getPosition(snap);
                    if (mOnGalleryListener != null && getChildCount() == 1) {
                        mOnGalleryListener.onPageSelected(positionIdle);
                    }
                }
                break;
            //缓慢拖拽
            case RecyclerView.SCROLL_STATE_DRAGGING:
                View viewDrag = mSnapHelper.findSnapView(this);
                if (viewDrag != null) {
                    int positionDrag = getPosition(viewDrag);
                }
                break;
            //快速滚动
            case RecyclerView.SCROLL_STATE_SETTLING:
                View viewSettling = mSnapHelper.findSnapView(this);
                if (viewSettling != null) {
                    int positionSettling = getPosition(viewSettling);
                }
                break;
        }
    }


    /**
     * 监听竖直方向的相对偏移量
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }


    /**
     * 监听水平方向的相对偏移量
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dx;
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    /**
     * 设置监听，由于画廊可能有视频，所以定义的抽象方法有:初始化完成，释放的监听，选中的监听等等
     * @param listener                  listener
     */
    public void setOnGalleryListener(OnGalleryListener listener){
        this.mOnGalleryListener = listener;
    }

    private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnGalleryListener != null && getChildCount() == 1) {
                mOnGalleryListener.onInitComplete();
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
            if (mDrift >= 0){
                if (mOnGalleryListener != null){
                    mOnGalleryListener.onPageRelease(true , getPosition(view));
                }
            }else {
                if (mOnGalleryListener != null){
                    mOnGalleryListener.onPageRelease(false , getPosition(view));
                }
            }
        }
    };

}

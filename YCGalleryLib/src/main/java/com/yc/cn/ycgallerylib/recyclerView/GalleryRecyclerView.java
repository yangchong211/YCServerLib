package com.yc.cn.ycgallerylib.recyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import static android.view.ViewConfiguration.*;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/4/17
 *     desc  : 自定义RecyclerView，画廊，采用builder模式
 *     revise:
 * </pre>
 */
public class GalleryRecyclerView extends RecyclerView {


    /**
     * 滑动速度
     */
    private int mFlingSpeed = FLING_MAX_VELOCITY;
    /**
     * 最大顺时滑动速度
     */
    private static final int FLING_MAX_VELOCITY = 8000;
    private RecyclerView.Adapter adapter;
    private OnGalleryListener listener;
    private int selectedPosition;
    private int orientation = RecyclerView.HORIZONTAL;
    private boolean isLocked;
    private int touchSlop;
    private int initialTouchX;
    private int initialTouchY;

    public GalleryRecyclerView(Context context) {
        this(context,null);
    }

    public GalleryRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GalleryRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        isLocked = false;
        ViewConfiguration vc = get(context);
        touchSlop = vc.getScaledEdgeSlop();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        //异常情况保存重要信息。
        //return super.onSaveInstanceState();
        final Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("selectedPosition",selectedPosition);
        bundle.putInt("flingSpeed",mFlingSpeed);
        bundle.putInt("orientation",orientation);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            selectedPosition = bundle.getInt("selectedPosition",selectedPosition);
            mFlingSpeed = bundle.getInt("flingSpeed",mFlingSpeed);
            orientation = bundle.getInt("orientation",orientation);
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state);
        // 轮播图用在fragment中，如果是横竖屏切换（Fragment销毁），不应该走smoothScrollToPosition(0)
        // 因为这个方法会导致ScrollManager的onHorizontalScroll不断执行，而ScrollManager.mConsumeX已经重置，会导致这个值紊乱
        // 而如果走scrollToPosition(0)方法，则不会导致ScrollManager的onHorizontalScroll执行，
        // 所以ScrollManager.mConsumeX这个值不会错误
        // 从索引selectedPosition处开始轮播
        // 但是因为不走ScrollManager的onHorizontalScroll，所以不会执行切换动画，
        // 所以就调用smoothScrollBy(int dx, int dy)，让item轻微滑动，触发动画
        smoothScrollBy(10, 0);
        smoothScrollBy(0, 0);
        scrollToPosition(selectedPosition);
    }

    /**
     * 这个方法是控制滑动速度的。通过它可以改变滑动因子，标准值是8000
     */
    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityX = balanceVelocity(velocityX);
        velocityY = balanceVelocity(velocityY);
        return super.fling(velocityX, velocityY);
    }

    /**
     * 返回滑动速度值
     */
    private int balanceVelocity(int velocity) {
        if (velocity > 0) {
            return Math.min(velocity, mFlingSpeed);
        } else {
            return Math.max(velocity, -mFlingSpeed);
        }
    }

    /**
     * 处理拦截事件的逻辑，更多可以看我的GitHub：https://github.com/yangchong211
     * 默认实现是返回false，也就是默认不拦截任何事件
     * 如果该方法返回为true，那么View将消费该事件，即会调用onTouchEvent()方法
     * 如果返回false,那么通过调用子View的dispatchTouchEvent()将事件交由子View来处理
     * ACTION_UP                            手指抬起
     * ACTION_CANCEL                        手指结束
     * ACTION_POINTER_UP                    多个手指情况下抬起一个手指,此时需要是缩放模式才触发
     * ACTION_DOWN                          手指抬起
     * ACTION_POINTER_DOWN                  代表用户又使用一个手指触摸到屏幕上，在已有一个触摸点的情况下，有新出现了一个触摸点。
     * ACTION_MOVE                          手指移动
     * @param e                             event
     * @return                              返回true表示自己处理事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e == null) {
            return false;
        }
        int action = MotionEventCompat.getActionMasked(e);
        int actionIndex = MotionEventCompat.getActionIndex(e);
        switch (action) {
            case MotionEvent.ACTION_DOWN :
                //获取手指抬起时候的位置，不拦截
                initialTouchX = Math.round(e.getX() + 0.5f);
                initialTouchY = Math.round(e.getY() + 0.5f);
                return super.onInterceptTouchEvent(e);
            case MotionEvent.ACTION_POINTER_DOWN:
                //获取多个手指情况下抬起一个手指的位置，不拦截
                initialTouchX = Math.round(MotionEventCompat.getX(e, actionIndex) + 0.5f);
                initialTouchY = Math.round(MotionEventCompat.getY(e, actionIndex) + 0.5f);
                return super.onInterceptTouchEvent(e);
            case MotionEvent.ACTION_UP:
                //sho到抬起的时候，不拦截
                return super.onInterceptTouchEvent(e);
            case MotionEvent.ACTION_MOVE:
                //移动，视情况拦截
                int x = Math.round(MotionEventCompat.getX(e, actionIndex) + 0.5f);
                int y = Math.round(MotionEventCompat.getY(e, actionIndex) + 0.5f);
                //获取滚动状态如果不是缓慢拖拽
                if (getScrollState() != SCROLL_STATE_DRAGGING ) {
                    int dx = x - initialTouchX;
                    int dy = y - initialTouchY;
                    boolean startScroll = false;
                    if (getLayoutManager()!=null){
                        boolean scrollHorizontally = getLayoutManager().canScrollHorizontally();
                        boolean scrollVertically = getLayoutManager().canScrollVertically();
                        if(scrollHorizontally && Math.abs(dy) > touchSlop && ( Math.abs(dx) > Math.abs(dy))) {
                            startScroll = true;
                        }
                        if(scrollVertically && Math.abs(dy) > touchSlop && ( Math.abs(dy) > Math.abs(dx))) {
                            startScroll = true;
                        }

                        //如下条件，结合成一个条件， 前者条件已经是判断未纵向移动了，那么后面补上横向移动就行
                        if(scrollVertically && Math.abs(dy) > touchSlop && (scrollHorizontally || Math.abs(dy) > Math.abs(dx))) {
                            startScroll = true;
                        }
                    }
                    return startScroll && super.onInterceptTouchEvent(e);
                }
                return super.onInterceptTouchEvent(e);
            default:
                return super.onInterceptTouchEvent(e);
        }
    }

    /**
     * 触摸事件
     * 如果返回结果为false表示不消费该事件，并且也不会截获接下来的事件序列，事件会继续传递
     * 如果返回为true表示当前View消费该事件，阻止事件继续传递
     *
     * 在这里要强调View的OnTouchListener。如果View设置了该监听，那么OnTouch()将会回调。
     * 如果返回为true那么该View的OnTouchEvent将不会在执行 这是因为设置的OnTouchListener执行时的优先级要比onTouchEvent高。
     * 优先级：OnTouchListener > onTouchEvent > onClickListener
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("onEvent","MyLinearLayout onTouchEvent");
        return super.onTouchEvent(event);
    }



    /**
     * 设置是否拦截事件，如果画廊图片支持双指缩放，则需要设置为true
     */
    public GalleryRecyclerView setLocked(boolean isLocked) {
        this.isLocked = isLocked;
        return this;
    }

    /**
     * 设置滑动速度（像素/s）
     * 建议设置8000到15000之间，不要设置太小。
     */
    public GalleryRecyclerView setFlingSpeed(int speed) {
        this.mFlingSpeed = speed;
        return this;
    }

    /**
     * 设置adapter
     */
    public GalleryRecyclerView setDataAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        return this;
    }

    /**
     * 设置滚动监听事件
     */
    public GalleryRecyclerView setOnGalleryListener(OnGalleryListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 设置选中的索引
     */
    public GalleryRecyclerView setSelectedPosition(int position){
        this.selectedPosition = position;
        return this;
    }

    /**
     * 设置横向或者竖向，注意需要限制传入类型
     */
    public GalleryRecyclerView setOrientation(@RecyclerView.Orientation int orientation){
        this.orientation = orientation;
        return this;
    }

    /**
     * 装载：
     * 注意要点：recyclerView轮播要是无限轮播，必须设置两点
     * 第一处是getItemCount() 返回的是Integer.MAX_VALUE。这是因为广告轮播图是无限轮播，getItemCount()
     * 返回的是Adapter中的总项目数，这样才能使RecyclerView能一直滚动。
     *
     * 第二处是onBindViewHolder()中的 position%list.size() ，表示position对图片列表list取余，
     * 这样list.get(position%list.size())才能按顺序循环展示图片。
     */
    public void setUp() {
        if (adapter!=null){
            setAdapter(adapter);
        }else {
            throw new NullPointerException("需要设置adapter");
        }
        if (this.getAdapter()!=null && this.getAdapter().getItemCount() <= 0) {
            return;
        }
        //暂时保持横向滑动
        GalleryLayoutManager manager = new GalleryLayoutManager(getContext(),orientation);
        setLayoutManager(manager);
        if (listener!=null){
            manager.setOnGalleryListener(listener);
        }
        adapter.notifyDataSetChanged();
        //滚动到指定的索引处
        //这里地方建议限定范围，否则有可能造成索引越界
        //smoothScrollToPosition(selectedPosition);
        scrollToPosition(selectedPosition);
    }


}

package com.yc.gallerylib.recyclerView;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/12/10
 *     desc  : 自定义画廊listener监听
 *     revise: 暂时先定义这几个监听事件
 * </pre>
 */
public interface OnGalleryListener {

    /**
     * 初始化完成
     * 【播放视频初始化这个】
     */
    void onInitComplete();

    /**
     * 释放的监听【播放视频需要这个】
     * @param isNext                是否下一个
     * @param position              索引
     */
    void onPageRelease(boolean isNext, int position);

    /**
     * 选中的监听
     * @param position              索引
     */
    void onPageSelected(int position);


}

package com.yc.cn.ycgallerylib.zoom.pool;


import android.graphics.RectF;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/05/30
 *     desc  : 矩阵对象池
 *     revise:
 * </pre>
 */
public class RectFPool extends ObjectsPool<RectF>{

    /**
     * 矩形对象池
     */
    public RectFPool(int size) {
        super(size);
    }

    @Override
    protected RectF newInstance() {
        return new RectF();
    }

    @Override
    protected RectF resetInstance(RectF obj) {
        obj.setEmpty();
        return obj;
    }


}

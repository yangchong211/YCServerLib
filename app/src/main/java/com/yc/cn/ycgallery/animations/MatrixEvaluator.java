package com.yc.cn.ycgallery.animations;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.widget.ImageView;


public class MatrixEvaluator implements TypeEvaluator<Matrix> {



    private float[] mTempStartValues = new float[9];
    private float[] mTempEndValues = new float[9];
    private Matrix mTempMatrix = new Matrix();

    /**
     * 当我们对ImageView的图像矩阵进行动画处理时，此属性将传递给Object动画器
     */
    static final Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY =
            new Property<ImageView, Matrix>(Matrix.class, "animatedTransform") {

        /**
         * 这是复制粘贴表单ImageView动画转换方法在SDK中是不可见的
         */
        @Override
        public void set(ImageView imageView, Matrix matrix) {
            Drawable drawable = imageView.getDrawable();
            if (drawable == null) {
                return;
            }
            if (matrix == null) {
                drawable.setBounds(0, 0, imageView.getWidth(), imageView.getHeight());
            } else {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                Matrix drawMatrix = imageView.getImageMatrix();
                if (drawMatrix == null) {
                    drawMatrix = new Matrix();
                    imageView.setImageMatrix(drawMatrix);
                }
                imageView.setImageMatrix(matrix);
            }
            imageView.invalidate();
        }

        @Override
        public Matrix get(ImageView object) {
            return null;
        }
    };


    @Override
    public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
        startValue.getValues(mTempStartValues);
        endValue.getValues(mTempEndValues);
        for (int i = 0; i < 9; i++) {
            float diff = mTempEndValues[i] - mTempStartValues[i];
            mTempEndValues[i] = mTempStartValues[i] + (fraction * diff);
        }
        mTempMatrix.setValues(mTempEndValues);
        return mTempMatrix;
    }

}

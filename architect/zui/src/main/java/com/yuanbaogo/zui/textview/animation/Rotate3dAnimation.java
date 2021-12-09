package com.yuanbaogo.zui.textview.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @Description: 旋转3d动画
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/7/21 3:47 PM
 * @Modifier:
 * @Modify:
 */
public class Rotate3dAnimation extends Animation {

    View mView;
    private final float mFromDegrees;
    private final float mToDegrees;
    private float mCenterX;
    private float mCenterY;
    private final boolean mTurnIn;
    private final boolean mTurnUp;
    private Camera mCamera;

    public Rotate3dAnimation(View view, float fromDegrees, float toDegrees, boolean turnIn, boolean turnUp) {
        mView = view;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mTurnIn = turnIn;
        mTurnUp = turnUp;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
        mCenterY = mView.getHeight();
        mCenterX = mView.getWidth() / 2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        final int derection = mTurnUp ? 1 : -1;

        final Matrix matrix = t.getMatrix();

        camera.save();
        if (mTurnIn) {
            camera.translate(0.0f, derection * mCenterY * (interpolatedTime - 1.0f), 0.0f);
        } else {
            camera.translate(0.0f, derection * mCenterY * (interpolatedTime), 0.0f);
        }
        camera.rotateX(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }

}
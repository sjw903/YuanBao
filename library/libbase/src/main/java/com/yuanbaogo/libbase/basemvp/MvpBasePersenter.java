package com.yuanbaogo.libbase.basemvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.CallSuper;

import java.lang.ref.WeakReference;

/**
 * @author yangf
 * @version 1.0
 */

public abstract class MvpBasePersenter<T extends IBaseView> implements IBasePresenter {

    public String TAG = getClass().getSimpleName();

    /**
     * View层接口，通过该接口控制View
     */
    protected WeakReference<T> mView;

//    protected Context mContext;


    /*----------------*/
    /* ATTACH CREATOR */
    /*----------------*/
    /**
     * 绑定视图
     */
    void attach(T view) {
        mView = new WeakReference<>(view);
    }

    /**
     * 解绑视图
     */
    public void detachView() {
//        this.mContext = null;
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }


    /*------------*/
    /* LIFE CYCLE */
    /*------------*/

    /**
     * 默认在执行Activity或Fragment的onCreate方法后被调用
     */
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {

    }

    /**
     * 决定是否继续创建页面，
     * 注意：如果返回false时，这里不会处理后续操作，例如关闭页面，需要自行处理。
     *
     * @return true，如果继续创建
     */
    protected boolean startCreate(Bundle savedInstanceState) {
        return true;
    }

    /**
     * 获取到传递信息
     *
     * @param intent 信息包
     */
    protected void onIntent(Intent intent) {

    }

    /**
     * 获取到传递数据
     *
     * @param bundle 信息包
     */
    protected void onExtras(Bundle bundle) {
    }

    /**
     * 默认在执行Activitye或Fragment的视图创建完毕后被调用
     *
     * @param savedInstanceState savedInstanceState
     */
    @CallSuper
    protected void onViewCreated(Bundle savedInstanceState) {

    }


    /**
     * 当入场动画结束后调用
     */
    @CallSuper
    protected void onEnterAnimationEnd() {

    }

    /**
     * 默认在执行Activity或Fragment的onStart方法后被调用
     */
    @CallSuper
    protected void onStart() {

    }


    /**
     * 默认在执行Activity或Fragment的onResume方法后被调用
     */
    @CallSuper
    protected void onResume() {

    }

    /**
     * 默认在执行Activity或Fragment的onPause方法后被调用
     */
    @CallSuper
    protected void onPause() {

    }

    /**
     * 默认在执行Activity或Fragment的onSaveInstanceState方法后被调用
     */
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {

    }

    /**
     * 默认在执行Activity或Fragment的onStop方法后被调用
     */
    @CallSuper
    protected void onStop() {

    }

    /**
     * 默认在执行Activity的onDestroy方法或Fragment的onDetach方法后被调用
     */
    @CallSuper
    protected void onDestroy() {

    }

    /*--------*/
    /* OTHERS */
    /*--------*/

    /**
     * 获取到new intent传递信息
     *
     * @param intent 信息包
     */
    protected void onNewIntent(Intent intent) {

    }

    /**
     * 获取到new intent传递数据
     *
     * @param bundle 信息包
     */
    protected void onNewExtras(Bundle bundle) {

    }

    /**
     * 默认在执行Activity或Fragment的onActivityResult方法后被调用
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
    
    public T getView() {
        if (mView != null) {
            return mView.get();
        }
        return null;
    }

    /**
     * 页面是否存在
     * @return true flase
     */
    protected boolean isActive() {
        return getView() != null && getView().isActive();
    }

    protected Context getContext(){
        if(isActive()){
            return mView.get().getViewContext();
        }
        return null;
    }


}

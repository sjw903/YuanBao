package com.yuanbaogo.libbase.basemvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author yangf
 * @version 1.0
 */
public abstract class MvpBaseFragment<V extends IBaseView, P extends MvpBasePersenter> extends Fragment
        implements IBaseView {
    public final String TAG = getClass().getSimpleName();

    protected P mPresenter;
    private View mContentView;
    protected Activity mContext;

    private boolean mIsFinish = false;

    /*--------------------*/
    /* ABSTRACT ON CREATE */
    /*--------------------*/

    protected abstract P createPresenter(Bundle savedInstanceState);

    protected abstract int createContentView(Bundle savedInstanceState);

    protected abstract void bindViews(Bundle savedInstanceState);

    protected abstract void bindListeners(Bundle savedInstanceState);

    protected abstract void initViews(Bundle savedInstanceState);

    protected View findViewById(@IdRes int id) {
        return mContentView.findViewById(id);
    }

    /*------------*/
    /* LIFE CYCLE */
    /*------------*/


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mPresenter = createPresenter(savedInstanceState);
        mPresenter.attach(this);
        mPresenter.onCreate(savedInstanceState);
        if (mPresenter.startCreate(savedInstanceState)) {
            startCreate(savedInstanceState);
        }
    }

    private void startCreate(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPresenter.onExtras(bundle);
        }
    }

    @Override
    @NonNull
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContentView = inflater.inflate(createContentView(savedInstanceState), container, false);
        bindViews(savedInstanceState);
        bindListeners(savedInstanceState);
        initViews(savedInstanceState);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }

    /*-------------*/
    /* BASE METHOD */
    /*-------------*/

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (mPresenter == null) {
//            return;
//        }
//        mPresenter.onConfigurationChanged(newConfig);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    /*-----------*/
    /* VIEW IMPL */
    /*-----------*/

//    /**
//     * 结束当前Fragment
//     */
//    public void finish() {
//        if (isPageValid()) {
//            mIsFinish = false;
//        }
//
//        if (!mIsFinish) {
//            mIsFinish = true;
//        }
//    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public Context getViewContext() {
        return mContext;
    }

}

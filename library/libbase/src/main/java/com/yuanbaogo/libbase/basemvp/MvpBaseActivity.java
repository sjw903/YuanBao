package com.yuanbaogo.libbase.basemvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.CallSuper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yuanbaogo.libbase.view.BaseActivity;

import java.lang.reflect.ParameterizedType;


/**
 * @author yangf
 * @version 1.0
 */
public abstract class MvpBaseActivity<V extends IBaseView, P extends MvpBasePersenter> extends BaseActivity implements IBaseView {
    public final String TAG = getClass().getSimpleName();

    protected P mPresenter;

    /*--------------------*/
    /* ABSTRACT ON CREATE */
    /*--------------------*/

    /**
     * 创建Presenter
     *
     * @param savedInstanceState
     * @return Presenter，主持者，连接View和Presenter
     */
//    protected abstract P createPresenter(Bundle savedInstanceState);

    /**
     * 创建基础内容视图，基于layout文件
     *
     * @param savedInstanceState
     * @return LayoutRes文件，用于创建基础视图
     */
    protected abstract int createContentView(Bundle savedInstanceState);

    /**
     * 绑定视图元素，例如findViewById
     *
     * @param savedInstanceState 保存的示例状态
     */
    protected abstract void bindViews(Bundle savedInstanceState);

    /**
     * 绑定视图监听者，例如setOnClickListener
     *
     * @param savedInstanceState 保存的示例状态
     */
    protected abstract void bindListeners(Bundle savedInstanceState);

    /**
     * 初始化视图元素，例如setVisibility
     *
     * @param savedInstanceState 保存的示例状态
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /*------------*/
    /* LIFE CYCLE */
    /*------------*/

    @Override
    @CallSuper
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPresenter = createPresenter(savedInstanceState);
        initARoute();
        mPresenter = getInstance(this, 1);
        mPresenter.attach(this);
        mPresenter.onCreate(savedInstanceState);

        if (mPresenter.startCreate(savedInstanceState)) {
            startCreate(savedInstanceState);
        }
    }

    private void initARoute() {
        ARouter.getInstance().inject(this);
    }

    /**
     * 如果你想在界面初始化完成之前进行操作，
     * 例如终止界面的初始化和后续操作,
     * 复写此方法，不调用父类实现即可。
     *
     * @param savedInstanceState 保存状态
     */
    @SuppressWarnings("unchecked")
    protected void startCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mPresenter.onIntent(intent);
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mPresenter.onExtras(bundle);
            }
        }

        setContentView(createContentView(savedInstanceState));
        bindViews(savedInstanceState);
        initViews(savedInstanceState);
        bindListeners(savedInstanceState);
        mPresenter.onViewCreated(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter.onDestroy();
        mPresenter = null;

    }

    /*-------------*/
    /* BASE METHOD */
    /*-------------*/

//    @Override
    // 这个方法是API 21(5.0)加入的，之前的版本将不会被调用
//    public void onEnterAnimationComplete() {
//        super.onEnterAnimationComplete();
//        if (mPresenter == null) {
//            return;
//        }
//        mPresenter.onEnterAnimationEnd();
//    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (mPresenter == null) {
//            return;
//        }
//        mPresenter.onConfigurationChanged(newConfig);
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mPresenter == null) {
            return;
        }
        if (intent != null) {
            mPresenter.onNewIntent(intent);
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mPresenter.onNewExtras(bundle);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter == null) {
            return;
        }
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

//    public void startFromRoot(BaseFragment toFragment) {
//        startFromRoot(toFragment, BaseFragment.SINGLETOP);
//    }
//
//    public void startFromRoot(SupportFragment toFragment, final int launchMode) {
//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        if (fragments == null || fragments.size() < 1) {
//            return;
//        }
//        Fragment fragment = fragments.get(0);
//        if (fragment instanceof BaseFragment) {
//            ((BaseFragment) fragment).start(toFragment, launchMode);
//        }
//    }

    /*-----------*/
    /* VIEW IMPL */
    /*-----------*/

    @Override
    public void finish() {
        super.finish();
    }

    public boolean isActive() {
        return !isFinishing();
    }


    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass()))
                    .getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Context getViewContext() {
        return this;
    }

}

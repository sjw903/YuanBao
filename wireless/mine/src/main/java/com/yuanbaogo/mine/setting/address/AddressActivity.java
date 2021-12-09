package com.yuanbaogo.mine.setting.address;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.commonlib.router.model.AddressOrderBean;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.model.AddressBean;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.dialog.NormalDialogFragment;
import com.yuanbaogo.zui.swipe.SwipeRecyclerView;
import com.yuanbaogo.zui.toast.ToastView;

import java.util.ArrayList;
import java.util.List;

import static com.yuanbaogo.mine.order.utils.Configuration.DEFAULT_ONE;
import static com.yuanbaogo.mine.order.utils.Configuration.LOAD_ROWS;

@Route(path = YBRouter.ADDRESS_ACTIVITY)
public class AddressActivity extends MvpBaseActivityImpl<AddressContract.View, AddressPresenter>
        implements AddressContract.View, View.OnClickListener, AddressRecyclerAdapter.OnDeleteAddressListener, OnRefreshListener, OnLoadMoreListener {

    private static final String TAG = "AddressActivity";
    private final List<AddressBean> mAddressBeanList = new ArrayList<>();
    private AddressRecyclerAdapter mAdapter;
    private SwipeRecyclerView mAddressRvList;
    private TextView mAddressTvAdd;
    private SmartRefreshLayout mAddressSmartRefresh;
    private ImageView mAddressEmpty;
    protected int mPage = DEFAULT_ONE;
    private NormalDialogFragment mDialog;
    @Autowired(name = YBRouter.AddressActivityParams.isOrderFlag)
    boolean isOrderFlag = false;

    @Autowired(name = YBRouter.AddressActivityParams.addressId)
    String addressId;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_address;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mAddressTvAdd = findViewById(R.id.address_tv_add);
        mAddressSmartRefresh = findViewById(R.id.address_smart_refresh);
        mAddressRvList = findViewById(R.id.address_rv_list);
        mAddressEmpty = findViewById(R.id.address_empty);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAddressTvAdd.setOnClickListener(this);
        mAdapter.setOnDeleteAddressListener(this);
        mAddressSmartRefresh.setOnRefreshListener(this);
        mAddressSmartRefresh.setOnLoadMoreListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAdapter = new AddressRecyclerAdapter(this, mAddressBeanList);
        mAddressRvList.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        clear();
        loadData();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.address_tv_add) {
            RouterHelper.toUpdateAddress("");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void loadAddressList(List<AddressBean> addressBeanList) {
        loadFinish(true);
        showEmpty(addressBeanList == null);
        if (addressBeanList == null) return;
        if (!TextUtils.isEmpty(addressId)) {
            for (int i = 0; i < addressBeanList.size(); i++) {
                if (addressId.equals(addressBeanList.get(i).getAddressId() + "")) {
                    addressBeanList.get(i).setSelect(true);
                } else {
                    addressBeanList.get(i).setSelect(false);
                }
            }
        }
        if (addressBeanList.size() != LOAD_ROWS) {
            mAddressSmartRefresh.finishLoadMoreWithNoMoreData();
        }
        mAddressBeanList.addAll(addressBeanList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFailure() {
        loadFinish(false);
    }

    @Override
    public void deleteAddress(long addressId) {
        AddressBean deleteAddressBean = null;
        for (int i = 0; i < mAddressBeanList.size(); i++) {
            AddressBean addressBean = mAddressBeanList.get(i);
            if (addressBean.getAddressId() == addressId) {
                deleteAddressBean = addressBean;
            }
        }
        if (deleteAddressBean == null) {
            return;
        }
        mAddressBeanList.remove(deleteAddressBean);
        showEmpty(mAddressBeanList.isEmpty());
        mAdapter.notifyDataSetChanged();
        ToastView.showToast("删除收货地址成功");
    }

    /**
     * 是否显示缺省页面
     *
     * @param isShowEmpty true 显示  false 不显示
     */
    protected void showEmpty(boolean isShowEmpty) {
        if (isShowEmpty) {
            mAddressEmpty.setVisibility(View.VISIBLE);
            mAddressSmartRefresh.setVisibility(View.GONE);
        } else {
            mAddressEmpty.setVisibility(View.GONE);
            mAddressSmartRefresh.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDelete(long addressId) {
        mDialog = new NormalDialogFragment(getString(R.string.delete_address_title), getString(R.string.delete_address_content),
                getString(R.string.delete_address_left),
                getString(R.string.delete_address_right));
        mDialog.show(getSupportFragmentManager(), "payPassword");
        mDialog.setOnNormalListener(() -> mPresenter.deleteAddress(addressId));
    }

    /**
     * 将信息返回到确认订单页面
     *
     * @param view
     * @param bean
     */
    @Override
    public void onItem(View view, AddressBean bean) {
        if (!isOrderFlag) {
            return;
        }
        RouterHelper.toAddressResult(AddressActivity.this, new AddressOrderBean()
                .setAddressId(bean.getAddressId())
                .setAddressDetails(bean.getAddressDetails())
                .setCity(bean.getCity())
                .setContactsName(bean.getContactsName())
                .setContactsPhone(bean.getContactsPhone())
                .setCountry(bean.getCountry())
                .setDefaulted(bean.getDefaulted())
                .setProvince(bean.getProvince())
                .setUserId(bean.getUserId()));
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPage = DEFAULT_ONE;
        clear();
        loadData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPage++;
        loadData();
    }

    private void loadData() {
        mPresenter.getAddressList(mPage);
    }

    private void loadFail() {
        if (mPage > 1) mPage--;
        else mPage = DEFAULT_ONE;
    }

    protected void loadFinish(boolean successful) {
        mAddressSmartRefresh.finishRefresh(successful);
        mAddressSmartRefresh.finishLoadMore(successful);
        if (!successful) loadFail();
    }

    private void clear() {
        if (mAddressBeanList.size() <= 0) {
            return;
        }
        mAddressBeanList.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}
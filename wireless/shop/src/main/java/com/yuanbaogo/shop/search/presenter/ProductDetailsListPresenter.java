package com.yuanbaogo.shop.search.presenter;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.commonlib.router.model.SearchMerchandiseBean;
import com.yuanbaogo.shop.publics.model.SearchMerchandiseListBean;
import com.yuanbaogo.shop.search.contract.ProductDetailsListContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/19/21 3:33 PM
 * @Modifier:
 * @Modify:
 */
public class ProductDetailsListPresenter extends MvpBasePersenter<ProductDetailsListContract.View>
        implements ProductDetailsListContract.Presenter {

    @Override
    public void getSearchMerchandise(SearchMerchandiseBean searchMerchandiseBean) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderBy", searchMerchandiseBean.getOrderBy());
        params.put("pageNum", searchMerchandiseBean.getPageNum());
        params.put("pageSize", searchMerchandiseBean.getPageSize());
        params.put("tag", searchMerchandiseBean.getTag());
        params.put("esGoodsName", searchMerchandiseBean.getEsGoodsName());
        params.put("specialId", searchMerchandiseBean.getSpecialId());
        NetWork.getInstance().post(getContext(),
                ChildUrl.SEARCH_MERCHANDISE,
                params,
                new RequestSateListener<SearchMerchandiseListBean>() {
                    @Override
                    public void onSuccess(int code, SearchMerchandiseListBean bean) {
                        if (code == 200 && getView() != null) {
                            getView().setSearchMerchandise(bean);
                        }
                    }

                    @Override
                    public void onFailure(Throwable var1) {
                        if (getView() != null) {
                            getView().initSearchMerchandise();
                        }
                    }
                });
    }

}

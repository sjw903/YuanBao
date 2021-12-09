package com.yuanbaogo.mine.setting.about;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuanbao.update.entity.AppUpdate;
import com.yuanbao.update.utils.UpdateManager;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.commonlib.router.RouterHelper;
import com.yuanbaogo.datacenter.constant.WebUrls;
import com.yuanbaogo.libbase.baseutil.ClickUtils;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.SettingItem;

@Route(path = YBRouter.ABOUT_ACTIVITY)
public class AboutActivity extends MvpBaseActivityImpl<AboutContract.View, AboutPresenter> implements View.OnClickListener, AboutContract.View {

    private SettingItem mAboutSiUpdate;
    private SettingItem mAboutSiStatement;
    private SettingItem mAboutSiAgreement;
    private SettingItem mAboutSiPrivacyPolicy;
    private SettingItem mAboutSiRealName;
    private SettingItem mAboutSiPay;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_about;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mAboutSiUpdate = findViewById(R.id.about_si_update);
        mAboutSiStatement = findViewById(R.id.about_si_statement);
        mAboutSiAgreement = findViewById(R.id.about_si_agreement);
        mAboutSiPrivacyPolicy = findViewById(R.id.about_si_privacy_policy);
        mAboutSiRealName = findViewById(R.id.about_si_real_name);
        mAboutSiPay = findViewById(R.id.about_si_pay);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAboutSiUpdate.setOnClickListener(this);
        mAboutSiStatement.setOnClickListener(this);
        mAboutSiAgreement.setOnClickListener(this);
        mAboutSiPrivacyPolicy.setOnClickListener(this);
        mAboutSiRealName.setOnClickListener(this);
        mAboutSiPay.setOnClickListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.about_si_update) {
            // 版本更新
            versionUpdate();
        } else if (id == R.id.about_si_statement) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            // 特别声明
            RouterHelper.toWebJs(WebUrls.proSpecial, true);
        } else if (id == R.id.about_si_agreement) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            // 用户服务协议
            // AgreementActivity.actionStart(this);
            RouterHelper.toWebJs(WebUrls.proUser, true);
        } else if (id == R.id.about_si_privacy_policy) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            // 隐私政策
            // PrivacyPolicyActivity.actionStart(this);
            RouterHelper.toWebJs(WebUrls.proPrivacy, true);
        } else if (id == R.id.about_si_real_name) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            // 实名认证协议
            RouterHelper.toWebJs(WebUrls.proRealproto, true);
        } else if (id == R.id.about_si_pay) {
            //防暴力点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            // 元宝支付服务协议
            RouterHelper.toWebJs(WebUrls.proPayproto, true);
        }
    }

    /**
     * 版本升级
     */
    private void versionUpdate() {
        mPresenter.getNewVersion();
    }

    @Override
    public void showNewVersion(ApkInfoModel apkInfoModel) {
        if (!apkInfoModel.getTerminal().contains("android")) {
            ToastView.showToast("下载应用文件失败");
            return;
        }
        // 这块还需要对比下当前版本号和下拉下来服务器中软件的版本号
//        int versionCode = APKInfoUtil.getVersionCode(this);
//        int versionNumber = Integer.parseInt(apkInfoModel.getVersionNumber());
        if (apkInfoModel.isIsNeedUpgrade()) {
            checkUpdate(apkInfoModel);
        }else{
            ToastView.showToast(R.string.about_update_new_toast);
        }
    }

    /**
     * 检查更新
     *
     * @param apkInfoModel /
     */
    private void checkUpdate(ApkInfoModel apkInfoModel) {
        // 更新的数据参数
        AppUpdate appUpdate = new AppUpdate.Builder()
                //更新地址（必传）
                .newVersionUrl(apkInfoModel.getUpdateUrl())
                // 版本号（非必填）
                .newVersionCode("v" + apkInfoModel.getVersionNumber())
                // 通过传入资源id来自定义更新对话框，注意取消更新的id要定义为btnUpdateLater，立即更新的id要定义为btnUpdateNow（非必填）
                .updateResourceId(R.layout.dialog_update)
                // 更新的标题，弹框的标题（非必填，默认为应用更新）
                .updateTitle(R.string.update_title)
                // 更新内容的提示语，内容的标题（非必填，默认为更新内容）
                .updateContentTitle(R.string.update_content_lb)
                // 更新内容（非必填，默认“1.用户体验优化\n2.部分问题修复”）
                .updateInfo(apkInfoModel.getDescriptions())
                // 文件大小（非必填）
                //.fileSize("5.8M")
                // 保存文件路径（默认前缀：Android/data/包名/files/ 文件名：download）
                //.savePath("/A/B")
                //是否采取静默下载模式（非必填，只显示更新提示，后台下载完自动弹出安装界面），否则，显示下载进度，显示下载失败
                .isSilentMode(false)
                //是否强制更新（非必填，默认不采取强制更新，否则，不更新无法使用）
                .forceUpdate(apkInfoModel.isForcedUpgrade() ? 1 : 0)
                //文件的MD5值，默认不传，如果不传，不会去验证md5(非静默下载模式生效，若有值，且验证不一致，会启动浏览器去下载)
                //.md5("")
                .build();
        new UpdateManager().startUpdate(this, appUpdate);
    }

}
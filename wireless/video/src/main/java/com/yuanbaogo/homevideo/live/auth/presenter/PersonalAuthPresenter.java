package com.yuanbaogo.homevideo.live.auth.presenter;


import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.yuanbaogo.datacenter.url.http.ChildUrl;
import com.yuanbaogo.commonlib.utils.CertificateIdUtils;
import com.yuanbaogo.commonlib.utils.FileUtils;
import com.yuanbaogo.datacenter.remote.helper.NetWork;
import com.yuanbaogo.datacenter.remote.interfaces.RequestSateListener;
import com.yuanbaogo.datacenter.remote.model.UpLoadFileBean;
import com.yuanbaogo.homevideo.live.auth.contract.PersonalAuthContract;
import com.yuanbaogo.libbase.basemvp.MvpBasePersenter;
import com.yuanbaogo.libbase.baseutil.BitmapUtils;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.libbase.baseutil.ValidatePhoneUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lhx
 * @description:
 * @date : 2021/7/21 18:37
 */
public class PersonalAuthPresenter extends MvpBasePersenter<PersonalAuthContract.View> implements PersonalAuthContract.Presenter {
    private String mJoHand = "";
    private String mUrlHand = "";
    private String mBtnSubmitTxt = "立即认证";
    private String mBackPath = "";
    private String mFrontPath = "";
//    private String mBackMimeType = "";
//    private String mFrontMimeType = "";
//    private File mBackFile;
//    private File mFrontFile;

    private String mCurrentPhoto;
    List<UpLoadFileBean> fileList = new ArrayList();

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validateInput();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    public void validateInput() {
        if (isActive()) {
            String name = getView().getTextEtNameView();
            String phone = getView().getTextEtPhoneView();
            String idCard = getView().getTextEtCardView();
//            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(idCard) ||
//                    (mJoHand == null && mUrlHand == null)) {
//                getView().setBtncomitStatus(mBtnSubmitTxt, false);
//            } else {
//                getView().setBtncomitStatus(mBtnSubmitTxt, true);
//            }
        }
    }

    @Override
    public TextWatcher getTextWatcher() {
        return mTextWatcher;
    }

    @Override
    public void comit() {
        if (isActive()) {
            String name = getView().getTextEtNameView();
            String phone = getView().getTextEtPhoneView();
            String idCard = getView().getTextEtCardView();
            String errInfo = new CertificateIdUtils().IDCardValidate(idCard);
            if (!CertificateIdUtils.chineseNameTest(name) || name.length() < 2) {
                ToastUtil.showToast("姓名格式输入错误");
                return;
            }
            if (!TextUtils.isEmpty(errInfo)) {
                ToastUtil.showToast("证件号格式输入错误");
                return;
            }
            if (!ValidatePhoneUtil.validateMobileNumber(phone)) {
                ToastUtil.showToast("手机号格式错误");
                return;
            }
            if (TextUtils.isEmpty(mFrontPath)) {
                ToastUtil.showToast("请上传身份证正面照");
                return;
            }
            if (TextUtils.isEmpty(mBackPath)) {
                ToastUtil.showToast("请上传身份证背面照");
                return;
            }
            toAuthentication(name, phone, idCard);
        }
    }

    private class CompressTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            LodingProgressDialog.show(MinsuLandlordAuthInfoActivity.this, null, false, true);
        }

        @Override
        protected String doInBackground(String... params) {
//            String newPath = BitmapUtils.compressAndGenImage(params[1], params[0],
//                    BitmapUtils.getBitmap(params[0]), BitmapUtils.MAXPICSIZE);
            String newPath = BitmapUtils.compressAndGenImage(params[1], FileUtils.YUANBAO_DIR,
                    BitmapUtils.getBitmap(params[0]), BitmapUtils.MAXPICSIZE);
            return newPath;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            LodingProgressDialog.dismiss();
        }
    }

    @Override
    public void compressAndGenImage(File photoFile) {
        String newName = "yuanbao" + System.currentTimeMillis() + ".jpeg";
        CompressTask task = new CompressTask() {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                File target = new File(s);
                if (target.exists()) {
                    uploadPic(target.getAbsolutePath(), target, "image/jpeg");
                } else {
                    ToastUtil.showToast("图片获取失败");
                }
            }
        };
        task.execute(photoFile.getAbsolutePath(), newName);
    }

    @Override
    public void uploadPic(String path, File file, String mimeType) {
        UpLoadFileBean bean = new UpLoadFileBean();
        bean.setFileName(file.getName());
        bean.setMediaType(mimeType);
        bean.setPath(path);
        if (mCurrentPhoto.equals("front")) {
            if (isActive()) {
                getView().setCardFrontPicture(file);
            }
            mFrontPath = path;
//            mFrontMimeType = mimeType;
//            mFrontFile = file;
            bean.setFileKey("idCardFrontPicture");
        } else if (mCurrentPhoto.equals("back")) {
            if (isActive()) { //必须加
                getView().setCardBackPicture(file);
            }
            mBackPath = path;
//            mBackMimeType = mimeType;
//            mBackFile = file;
            bean.setFileKey("idCardBackPicture");
        }
        fileList.add(bean);
        getView().setBtncomitStatus(mBtnSubmitTxt, true);
    }

    private void toAuthentication(String name, String phone, String idCard) {
        Map<String, String> params = new HashMap<>();
        params.put("realName", name);
        params.put("phone", phone);
        params.put("idCard", idCard);

        NetWork.getInstance().upLoadFile(getContext(), ChildUrl.authentication, params, fileList, new RequestSateListener<String>() {
            @Override
            public void onSuccess(int code, String str) {
                getView().showStatus(true);
            }

            @Override
            public void onFailure(Throwable e) {
                getView().showStatus(false);
            }
        });
//        NetWork.getInstance().upload(fileidCardBackPicture,fileidCardFrontPicture);
    }


    @Override
    public void setCurrentPhoto(String type) {
        mCurrentPhoto = type;
    }

}

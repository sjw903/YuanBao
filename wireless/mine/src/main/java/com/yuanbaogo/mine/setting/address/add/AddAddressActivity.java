package com.yuanbaogo.mine.setting.address.add;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.utils.TextUtils;
import com.github.gzuliyujiang.wheelpicker.AddressPicker;
import com.github.gzuliyujiang.wheelpicker.annotation.AddressMode;
import com.github.gzuliyujiang.wheelpicker.contract.OnAddressPickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.CityEntity;
import com.github.gzuliyujiang.wheelpicker.entity.CountyEntity;
import com.github.gzuliyujiang.wheelpicker.entity.ProvinceEntity;
import com.permissionx.guolindev.PermissionX;
import com.yuanbaogo.commonlib.base.MvpBaseActivityImpl;
import com.yuanbaogo.libbase.baseutil.GsonUtil;
import com.yuanbaogo.libbase.baseutil.ToastUtil;
import com.yuanbaogo.libbase.baseutil.ValidatePhoneUtil;
import com.yuanbaogo.mine.R;
import com.yuanbaogo.mine.setting.model.AddressBean;
import com.yuanbaogo.router.YBRouter;
import com.yuanbaogo.zui.toast.ToastView;
import com.yuanbaogo.zui.view.TitleBar;

@Route(path = YBRouter.ADD_ADDRESS_ACTIVITY)
public class AddAddressActivity extends MvpBaseActivityImpl<AddAddressContract.View, AddAddressPresenter> implements AddAddressContract.View, View.OnClickListener, TextWatcher, OnAddressPickedListener {

    public static final int ADDRESS_BOOK_RESULT = 0x30;
    private TitleBar mAddAddressTitleBar;
    private EditText mAddAddressEtName;
    private TextView mAddAddressTvBook;
    private EditText mAddAddressEtPhone;
    private TextView mAddAddressTvArea;
    private EditText mAddAddressEtDetails;
    private SwitchCompat mAddAddressSwDefault;
    private TextView mAddAddressTvAdd;
    private String mProvince, mCity, mCounty;

    @Autowired(name = YBRouter.AddressActivityParams.addressBean)
    String addressBean = "";
    private AddressBean mAddressBean = new AddressBean();
    private String mName;
    private String mPhone;
    private String mDetails;

    @Override
    protected int createContentView(Bundle savedInstanceState) {
        return R.layout.mine_activity_add_address;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        mAddAddressTitleBar = findViewById(R.id.add_address_title_bar);
        mAddAddressEtName = findViewById(R.id.add_address_et_name);
        mAddAddressTvBook = findViewById(R.id.add_address_tv_book);
        mAddAddressEtPhone = findViewById(R.id.add_address_et_phone);
        mAddAddressTvArea = findViewById(R.id.add_address_tv_area);
        mAddAddressEtDetails = findViewById(R.id.add_address_et_details);
        mAddAddressSwDefault = findViewById(R.id.add_address_sw_default);
        mAddAddressTvAdd = findViewById(R.id.add_address_tv_add);
    }

    @Override
    protected void bindListeners(Bundle savedInstanceState) {
        mAddAddressTvBook.setOnClickListener(this);
        mAddAddressTvAdd.setOnClickListener(this);
        mAddAddressTvArea.setOnClickListener(this);
        mAddAddressEtName.addTextChangedListener(this);
        mAddAddressEtPhone.addTextChangedListener(this);
        mAddAddressEtDetails.addTextChangedListener(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        if (addressBean.equals("") || TextUtils.isEmpty(addressBean)) {
            mPresenter.getDefaultAddress();
            return;
        }
        mAddressBean = GsonUtil.GsonToBean(addressBean, AddressBean.class);
        mAddAddressTitleBar.setTitle(getString(R.string.update_address_title));
        mAddAddressTvArea.setText(String.format("%s %s %s",
                mAddressBean.getProvince() == null ? "" : mAddressBean.getProvince(),
                mAddressBean.getCity() == null ? "" : mAddressBean.getCity(),
                mAddressBean.getCountry() == null ? "" : mAddressBean.getCountry()));
        mAddAddressEtDetails.setText(mAddressBean.getAddressDetails());
        mAddAddressEtName.setText(mAddressBean.getContactsName());
        mAddAddressEtPhone.setText(mAddressBean.getContactsPhone());
        mProvince = mAddressBean.getProvince();
        mCity = mAddressBean.getCity();
        mCounty = mAddressBean.getCountry();
        mAddAddressSwDefault.setChecked(mAddressBean.getDefaulted() != null && mAddressBean.getDefaulted());
        initCheck();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_address_tv_book) {
            // ?????????
            PermissionX.init(this)
                    .permissions(Manifest.permission.READ_CONTACTS)
                    .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83e8dd"))
                    .onExplainRequestReason((scope, deniedList) -> {
                        String message = "?????????????????????????????????????????????";
                        scope.showRequestReasonDialog(deniedList, message, "??????", "??????");
                    })
                    .request((allGranted, grantedList, deniedList) -> {
                        if (allGranted) {
                            // ???????????????????????????????????????
                            intentToContact();
                        } else {
                            ToastUtil.showToast("????????????????????????????????????????????????");
                        }
                    });

        } else if (id == R.id.add_address_tv_add) {
            if (!ValidatePhoneUtil.validateMobileNumber(mPhone)) {
                ToastView.showToast("???????????????????????????");
                return;
            }
            if (mAddAddressTvArea.getText().toString().equals("???????????????")) {
                ToastView.showToast("???????????????");
                return;
            }
            mAddressBean.setContactsName(mName);
            mAddressBean.setContactsPhone(mPhone);
            mAddressBean.setAddressDetails(mDetails);
            mAddressBean.setProvince(mProvince);
            mAddressBean.setCity(mCity);
            mAddressBean.setCountry(mCounty);
            mAddressBean.setDefaulted(mAddAddressSwDefault.isChecked());
            mAddressBean.setDefaulted(mAddAddressSwDefault.isChecked());
            mPresenter.addAddress(mAddressBean);
        } else if (id == R.id.add_address_tv_area) {
            // ??????????????????
            AddressPicker picker = new AddressPicker(this);
            picker.setDefaultValue(mProvince, mCity, mCounty);
            picker.setAddressMode(AddressMode.PROVINCE_CITY_COUNTY);
            picker.setOnAddressPickedListener(this);
            picker.show();
        }
    }

    private void intentToContact() {
        // ????????????????????????
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, ADDRESS_BOOK_RESULT);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String editable = mAddAddressEtPhone.getText().toString().trim();
        if (editable.length() == 1 && !editable.equals("1")) {
            if (isActive()) {
                mAddAddressEtPhone.setText("");
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        initCheck();
    }

    private void initCheck() {
        mName = mAddAddressEtName.getText().toString().trim();
        mPhone = mAddAddressEtPhone.getText().toString().trim();
        mDetails = mAddAddressEtDetails.getText().toString().trim();
        mAddAddressTvAdd.setEnabled(!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mDetails));
    }

    @Override
    public void addFinish() {
        if (mAddressBean.getUserId() != null && mAddressBean.getUserId() > 0) {
            ToastView.showToast("????????????????????????");
        } else {
            ToastView.showToast("????????????????????????");
        }
        finish();
    }

    @Override
    public void addFail(String error) {
        ToastUtil.showToast(error);
    }

    @Override
    public void hasDefaultAddress(boolean hasDefault) {
        mAddAddressSwDefault.setChecked(!hasDefault);
    }

    @Override
    public void onAddressPicked(ProvinceEntity province, CityEntity city, CountyEntity county) {
        mProvince = province.getName();
        mCity = city != null ? city.getName() : mProvince;
        mCounty = county != null ? county.getName() : mProvince;
        mAddAddressTvArea.setText(String.format("%s %s %s", mProvince, mCity, mCounty));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != ADDRESS_BOOK_RESULT || data == null) {
            return;
        }
        Uri uri = data.getData();
        String phoneNum = null;
        String contactName = null;
        // ?????????????????????
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = null;
        if (uri != null) {
            cursor = contentResolver.query(uri,
                    new String[]{"display_name", "data1"}, null, null, null);
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            cursor.close();
        }
        //  ?????????????????????  -  ?????? ???????????????
        if (phoneNum != null) {
            phoneNum = phoneNum.replaceAll("-", " ");
            // ????????????  ??????????????????-?????????"" ????????????????????????????????????????????? ??????????????????
            phoneNum = phoneNum.replaceAll(" ", "");
        }

        mPhone = phoneNum;
        mName = contactName;
        mAddAddressEtName.setText(contactName);
        mAddAddressEtPhone.setText(phoneNum);
    }

}
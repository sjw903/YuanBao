package com.yuanbaogo.datacenter.local;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.mmkv.MMKV;

public class SharePreferenceConfigImpl implements SharePreferenceConfig {
    private static SharePreferenceConfigImpl mSharePreferenceConfigImpl;
    private MMKV mmkv = null;
    private Context mContext;

    public SharePreferenceConfigImpl() {
    }

    public static SharePreferenceConfigImpl getSharePreferenceConfigImpl() {
        return mSharePreferenceConfigImpl;
    }

    public SharePreferenceConfigImpl(Context context) {
        this.mContext = context;
        String dirs = context.getFilesDir().getAbsolutePath() + "/yuanbaoConfig";
        MMKV.initialize(dirs);//初始化mmkv
    }

    public static SharePreferenceConfigImpl getInstance(Context context){
        if (mSharePreferenceConfigImpl == null){
            synchronized (SharePreferenceConfigImpl.class){
                if (mSharePreferenceConfigImpl == null){
                    mSharePreferenceConfigImpl = new SharePreferenceConfigImpl(context);
                }
            }
        }
        return mSharePreferenceConfigImpl;
    }

    @Override
    public void loadConfig() {
        mmkv = MMKV.mmkvWithID("YBSharePreference", MMKV.MULTI_PROCESS_MODE);
    }

    @Override
    public void loadConfig(String mmkvName) {
        if(TextUtils.isEmpty(mmkvName)){
            loadConfig();
        }else{
            mmkv = MMKV.mmkvWithID(mmkvName, MMKV.MULTI_PROCESS_MODE);
        }
    }

    @Override
    public void loadConfigCRYPTKEY(String mmkvName) {
        if(TextUtils.isEmpty(mmkvName)){
            loadConfig();
        }else{
            mmkv = MMKV.mmkvWithID(mmkvName, MMKV.MULTI_PROCESS_MODE, CRYPTKEY);
        }
    }

    @Override
    public void setString(String key, String value) {
        mmkv.encode(key,value);
    }

    @Override
    public void setInt(String key, int value) {
        mmkv.encode(key,value);
    }

    @Override
    public void setBoolean(String key, Boolean value) {
        mmkv.encode(key,value);
    }

    @Override
    public void setByte(String key, byte[] value) {
        this.setString(key, String.valueOf(value));
    }

    @Override
    public void setShort(String key, short value) {
        this.setString(key, String.valueOf(value));
    }

    @Override
    public void setLong(String key, long value) {
        mmkv.encode(key,value);
    }

    @Override
    public void setFloat(String key, float value) {
        mmkv.encode(key,value);
    }

    @Override
    public void setDouble(String key, double value) {
        this.setString(key, String.valueOf(value));
    }

    @Override
    public void setString(int resID, String value) {
        this.setString(this.mContext.getString(resID), value);
    }

    @Override
    public void setInt(int resID, int value) {
        this.setInt(this.mContext.getString(resID), value);
    }

    @Override
    public void setBoolean(int resID, Boolean value) {
        this.setBoolean(this.mContext.getString(resID), value);
    }

    @Override
    public void setByte(int resID, byte[] value) {
        this.setByte(this.mContext.getString(resID), value);
    }

    @Override
    public void setShort(int resID, short value) {
        this.setShort(this.mContext.getString(resID), value);
    }

    @Override
    public void setLong(int resID, long value) {
        this.setLong(this.mContext.getString(resID), value);
    }

    @Override
    public void setFloat(int resID, float value) {
        this.setFloat(this.mContext.getString(resID), value);
    }

    @Override
    public void setDouble(int resID, double value) {
        this.setDouble(this.mContext.getString(resID), value);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return mmkv.decodeString(key,defaultValue);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return mmkv.decodeInt(key,defaultValue);
    }

    @Override
    public boolean getBoolean(String key, Boolean defaultValue) {
        return mmkv.decodeBool(key,defaultValue);
    }

    @Override
    public byte[] getByte(String key, byte[] defaultValue) {
        try {
            return this.getString(key, "").getBytes();
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    @Override
    public short getShort(String key, Short defaultValue) {
        try {
            return Short.valueOf(this.getString(key, ""));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    @Override
    public long getLong(String key, Long defaultValue) {
        return  mmkv.decodeLong(key, defaultValue);
    }

    @Override
    public float getFloat(String key, Float defaultValue) {
        return  mmkv.decodeFloat(key, defaultValue);
    }

    @Override
    public double getDouble(String key, Double defaultValue) {
        try {
            return Double.valueOf(this.getString(key, ""));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    @Override
    public String getString(int resID, String defaultValue) {
        return this.getString(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public int getInt(int resID, int defaultValue) {
        return this.getInt(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public boolean getBoolean(int resID, Boolean defaultValue) {
        return this.getBoolean(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public byte[] getByte(int resID, byte[] defaultValue) {
        return this.getByte(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public short getShort(int resID, Short defaultValue) {
        return this.getShort(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public long getLong(int resID, Long defaultValue) {
        return this.getLong(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public float getFloat(int resID, Float defaultValue) {
        return this.getFloat(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public double getDouble(int resID, Double defaultValue) {
        return this.getDouble(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public void remove(String key) {
        mmkv.removeValueForKey(key);
    }

    @Override
    public void remove(String... keys) {
        String[] var2 = keys;
        int var3 = keys.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            this.remove(key);
        }

    }

    @Override
    public void clear() {
        mmkv.clearAll();
    }
}

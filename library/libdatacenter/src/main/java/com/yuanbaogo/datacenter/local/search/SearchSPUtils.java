package com.yuanbaogo.datacenter.local.search;

import android.content.Context;
import android.util.Log;

import com.yuanbaogo.datacenter.local.SharePreferenceConfigImpl;
import com.yuanbaogo.libbase.config.ApplicationConfigHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 7/12/21 1:39 PM
 * @Modifier:
 * @Modify:
 */
public class SearchSPUtils {

    public static Context mContext;

    private static SearchSPUtils searchSPUtils = new SearchSPUtils();

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "yuanbaogo";

    /**
     * 搜索历史记录-商城 文件名
     */
    public static final String SEARCH_HISTORY_SHOP = "search_history_shop";

    /**
     * 搜索历史记录-短视频 文件名
     */
    public static final String SEARCH_HISTORY_VIDEO = "search_history_video";

    /**
     * 保存的sp文件名称
     */
    public static SearchSPUtils getInstance(Context context) {
        mContext = context;
        SharePreferenceConfigImpl.getInstance(mContext).loadConfig(FILE_NAME);
        return searchSPUtils;
    }

    /**
     * 保存搜索记录
     *
     * @param keyword
     */
    public static void save(String name, String keyword) {
        // 获取搜索框信息
        String old_text = SharePreferenceConfigImpl.getInstance(mContext).getString(name, "");
        // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
        StringBuilder builder = new StringBuilder(old_text);
        builder.append(keyword + ",");
        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
        if (!old_text.contains(keyword + ",")) {
            SharePreferenceConfigImpl.getInstance(mContext).setString(name, builder.toString());
        } else {
            //删除已存在的
            getHistoryDeleteItem(name, keyword);
            //重新查询并添加
            String history = SharePreferenceConfigImpl.getInstance(mContext).getString(name, "");
            StringBuilder builder2 = new StringBuilder(history);
            builder2.append(keyword + ",");
            SharePreferenceConfigImpl.getInstance(mContext).setString(name, builder2.toString());
        }
    }

    /**
     * 获取历史记录
     *
     * @return
     */
    public static List<String> getHistoryList(String name) {
        // 获取搜索记录文件内容
        String history = SharePreferenceConfigImpl.getInstance(mContext).getString(name, "");
        // 用逗号分割内容返回集合
        List<String> history_arr = Arrays.asList(history.split(","));//根据逗号分隔转化为list
        Collections.reverse(history_arr);
        if (history_arr.size() > 21) {
            return history_arr.subList(0, 20);
        }
        return history_arr;
    }

    /**
     * 删除单条数据
     *
     * @param deleteItem
     * @return
     */
    public static void getHistoryDeleteItem(String name, String deleteItem) {
        // 获取搜索记录文件内容
        String history = SharePreferenceConfigImpl.getInstance(mContext).getString(name, "");
        String newStr = history.replace(deleteItem + ",", ""); //得到新的字符串
        SharePreferenceConfigImpl.getInstance(mContext).setString(name, newStr);
    }

    /**
     * 清除搜索记录
     */
    public static void cleanHistory(String name) {
        SharePreferenceConfigImpl.getInstance(mContext).remove(name);
    }
}

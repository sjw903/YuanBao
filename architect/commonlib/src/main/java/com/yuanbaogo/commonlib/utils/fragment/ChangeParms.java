package com.yuanbaogo.commonlib.utils.fragment;

/**
 * @Description: 创建接口  回调到MainActivity
 * @Params:
 * @Problem:
 * @Author: HG
 * @Date: 8/31/21 5:12 PM
 * @Modifier:
 * @Modify:
 */
public class ChangeParms {

    /**
     * 改变选中Frangment的接口
     */
    public static ChangeFragment sChangeFragment;

    /**
     * 设置被选中的Fragment
     *
     * @param changeFragment
     */
    public static void setFragmentSelected(ChangeFragment changeFragment) {
        sChangeFragment = changeFragment;
    }

}

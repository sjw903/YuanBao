package com.tencent.qcloud.ugckit.module.effect.paster.view;

import android.content.Context;
import android.view.View;

import com.tencent.qcloud.ugckit.R;

import androidx.annotation.NonNull;


/**
 * 创建 OperationView的工厂
 */
public class TCPasterOperationViewFactory {

    @NonNull
    public static PasterView newOperationView(Context context) {
        return (PasterView) View.inflate(context, R.layout.ugckit_layout_paster_operation_view, null);
    }
}

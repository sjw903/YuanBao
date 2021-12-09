package com.yuanbaogo.homevideo.live.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yuanbaogo.homevideo.R;
import com.yuanbaogo.libbase.baseutil.DateUtil;
import com.yuanbaogo.libbase.view.BaseActivity;

public class EndingStoryActivity extends BaseActivity {


    public static void toEndingStory(Context ctx, int type, String portraitUrl, String anchorNickName,
                                     String mCharm, String mVisitors,String fansNew, String orderCount,
                                     String totalAmount, String commissionAmount, String time) {

        Intent intent = new Intent(ctx, EndingStoryActivity.class);
        intent.putExtra("type", type);

        intent.putExtra("time", time);
        intent.putExtra("headurl", portraitUrl);
        intent.putExtra("name", anchorNickName);

        intent.putExtra("charm_num", mCharm);
        intent.putExtra("viewer_num", mVisitors);
        intent.putExtra("fans_num", fansNew);

        intent.putExtra("order_num", orderCount);
        intent.putExtra("order_sum", totalAmount);
        intent.putExtra("money_sum", commissionAmount);

        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending_story);
        initView();
        setStatusBarTransparent();
    }

    private ImageView iv_back;
    private ImageView iv_head;
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_name;
    private LinearLayout ll_charm;
    private TextView tv_charm_num;
    private TextView tv_viewer_num;
    private TextView tv_fans_num;
    private LinearLayout ll_market;
    private TextView tv_order_num;
    private TextView tv_order_sum;
    private TextView tv_money_sum;

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_title = (TextView) findViewById(R.id.tv_title);

        iv_head = (ImageView) findViewById(R.id.iv_head);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_name = (TextView) findViewById(R.id.tv_name);

        ll_charm = (LinearLayout) findViewById(R.id.ll_charm);
        tv_charm_num = (TextView) findViewById(R.id.tv_charm_num);
        tv_viewer_num = (TextView) findViewById(R.id.tv_viewer_num);
        tv_fans_num = (TextView) findViewById(R.id.tv_fans_num);

        ll_market = (LinearLayout) findViewById(R.id.ll_market);
        tv_order_num = (TextView) findViewById(R.id.tv_order_num);
        tv_order_sum = (TextView) findViewById(R.id.tv_order_sum);
        tv_money_sum = (TextView) findViewById(R.id.tv_money_sum);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int type = getIntent().getIntExtra("type", 0);

        String time = getIntent().getStringExtra("time");
        String headurl = getIntent().getStringExtra("headurl");
        String name = getIntent().getStringExtra("name");

        String charm_num = getIntent().getStringExtra("charm_num");
        String viewer_num = getIntent().getStringExtra("viewer_num");
        String fans_num = getIntent().getStringExtra("fans_num");

        String order_num = getIntent().getStringExtra("order_num");
        String order_sum = getIntent().getStringExtra("order_sum");
        String money_sum = getIntent().getStringExtra("money_sum");

        tv_time.setText(DateUtil.dateCalculateTime(time));
        Glide.with(this).load(headurl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(iv_head);
        tv_name.setText(name);

        tv_charm_num.setText(charm_num);
        tv_viewer_num.setText(viewer_num);
        tv_fans_num.setText(fans_num);

        tv_order_num.setText(order_num);
        tv_order_sum.setText(Integer.parseInt(order_sum)/100+"");
        tv_money_sum.setText(Integer.parseInt(money_sum)/100+"");

        if (type == 0) { //观众退出
            ll_charm.setVisibility(View.GONE);
            ll_market.setVisibility(View.GONE);
            tv_title.setText("本场直播已结束");
        } else if (type == 1) {//主播不带货
            ll_market.setVisibility(View.GONE);
        }


    }
    /**
     * 设置透明状态栏
     */
    private void setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            //设置修改状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏的颜色，和你的APP主题或者标题栏颜色一致就可以了
            window.setStatusBarColor(getResources().getColor(R.color.color212121));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

}
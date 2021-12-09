package com.yuanbaogo.homevideo.live.pull.adapter;

/**
 * zspace印象列表 Adapter
 * Created by yangf on 2018/12/11.
 * <p>
 * 注释掉 footer 实现方案 2018.12.27 暂时保留
 */
//public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private Context context;
//    private ArrayList<ZspaceImpressHomeListBean.ListBean> mList;
//    private List<ZspaceImpressHomeHeaderBean.BannerBean> headerBannerList;
//    private String projectFid;
//    private OnItemClickListener onItemClickListener;
////    private boolean mShowLoadMore = true;
//
//    public interface OnItemClickListener {
//
//        void onTabClick(int position);
//
//        void onItmClick(String impressFid, int position);
//
//        void onItmeViewClick(int position, ArrayList<ZspaceImpressHomeListBean.ListBean> list);
//    }
//
//    public MessageListAdapter(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v;
//        if (viewType == TYPE_ITEM) {
//            v = LayoutInflater.from(context).inflate(R.layout.sojourn_zspace_item_ugchome_list, parent, false);
//            return new ItemViewHolder(v);
//        } else if (viewType == TYPE_HEADER) {
//            v = LayoutInflater.from(context).inflate(R.layout.sojourn_zspace_item_list_header, parent, false);
//            return new HeaderViewHolder(v);
////        } else if (viewType == TYPE_FOOTER) {
////            v = LayoutInflater.from(context).inflate(R.layout.sojourn_zspace_item_list_footer, parent, false);
////            return new FooterViewHolder(v);
//        }
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder h, int position, List payloads) {
//        if (payloads.isEmpty()) {
//            onBindViewHolder(h, position);
//        } else {
//            if (h instanceof HeaderViewHolder) {
//                String datacode = (String) payloads.get(0);
//                HeaderViewHolder holder = (HeaderViewHolder) h;
//                if ("owner_pic".equals(datacode)) {
//                    if (!StringUtil.isNull(LoginStatusHelper.getUid()) && ApplicationConfigHelper.getUserInfo() != null) {
//                        holder.pv_owner_pic.setImageUri(ApplicationConfigHelper.getUserInfo().getHeadImg());
//                    } else {
//                        holder.pv_owner_pic.setPlaceHolderImage(context.getResources().getDrawable(R.drawable.icon_zsapce_user_initial));
//                    }
//                    holder.pv_owner_pic.display();
//                } else if ("selectTab".equals(datacode)) {
//                    holder.ctl_tab_container.getTabAt(1).select();
//                    onItemClickListener.onTabClick(1);
//                }
//            } else {
//                if (h instanceof ItemViewHolder) {
//                    ItemViewHolder holder = (ItemViewHolder) h;
//                    ZspaceImpressHomeListBean.ListBean bean = mList.get(getRealPos(position));
//                    refreshPraise(holder, bean);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
//        if (h instanceof ItemViewHolder) {
//            ItemViewHolder holder = (ItemViewHolder) h;
//            final ZspaceImpressHomeListBean.ListBean bean = mList.get(getRealPos(position));
//            if (bean == null) {
//                return;
//            }
//            if (!ListUtil.isEmpty(bean.getPicList())) {
//                ZspaceImpressHomeListBean.ListBean.PicListBean pb = bean.getPicList().get(0);
//                if (pb != null) {
//                    ViewGroup.LayoutParams params = holder.img_ugclist_item.getLayoutParams();
//                    double ratio = (double) pb.getHeightPixel() / pb.getWidthPixel();
//                    int width = (DeviceUtil.getScreenWidth() - DensityUtil.dip2px(context, 50)) / 2;
//                    int height = (int) (width * ratio);
//                    if (height > DensityUtil.dip2px(context, 260)) {
//                        height = DensityUtil.dip2px(context, 260);
//                    }
//                    params.height = height;
//                    holder.img_ugclist_item.setLayoutParams(params);
//
//                    holder.img_ugclist_item.setImageUri(pb.getPicUrl() == null ? "" : pb.getPicUrl());
//                    if (pb.getIsAbnormal() == 1) { //鉴黄图 ，模糊
//                        holder.img_ugclist_item.setImageBlur(16);
//                    }
//                    holder.img_ugclist_item.display();
//                }
//            }
//
//            List<String> labelList = bean.getLabelList();
//            if (!ListUtil.isEmpty(labelList)) {
//                holder.ll_ugclist_item_lable.setVisibility(View.VISIBLE);
//                holder.ll_ugclist_item_lable.setTags(labelList);
//            } else {
//                holder.ll_ugclist_item_lable.setVisibility(View.GONE);
//            }
//
//            holder.tv_img_ugclist_item_content.setText(bean.getContent());
//            refreshPraise(holder, bean);
//            holder.ll_like_selected.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String imei = DeviceUtil.getDeviceId();
//                    if (imei == null) {
//                        if (!LoginInfo.getLoginState(context)) {
//                            LoginInfo.startLoginActivity(context);
//                            return;
//                        }
//                    }
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onItmClick(bean.getImpressFid(), position);
//                    }
//                }
//            });
//            holder.ugclist_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onItmeViewClick(getRealPos(position), mList);
//                    }
//                }
//            });
//        } else if (h instanceof HeaderViewHolder) {
//            HeaderViewHolder holder = (HeaderViewHolder) h;
//
//            if (LoginStatusHelper.getLoginState()) {
//                String url = "";
//                if (!StringUtil.isNull(LoginStatusHelper.getUid()) && ApplicationConfigHelper.getUserInfo() != null) {
//                    url = ApplicationConfigHelper.getUserInfo().getHeadImg();
//                }
//                holder.pv_owner_pic.setImageUri(url);
//                holder.pv_owner_pic.display();
//            }
//            if (headerBannerList != null && headerBannerList.size() > 0) {
//                holder.mBanner.setVisibility(View.VISIBLE);
//                holder.mBannerIndicator.setVisibility(View.VISIBLE);
//                int round = com.freelxl.baselibrary.util.DensityUtil.dip2px(context, 2);
//                holder.mBanner.setRadius(round, round, round, round);
//                holder.mBanner.setPointViewVisible(false);
//                if (headerBannerList.size() == 1) {
//                    holder.mBannerIndicator.setVisibility(View.GONE);
//                    holder.mBanner.setVisibility(View.VISIBLE);
//                    holder.mBanner.setCanLoop(false);
//                } else {
//                    holder.mBannerIndicator.setVisibility(View.VISIBLE);
//                    holder.mBanner.setVisibility(View.VISIBLE);
//                    holder.mBanner.setCanLoop(true);
//                }
//                holder.mBannerIndicator.setViewPager(holder.mBanner.getViewPager());
//                holder.mBannerIndicator.setRealCount(headerBannerList.size());
//                List<String> urls = new ArrayList<>();
//                for (ZspaceImpressHomeHeaderBean.BannerBean bannerBean : headerBannerList) {
//                    urls.add(HttpPrefixAddUtil.addScheme(bannerBean.getImg()));
//                }
//                holder.mBanner.setPages(new CBViewHolderCreator<BannerImageHolderView>() {
//                    @Override
//                    public BannerImageHolderView createHolder() {
//                        return new BannerImageHolderView();
//                    }
//                }, urls)
//                        .setOnItemClickListener(new com.ziroom.commonlibrary.widget.convenientbanner.listener.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(int position) {
//                                if (!TextUtils.isEmpty(headerBannerList.get(position).getTarget())) {
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("url", headerBannerList.get(position).getTarget());
//                                    bundle.putString("shareImg", headerBannerList.get(position).getImg());
//                                    Routers.open(context, com.commonlib.ziroomrountesscheme.rent.ConstantValues.DataUrls.H5WJ_FUNCTION, bundle);
//                                }
//                            }
//                        })
//                        .setPointViewVisible(true);
//                holder.mBanner.startTurning(3000);
//            } else {
//                holder.mBanner.setVisibility(View.GONE);
//                holder.mBannerIndicator.setVisibility(View.GONE);
//                holder.mBanner.setCanLoop(false);
//            }
////        } else if (h instanceof FooterViewHolder) {
////            FooterViewHolder holder = (FooterViewHolder) h;
////            if (mShowLoadMore) {
////                switch (mLoadMoreStatus) {
////                    case PULL_UP_LOAD_MORE:
////                        holder.tv_load_more.setText(context.getResources().getString(R.string.sojourn_zspace_srl_footer_pulling));
////                        break;
////                    case LOADING_MORE:
////                        holder.tv_load_more.setText(context.getResources().getString(R.string.sojourn_zspace_srl_header_refreshing));
////                        break;
////                }
////            } else {
////                holder.tv_load_more.setText(context.getResources().getString(R.string.sojourn_zspace_srl_footer_nothing));
////            }
//        }
//    }
//
//    private void refreshPraise(ItemViewHolder holder, ZspaceImpressHomeListBean.ListBean bean) {
//        if (bean.getPraiseCount() > 0) {
//            if (bean.getPraiseCount() > 10000) {
//                holder.masonry_item_title.setText(String.format("%.1f", bean.getPraiseCount() / 10000.0) + context.getResources().getString(R.string.sojourn_zspace_signW));
//            } else if (bean.getPraiseCount() > 1000) {
//                holder.masonry_item_title.setText(String.format("%.1f", bean.getPraiseCount() / 1000.0) + context.getResources().getString(R.string.sojourn_zspace_signK));
//            } else {
//                holder.masonry_item_title.setText(String.valueOf(bean.getPraiseCount()));
//            }
//        } else {
//            holder.masonry_item_title.setText(context.getResources().getString(R.string.sojourn_zspace_like));
//        }
//        if (bean.getIsPraise() == 1) {
//            holder.img_ugclist_item_hold.setImageResource(R.drawable.icon_zspace_like_18_selected);
//            holder.masonry_item_title.setTextColor(context.getResources().getColor(R.color.sojourn_zspace_color_CAB06C));
//        } else {
//            holder.img_ugclist_item_hold.setImageResource(R.drawable.icon_zspace_like_18_unselected);
//            holder.masonry_item_title.setTextColor(context.getResources().getColor(R.color.sojourn_zspace_color_999999));
//        }
//    }
//
//    private int getRealPos(int position) {
//        return position - 1;
//    }
//
//    @Override
//    public int getItemCount() {
//        int count = mList == null ? 0 : mList.size();
//        count++;// Header
////        if (count != 0) {
////            count++;// Footer
////        }
//        return count;
//    }
//
//    public void setData(ArrayList<ZspaceImpressHomeListBean.ListBean> list) {
//        this.mList = list;
//        notifyDataSetChanged();
//    }
//
//    public void addData(List<ZspaceImpressHomeListBean.ListBean> list) {
//        if (mList == null) {
//            mList = new ArrayList<>();
//        }
//        mList.addAll(list);
////        notifyDataSetChanged();
////        notifyItemInserted(mList.size());//通知插入了数据
//    }
//
//    public static final int TYPE_ITEM = 0;  //普通ItemView
//    public static final int TYPE_HEADER = 2;  //头布局
////    public static final int TYPE_FOOTER = 1;  //底部FootView
//
////    //上拉加载更多
////    public static final int PULL_UP_LOAD_MORE = 0;
////    //正在加载中
////    public static final int LOADING_MORE = 1;
////    //上拉加载更多状态-默认为0
////    private int mLoadMoreStatus = 0;
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return TYPE_HEADER;
////        } else if (position + 1 == getItemCount()) {
////            return TYPE_FOOTER;
//        } else {
//            return TYPE_ITEM;
//        }
//    }
//
//    @Override
//    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        if (isStaggeredGridLayout(holder)) {
//            handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
//        }
//    }
//
//    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
//        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
//        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
//            return true;
//        }
//        return false;
//    }
//
//    private void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
//        StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
////        if (getItemViewType(position) == TYPE_FOOTER || getItemViewType(position) == TYPE_HEADER) {
//        if (getItemViewType(position) == TYPE_HEADER) {
//            p.setFullSpan(true);
//        }
//    }
//
////    public void changeMoreStatus(int status) {
////        mLoadMoreStatus = status;
//    //        notifyDataSetChanged();
////    }
////
////    public void setCanLoadMore(boolean loadMore) {
////        this.mShowLoadMore = loadMore;
//    //        notifyDataSetChanged();
////    }
//
//    public void showHeader(List<ZspaceImpressHomeHeaderBean.BannerBean> list, String mFid) {
//        this.headerBannerList = list;
//        this.projectFid = mFid;
//        if (!ListUtil.isEmpty(mList)) notifyDataSetChanged();
//    }
//
//    public void updateSingleItem(int position, int datacode) {
//        if (!ListUtil.isEmpty(mList) && position > 0 && position < mList.size()) {
//            ZspaceImpressHomeListBean.ListBean bean = mList.get(getRealPos(position));
//            if (datacode == 1) {
//                bean.setIsPraise(1);
//                bean.setPraiseCount(bean.getPraiseCount() + 1);
//            } else {
//                bean.setIsPraise(0);
//                bean.setPraiseCount(bean.getPraiseCount() - 1);
//            }
//        }
//        notifyItemChanged(position, datacode);
//    }
//
//    public void updateSingleItem(ZspaceImpressDetailLikeEvent event) {
//        if (event.getEventType() == 1) {
//            ZspaceImpressHomeListBean.ListBean bean = getImpressImageBean(event.getImpressFid());
//            if (bean != null) {
//                bean.setIsPraise(event.isLike() ? 1 : -1);
//                bean.setPraiseCount(event.getLikeCount());
//                mList.set(mList.indexOf(bean), bean);
//                notifyItemRangeChanged(mList.indexOf(bean), mList.size());
//            }
//        }
//    }
//
//    private ZspaceImpressHomeListBean.ListBean getImpressImageBean(String fid) {
//        if (mList != null && mList.size() > 0) {
//            for (ZspaceImpressHomeListBean.ListBean bean : mList) {
//                if (bean != null && !TextUtils.isEmpty(bean.getImpressFid()) && TextUtils.equals(bean.getImpressFid(), fid)) {
//                    return bean;
//                }
//            }
//        }
//        return null;
//    }
//
//    public void updateOwnerPic() {
//        notifyItemChanged(0, "owner_pic");
//    }
//
//    public void selectTab() {
//        notifyItemChanged(0, "selectTab");
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
////    class FooterViewHolder extends RecyclerView.ViewHolder {
////        View v_root;
////        TextView tv_load_more;
////
////        public FooterViewHolder(View itemView) {
////            super(itemView);
////            v_root = itemView;
////            tv_load_more = (TextView) itemView.findViewById(R.id.sojourn_minsu_tv_load_more);
////        }
////    }
//
//    class HeaderViewHolder extends RecyclerView.ViewHolder {
//        View v_root;
//        RoundConvenientBanner mBanner;
//        FlatBubblePageIndicator mBannerIndicator;
//        TextView tv_my_ugc;
//        PictureView pv_owner_pic;
//        LinearLayout ll_my_pic;
//        TabLayout ctl_tab_container;
//
//        public HeaderViewHolder(View itemView) {
//            super(itemView);
//            v_root = itemView;
//            mBanner = (RoundConvenientBanner) itemView.findViewById(R.id.banner_ugchome);
//            mBannerIndicator = itemView.findViewById(R.id.banner_indicator);
//            tv_my_ugc = (TextView) itemView.findViewById(R.id.tv_my_ugc);
//            pv_owner_pic = itemView.findViewById(R.id.pv_owner_pic);
//            ll_my_pic = itemView.findViewById(R.id.ll_owner_pic);
//            ctl_tab_container = (TabLayout) itemView.findViewById(R.id.ctl_tab_container);
//
//            pv_owner_pic.setCornersRadius(100);
//            setTabIndicator(ctl_tab_container, 0, 20);
//            ctl_tab_container.addTab(ctl_tab_container.newTab().setText(context.getResources().getString(R.string.sojourn_zspace_ugc_home_title_hot)));
//            ctl_tab_container.addTab(ctl_tab_container.newTab().setText(context.getResources().getString(R.string.sojourn_zspace_ugc_home_title_new)));
//            ctl_tab_container.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                @Override
//                public void onTabSelected(TabLayout.Tab tab) {
//                    onItemClickListener.onTabClick(tab.getPosition());
//                }
//
//                @Override
//                public void onTabUnselected(TabLayout.Tab tab) {
//                    LogUtil.logBigData();
//                }
//
//                @Override
//                public void onTabReselected(TabLayout.Tab tab) {
//
//                }
//            });
//
//            ll_my_pic.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (!LoginInfo.getLoginState(context)) {
//                        LoginInfo.startLoginActivity(context);
//                        return;
//                    }
//                    IntentUtil.startMyImpressActivity(context, projectFid);
//                }
//            });
//        }
//    }
//
//    class ItemViewHolder extends RecyclerView.ViewHolder {
//        private RelativeLayout ugclist_item;
//        private LinearLayout ll_like_selected;
//        private PictureView img_ugclist_item;
//        private TagCloudView ll_ugclist_item_lable;
//        private TextView masonry_item_title;
//        private TextView tv_img_ugclist_item_content;
//        private ImageView img_ugclist_item_hold;
//
//        public ItemViewHolder(View itemView) {
//            super(itemView);
//            ugclist_item = (RelativeLayout) itemView.findViewById(R.id.ugclist_item);
//            img_ugclist_item = (PictureView) itemView.findViewById(R.id.img_ugclist_item);
//            ll_like_selected = (LinearLayout) itemView.findViewById(R.id.ll_like_selected);
//            ll_ugclist_item_lable = (TagCloudView) itemView.findViewById(R.id.ll_ugclist_item_lable);
//            img_ugclist_item_hold = (ImageView) itemView.findViewById(R.id.img_ugclist_item_hold);
//            masonry_item_title = (TextView) itemView.findViewById(R.id.masonry_item_title);
//            tv_img_ugclist_item_content = (TextView) itemView.findViewById(R.id.tv_img_ugclist_item_content);
//
//        }
//    }
//
//    /**
//     * 设置tablayout  下面的横线长度
//     * 线的宽度是根据 tabView的宽度来设置的
//     *
//     * @param tabLayout
//     * @param leftDip
//     * @param rightDip
//     */
//    public void setTabIndicator(final TabLayout tabLayout, final int leftDip, final int rightDip) {
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
//                    int left = com.freelxl.baselibrary.util.DensityUtil.dip2px(tabLayout.getContext(), leftDip);
//                    int right = com.freelxl.baselibrary.util.DensityUtil.dip2px(tabLayout.getContext(), rightDip);
//                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
//                        View tabView = mTabStrip.getChildAt(i);
//                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
//                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
//                        mTextViewField.setAccessible(true);
//                        TextView mTextView = (TextView) mTextViewField.get(tabView);
//                        tabView.setPadding(0, 0, 0, 0);
//                        int width = 0; // 字多宽线就多宽，所以测量mTextView的宽度
//                        width = mTextView.getWidth();
//                        if (width == 0) {
//                            mTextView.measure(0, 0);
//                            width = mTextView.getMeasuredWidth();
//                        }
//                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
//                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                        params.width = width;
//                        params.leftMargin = left;
//                        params.rightMargin = right;
//                        tabView.setLayoutParams(params);
//                        tabView.invalidate();
//                    }
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//}

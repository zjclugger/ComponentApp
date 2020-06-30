package com.zjclugger.buyer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.ui.adapter.ModuleAdapter;
import com.zjclugger.buyer.ui.adapter.NearbyBusinessesAdapter;
import com.zjclugger.buyer.ui.adapter.RecommendationAdapter;
import com.zjclugger.buyer.ui.banner.BannerImageAdapter;
import com.zjclugger.buyer.ui.banner.BannerNumIndicator;
import com.zjclugger.buyer.ui.goods.GoodsActivity;
import com.zjclugger.buyer.ui.shop.ShopListFragment;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.buyer.webapi.response.ModuleResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.JListItem;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.JSearchView;
import com.zjclugger.lib.view.recyclerview.JGridLayoutManager;
import com.zjclugger.lib.view.recyclerview.GridItemDecoration;
import com.zjclugger.router.utils.ARouterUtils;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.transformer.AlphaPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页@title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = "Home";
    @BindView(R.id.banner)
    Banner mBannerView;

    @BindView(R.id.search_view)
    JSearchView mSearchView;
    @BindView(R.id.module_recycler)
    RecyclerView mModuleRecyclerView;
    ModuleAdapter mModuleAdapter;
    List<ModuleResult> mModuleList = new ArrayList<>();
    //功能列表
    @BindView(R.id.function_recycler)
    RecyclerView mFunctionRecyclerView;
    ModuleAdapter mFunctionAdapter;
    List<ModuleResult> mFunctionList = new ArrayList<>();
    //推荐列表
    @BindView(R.id.recommendation_recycler)
    RecyclerView mRecommendationRecyclerView;
    RecommendationAdapter mRecommendationAdapter;
    List<GoodsResult> mRecommendationList = new ArrayList<>();
    //附近商家
    @BindView(R.id.business_recycler)
    RecyclerView mBusinessRecyclerView;
    NearbyBusinessesAdapter mBusinessAdapter;
    List<GoodsResult> mBusinessList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.bg_white, false);
    }

    @Override
    public boolean isPublicPermission() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews() {
        // mCurrentUser = UserManager.getInstance().getCurrentUser();
        initBanner();
        initSearchView();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mModuleAdapter = new ModuleAdapter(mModuleList);
        mModuleAdapter.setLayoutSize(ViewUtils.dp2px(mContext, 70), ViewUtils.dp2px(mContext, 100));
        mModuleRecyclerView.setLayoutManager(layoutManager);
        mModuleRecyclerView.addItemDecoration(ViewUtils.getListDivider(mContext, 20f,
                R.color.bg_white, false));
        mModuleRecyclerView.setAdapter(mModuleAdapter);
        mModuleAdapter.setOnItemClickListener((adapter, view, position) -> {
            WidgetUtils.toast(mContext, mModuleList.get(position).getName(), false);
            if (position == 0) {
                //美食 FoodListFragment
                ARouterUtils.openDetailFragment(getActivity(),
                        ShopListFragment.class.getName(), null, R.color.bg_white, false);
            }
        });

        //bind function
        mFunctionRecyclerView.setLayoutManager(new JGridLayoutManager(mContext, 5,
                GridLayoutManager.VERTICAL, false));
        mFunctionRecyclerView.addItemDecoration(new GridItemDecoration.Builder(mContext)
                .setHorizontalSpan(R.dimen.margin_smallest)
                .setVerticalSpan(R.dimen.margin_smallest)
                .setColorResource(R.color.bg_white)
                .setShowLastLine(false)
                .build());
        mFunctionAdapter = new ModuleAdapter(mFunctionList);
        mFunctionAdapter.setLayoutSize(ViewUtils.dp2px(mContext, 60), ViewUtils.dp2px(mContext,
                90));
        mFunctionAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mFunctionAdapter.bindToRecyclerView(mFunctionRecyclerView);
        mFunctionAdapter.setOnItemClickListener((adapter, view, position) -> {
            WidgetUtils.toast(mContext, mFunctionList.get(position).getName(), false);
        });

        //bind Recommendation
        mRecommendationRecyclerView.setLayoutManager(new JGridLayoutManager(mContext, 5,
                GridLayoutManager.VERTICAL, false));
        mRecommendationRecyclerView.addItemDecoration(new GridItemDecoration.Builder(mContext)
                .setHorizontalSpan(R.dimen.margin_smallest)
                .setVerticalSpan(R.dimen.margin_smallest)
                .setColorResource(R.color.bg_white)
                .setShowLastLine(false)
                .build());
        mRecommendationAdapter = new RecommendationAdapter(R.layout.item_image_text,
                mRecommendationList);
        mRecommendationAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecommendationAdapter.bindToRecyclerView(mRecommendationRecyclerView);
        mRecommendationAdapter.setOnItemClickListener((adapter, view, position) -> {
            WidgetUtils.toast(mContext, mRecommendationList.get(position).getName(), false);
            startActivity(new Intent(mContext, GoodsActivity.class));
        });

        //附近商家
        mBusinessAdapter = new NearbyBusinessesAdapter(R.layout.item_nearby_business,
                mBusinessList);
        mBusinessRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBusinessRecyclerView.addItemDecoration(ViewUtils.getListDivider(mContext, 1f, true));
        mBusinessRecyclerView.setAdapter(mBusinessAdapter);
        mBusinessAdapter.setOnItemClickListener((adapter, view, position) -> {
            WidgetUtils.toast(mContext, mBusinessList.get(position).getName(), false);
        });

        bindViewData();
    }

    @Override
    public void onStart() {
        super.onStart();
        // mBanner.startAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchView.clearFocus();
    }

    @Override
    public void onStop() {
        super.onStop();
        //mBanner.stopAutoPlay();
    }

    private void bindViewData() {
        //show module
        initModuleListData();

        initFunctionListData();

        initRecommendationListData();

        initBusinessListData();
    }

    private void initModuleListData() {
        ModuleResult moduleResult;
        for (int i = 0; i < 10; i++) {
            moduleResult = new ModuleResult();
            moduleResult.setId(100 + i);
            moduleResult.setName("模块" + moduleResult.getId());
            moduleResult.setUrl("http://img.qqzhi.com/uploads/2018-11-29/120313953.jpg");
            mModuleList.add(moduleResult);
        }
        mModuleAdapter.notifyDataSetChanged();
    }

    private void initFunctionListData() {
        ModuleResult moduleResult;
        for (int i = 0; i < 10; i++) {
            moduleResult = new ModuleResult();
            moduleResult.setId(200 + i);
            moduleResult.setName("功能" + i);
            moduleResult.setUrl("http://img.qqzhi.com/uploads/2018-11-29/120313953.jpg");
            mFunctionList.add(moduleResult);
        }
        mFunctionAdapter.notifyDataSetChanged();
    }

    private void initRecommendationListData() {
        //TODO：有价格
        GoodsResult result;
        for (int i = 0; i < 10; i++) {
            result = new GoodsResult();
            result.setId(String.valueOf(300 + i));
            result.setName("推荐" + result.getId());
            result.setUrl("http://img.qqzhi.com/uploads/2018-11-29/120313953.jpg");
            result.setPrice(300 + i);
            mRecommendationList.add(result);
        }
        mRecommendationAdapter.notifyDataChanged();
    }

    private void initBusinessListData() {
        //TODO：内容不一样
        GoodsResult result;
        for (int i = 0; i < 10; i++) {
            result = new GoodsResult();
            result.setId(String.valueOf(400 + i));
            result.setName("商品" + result.getId());
            result.setBusinessName("附近商家" + result.getId());
            result.setUrl("http://img.qqzhi.com/uploads/2018-11-29/120313953.jpg");
            result.setFloorPrice(0.01d);
            result.setScore(3.5f);
            result.setPrice(400 + i);
            mBusinessList.add(result);
        }
        mBusinessAdapter.notifyDataChanged();
    }

    private View getHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.tile_item_header,
                (ViewGroup) mModuleRecyclerView.getParent(), false);
        TextView headerView = view.findViewById(R.id.sub_system_header);
        headerView.setText(R.string.sub_system_hr);
        return view;
    }

   /*    @Override
    public void initDatabase() {
        //TODO: 初始化数据库
    }*/

    private void initSearchView() {
        mSearchView.init(getActivity());
        mSearchView.getRightView().setVisibility(View.GONE);
        mSearchView.setQueryHint("请输入商家、商品");
        mSearchView.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        mSearchView.getSearchView().onActionViewExpanded();
        mSearchView.getSearchView().setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //mQueryCondition.put(HRConstants.StaffQueryCondition.STAFF_NAME, query);
                //getStaffList(false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Log.d("KING", "text change " + newText);
                //fixed 空值时点击搜索不查询，还是监听搜索按键的好
                // mQueryText = newText;
                return false;
            }
        });
        mSearchView.setOnSoftKeyBoardChangeListener(new JSearchView.OnSoftKeyboardChangeListener() {
            @Override
            public void show(int height) {
            }

            @Override
            public void hide(int height) {
                /*if (!mQueryText.equals(mQueryCondition.get(HRConstants.StaffQueryCondition
                .STAFF_NAME))) {
                    mQueryCondition.put(HRConstants.StaffQueryCondition.STAFF_NAME, mQueryText);
                    getStaffList(false);
                }*/
            }
        });
    }

    private void initBanner() {
        //get image list

 /*       List<String> urlList = new ArrayList<>();
        urlList.add("https://timgsa.baidu
        .com/timg?image&quality=80&size=b9999_10000&sec=1586508819188&di
        =7e827f302c962d8c6d49aa92ea9dbba6&imgtype=0&src=http%3A%2F%2Fimg1.imgtn.bdimg
        .com%2Fit%2Fu%3D904104921%2C728464647%26fm%3D214%26gp%3D0.jpg");
        urlList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        urlList.add("http://img1.imgtn.bdimg.com/it/u=3721961507,511749509&fm=27&gp=0.jpg");
        urlList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        urlList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");

        List<String> titleList = new ArrayList<>();
        titleList.add("Image 1");
        titleList.add("Image 2");
        titleList.add("Image 3");*/

        List<JListItem<String>> mBannerList = new ArrayList<>();
        mBannerList.add(new JListItem(2201, "第一个", "https://img.zcool" +
                ".cn/community/011ad05e27a173a801216518a5c505.jpg"));
        mBannerList.add(new JListItem(2202, "第一个", "https://img.zcool" +
                ".cn/community/0148fc5e27a173a8012165184aad81.jpg"));
        mBannerList.add(new JListItem(2203, "第一个", "https://img.zcool" +
                ".cn/community/013c7d5e27a174a80121651816e521.jpg"));
        mBannerList.add(new JListItem(2204, "第一个", "https://img.zcool" +
                ".cn/community/01b8ac5e27a173a80120a895be4d85.jpg"));
        mBannerList.add(new JListItem(2205, "第一个", "https://img.zcool" +
                ".cn/community/01a85d5e27a174a80120a895111b2c.jpg"));

        mBannerView.setAdapter(new BannerImageAdapter(mBannerList))
                .setIndicator(new BannerNumIndicator(mContext))
                .setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
        //mBannerView.setBannerGalleryEffect(20, 10);
        //添加透明效果(画廊配合透明效果更棒)
        mBannerView.addPageTransformer(new AlphaPageTransformer());

        //setBannerTitles(titleList)

        //简单使用
       /* mBanner.setImages(localList)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int i) {
                        //ERPUtils.toastErrorMessage(ERPMainActivity.this, "点击了第" + i + "张图片");
                    }
                })
                .start();
        mBanner.requestFocus();*/
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}

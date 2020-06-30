package com.zjclugger.seller.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.view.JSearchView;
import com.zjclugger.router.utils.ARouterUtils;
import com.zjclugger.seller.R;
import com.zjclugger.seller.ui.goods.CategoryManageFragment;
import com.zjclugger.seller.ui.goods.GoodsManagerFragment;
import com.zjclugger.seller.utils.SellerConstants;
import com.zjclugger.seller.webapi.response.GoodsResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class SellerHomeFragment extends BaseFragment {

    @BindView(R.id.search_view)
    JSearchView mSearchView;
    @BindView(R.id.goods_add)
    TextView mGoodsAddView;
    @BindView(R.id.goods_update)
    TextView mGoodsUpdateView;
    @BindView(R.id.goods_category)
    TextView mGoodsCategoryView;

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
        return R.layout.fragment_seller_home;
    }

    @Override
    public void initViews() {
        //  mCurrentUser = UserManager.getInstance().getCurrentUser();
        //mLastLoginTime.setValueText(ERPUtils.DATETIME_FORMAT.format(new Date()));
        initSearchView();
        //mPortalNavigation.setSelected(false);

        //todo: for test
        mGoodsAddView.setOnClickListener(v -> {
            GoodsResult goodsResult = new GoodsResult();
            goodsResult.setName("");

            Bundle params = new Bundle();
            params.putParcelable(SellerConstants.Keywords.KEY_GOODS_MANAGE, goodsResult);
            ARouterUtils.openDetailFragment(getActivity(),
                    GoodsManagerFragment.class.getName(), params, R.color.bg_white, false);
        });


        mGoodsUpdateView.setOnClickListener(v -> {
            List<String> imageList = new ArrayList<>();
            imageList.add("http://img.qqzhi.com/uploads/2019-04-06/103454404.jpg");
            imageList.add("http://img.qqzhi.com/uploads/2019-03-26/203922802.jpg");
            imageList.add("http://img.qqzhi.com/uploads/2019-03-25/153128925.jpg");
            imageList.add("http://img.qqzhi.com/uploads/2019-05-23/184503893.jpg");
            imageList.add("http://img.qqzhi.com/uploads/2019-04-19/181211409.jpg");
            imageList.add("http://img.qqzhi.com/uploads/2019-04-03/060630691.jpg");
            imageList.add("http://img.qqzhi.com/uploads/2019-03-25/121825762.jpg");
            GoodsResult goodsResult = new GoodsResult();
            goodsResult.setId("102");
            goodsResult.setCategoryId(202);
            goodsResult.setName("测试商品");
            goodsResult.setGoodsImageUrl(imageList);
            goodsResult.setCategoryName("测试分类1");
            goodsResult.setDescription("这么好的商品无须描述");
            goodsResult.setPrice(999d);

            Bundle params = new Bundle();
            params.putParcelable(SellerConstants.Keywords.KEY_GOODS_MANAGE, goodsResult);
            ARouterUtils.openDetailFragment(getActivity(),
                    GoodsManagerFragment.class.getName(), params, R.color.bg_white, false);
        });

        mGoodsCategoryView.setOnClickListener(v -> {
            ARouterUtils.openDetailFragment(getActivity(),
                    CategoryManageFragment.class.getName(), null, R.color.bg_white, false);

        });
        bindViewData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchView.clearFocus();
    }

    private void bindViewData() {
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


    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}

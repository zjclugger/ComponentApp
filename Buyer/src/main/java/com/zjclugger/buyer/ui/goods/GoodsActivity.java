package com.zjclugger.buyer.ui.goods;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.zjclugger.buyer.R;
import com.zjclugger.lib.base.BaseActivity;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.view.NoScrollViewPager;
import com.zjclugger.lib.view.viewpager.ItemTitlePagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品详情<br>
 * Created by King.Zi on 2020/4/26.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsActivity extends BaseActivity {

    private static final String[] CHANNELS = new String[]{"商品简介", "商品详情", "商品评论"};
    @BindView(R.id.goods_magicindicator_tabs)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.goods_view_pager)
    public NoScrollViewPager mViewPager;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private GoodsInfoFragment mGoodsInfoFragment;
    private GoodsDetailFragment mGoodsDetailFragment;
    private GoodsCommentFragment mGoodsCommentFragment;
    private View.OnClickListener mOpenCommentViewListener;
    private View.OnClickListener mOpenDetailViewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_layout);
        ButterKnife.bind(this);

        mGoodsInfoFragment = new GoodsInfoFragment();
        mGoodsDetailFragment = new GoodsDetailFragment();
        mGoodsCommentFragment = new GoodsCommentFragment();

        fragmentList.add(mGoodsInfoFragment);
        fragmentList.add(mGoodsDetailFragment);
        fragmentList.add(mGoodsCommentFragment);

        mViewPager.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, CHANNELS));

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                if (0 == position) {
                    if (null == mOpenDetailViewListener) {
                        mOpenDetailViewListener = v -> mViewPager.setCurrentItem(1);

                        mGoodsInfoFragment.setOpenDetailViewListener(mOpenDetailViewListener);
                    }

                    if (null == mOpenCommentViewListener) {
                        mOpenCommentViewListener = v -> mViewPager.setCurrentItem(2);
                        mGoodsInfoFragment.setOpenCommentViewListener(mOpenCommentViewListener);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                mMagicIndicator.setVisibility(0 == position ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return CHANNELS.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView =
                        new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(CHANNELS[index]);
                simplePagerTitleView.setNormalColor(mContext.getResources().getColor(R.color.black));
                simplePagerTitleView.setSelectedColor(mContext.getResources().getColor(R.color.text_selected));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#ED6A53"));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after
        // setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(this, 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.shape_splitter_white));
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
        mMagicIndicator.setVisibility(View.GONE);
    }

    @Override
    protected boolean isDetailActivity() {
        return true;
    }

    @Override
    protected String getDetailActivityTitle() {
        return "商品标题";
    }
}

package com.zjclugger.buyer.ui.shopcart;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.zjclugger.basiclib.Log;
import com.zjclugger.buyer.R;
import com.zjclugger.buyer.persistence.entity.Shopcart;
import com.zjclugger.buyer.ui.order.OrderSubmitFragment;
import com.zjclugger.buyer.ui.vm.BuyerViewModel;
import com.zjclugger.buyer.utils.BuyerConstants;
import com.zjclugger.buyer.webapi.response.GoodsResult;
import com.zjclugger.buyer.webapi.response.ShopResult;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.SerializableMap;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.utils.ARouterUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 购物车 <br>
 * Created by King.Zi on 2020/4/24.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ShopCartFragment extends BaseFragment {
    private static final String TAG = "ShopCart";
    @BindView(R.id.expand_list_view)
    ExpandableListView mExpandableListView;
    @BindView(R.id.all_checkBox)
    CheckBox mAllCheckBoxView;
    @BindView(R.id.total_price)
    TextView mTotalPriceView;
    @BindView(R.id.go_settlement)
    TextView mSettlementView;
    @BindView(R.id.order_info)
    LinearLayout mOrderInfoLayout;
    @BindView(R.id.share_goods)
    TextView mShareView;
    @BindView(R.id.collect_goods)
    TextView mCollectGoodsView;
    @BindView(R.id.del_goods)
    TextView mDeleteGoodsView;
    @BindView(R.id.share_info)
    LinearLayout mShareInfoView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_title_back)
    ImageView mNavBackView;
    @BindView(R.id.tv_title_text)
    TextView mShopCartNumView;
    @BindView(R.id.tv_right_text)
    TextView mEditView;
    @BindView(R.id.empty_view)
    TextView mShopCartEmptyView;
    @BindView(R.id.bottom_layout)
    LinearLayout mBottomLayout;
    BuyerViewModel mViewModel;

    private double mTotalPrice = 0.00d;
    private int mTotalCount = 0;
    private boolean mActionComplete = false;    //false就是编辑，true就是完成
    private ShopCartAdapter mShopCartAdapter;
    private ArrayList<ShopResult> mShopResultList = new ArrayList<>(); //组元素的列表[商铺]
    private Map<String, List<GoodsResult>> mShopCartGoodsMap = new HashMap<>(); //子元素的列表[商品]
    //已选择的
    private ArrayList<ShopResult> mShopSelectedList = new ArrayList<>(); //组元素的列表[商铺]
    private Map<String, List<GoodsResult>> mGoodsSelectedMap = new HashMap<>(); //子元素的列表[商品]

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.bg_white, false);
        mViewModel = ViewModelProviders.of(getActivity()).get(BuyerViewModel.class);
    }


    private void getLocalShopCartList() {
        //todo: for test
        mViewModel.getShopCarts().observe(this, new Observer<List<Shopcart>>() {
            @Override
            public void onChanged(List<Shopcart> shopcartList) {
                showMessage("获取到本地购物车数据=" + (null == shopcartList ? 0 : shopcartList.size()));
            }
        });

        mViewModel.getShopCartList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<Shopcart>>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(List<Shopcart> shopcarts) {
                        //TODO:如果查询没有结果是可以得到一个空list
                        if (null == shopcarts) {
                            showMessage("获取购物车内的商品列表为空");
                        } else {
                            showMessage("获取购物车内的商品列表数=" + shopcarts.size());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public boolean isPublicPermission() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    public void initViews() {
        mNavBackView.setVisibility(View.INVISIBLE);
        mEditView.setText(R.string.text_edit);

        initRefresh();
        mShopCartEmptyView.setCompoundDrawablesWithIntrinsicBounds(null,
                getResources().getDrawable(R.mipmap.img_shop_cart_empty), null, null);
        mShopCartEmptyView.setCompoundDrawablePadding(ViewUtils.dp2px(mContext, 5));
        // mShopCartGoodsMap = new HashMap<>();
        initListView();
        bindViewData();
        //todo: for test
        getLocalShopCartList();
    }

    @Override
    public void onResume() {
        super.onResume();
        changeShopCartNumber();
    }

    private void initRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //TODO:刷新
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                //TODO:刷新
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initListView() {
        //init adapter
        mShopCartAdapter = new ShopCartAdapter(mContext, mShopResultList, mShopCartGoodsMap);
        //关键步骤1：设置复选框的接口
        mShopCartAdapter.setCheckInterface(new ShopCartAdapter.CheckInterface() {
            @Override
            public void checkGroup(int groupPosition, boolean isChecked) {
                ShopResult shopResult = mShopResultList.get(groupPosition);
                List<GoodsResult> child = mShopCartGoodsMap.get(shopResult.getId());
                for (int i = 0; i < child.size(); i++) {
                    child.get(i).setChecked(isChecked);
                }
                if (isCheckAll()) {
                    mAllCheckBoxView.setChecked(true);//全选
                } else {
                    mAllCheckBoxView.setChecked(false);//反选
                }
                mShopCartAdapter.notifyDataSetChanged();
                calculate();
            }

            @Override
            public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
                boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态
                ShopResult group = mShopResultList.get(groupPosition);
                List<GoodsResult> child = mShopCartGoodsMap.get(group.getId());
                for (int i = 0; i < child.size(); i++) {
                    //不选全中
                    if (child.get(i).isChecked() != isChecked) {
                        allChildSameState = false;
                        break;
                    }
                }

                if (allChildSameState) {
                    group.setChoosed(isChecked);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
                } else {
                    group.setChoosed(false);//否则一律视为未选中
                }

                if (isCheckAll()) {
                    mAllCheckBoxView.setChecked(true);//全选
                } else {
                    mAllCheckBoxView.setChecked(false);//反选
                }

                mShopCartAdapter.notifyDataSetChanged();
                calculate();
            }
        });
        //关键步骤2:设置增删减的接口
        mShopCartAdapter.setModifyCountInterface(new ShopCartAdapter.ModifyCountInterface() {
            @Override
            public void doIncrease(int groupPosition, int childPosition, View showCountView,
                                   boolean isChecked) {
                GoodsResult good = (GoodsResult) mShopCartAdapter.getChild(groupPosition,
                        childPosition);
                int count = good.getCount();
                count++;
                good.setCount(count);
                ((TextView) showCountView).setText(String.valueOf(count));
                mShopCartAdapter.notifyDataSetChanged();
                calculate();
            }

            @Override
            public void doDecrease(int groupPosition, int childPosition, View showCountView,
                                   boolean isChecked) {
                GoodsResult good = (GoodsResult) mShopCartAdapter.getChild(groupPosition,
                        childPosition);
                int count = good.getCount();
                if (count == 1) {
                    return;
                }
                count--;
                good.setCount(count);
                ((TextView) showCountView).setText("" + count);
                //TODO: 加减后会刷新列表，需要考虑无刷新-King.zi
                mShopCartAdapter.notifyDataSetChanged();
                calculate();
            }

            @Override
            public void doUpdate(int groupPosition, int childPosition, View showCountView,
                                 boolean isChecked) {
                GoodsResult good = (GoodsResult) mShopCartAdapter.getChild(groupPosition,
                        childPosition);
                int count = good.getCount();
                Log.i("进行更新数据，数量" + count + "");
                ((TextView) showCountView).setText(String.valueOf(count));
                mShopCartAdapter.notifyDataSetChanged();
                calculate();
            }

            @Override
            public void childDelete(int groupPosition, int childPosition) {
                ShopResult group = mShopResultList.get(groupPosition);
                List<GoodsResult> child = mShopCartGoodsMap.get(group.getId());
                child.remove(childPosition);
                if (child.size() == 0) {
                    mShopResultList.remove(groupPosition);
                }
                mShopCartAdapter.notifyDataSetChanged();
                calculate();
            }
        });

        //关键步骤3:监听组列表的编辑状态
        mShopCartAdapter.setGroupEditorListener(new ShopCartAdapter.GroupEditorListener() {
            @Override
            public void groupEditor(int groupPosition) {

            }
        });

        //init list view
        mExpandableListView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
       /* mExpandableListView.setDivider(getResources().getDrawable(R.drawable.sharp_line_5));
        mExpandableListView.setChildDivider(getResources().getDrawable(R.drawable.sharp_line_1));*/

        mExpandableListView.setAdapter(mShopCartAdapter);

        //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        for (int i = 0; i < mShopCartAdapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }

        mExpandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                int firstVisiablePostion = view.getFirstVisiblePosition();
                int top = -1;
                View firstView = view.getChildAt(firstVisibleItem);
                Log.i("childCount=" + view.getChildCount());//返回的是显示层面上的所包含的子view的个数
                if (firstView != null) {
                    top = firstView.getTop();
                }
                Log.i("firstVisiableItem=" + firstVisibleItem + ",fistVisiablePosition=" + firstVisiablePostion + ",firstView=" + firstView + ",top=" + top);
                if (firstVisibleItem == 0 && top == 0) {
                    //mPtrFrame.setEnabled(true);
                } else {
                    //mPtrFrame.setEnabled(false);
                }
            }
        });
    }

    /**
     * @return 判断组元素是否全选
     */
    private boolean isCheckAll() {
        for (ShopResult group : mShopResultList) {
            if (!group.isChoosed()) {
                return false;
            }
        }
        return true;
    }

    @OnClick({R.id.all_checkBox, R.id.go_settlement, R.id.share_goods, R.id.collect_goods,
            R.id.del_goods, R.id.tv_right_text})
    public void onClick(View view) {
        AlertDialog dialog;
        switch (view.getId()) {
            case R.id.all_checkBox:
                doCheckAll();
                break;
            case R.id.go_settlement:
                if (mTotalCount == 0) {
                    WidgetUtils.toast(mContext, "请选择要支付的商品", false);
                    return;
                }
                //todo: open submit fragment
                Bundle params = new Bundle();
                params.putParcelableArrayList(BuyerConstants.Keywords.KEY_ORDER_SHOP_LIST,
                        mShopSelectedList);
                SerializableMap<String, List<GoodsResult>> goodsList = new SerializableMap<>();
                //TODO:应该是选中的
                goodsList.setMap(mGoodsSelectedMap);
                params.putSerializable(BuyerConstants.Keywords.KEY_ORDER_SHOP_GOODS_LIST,
                        goodsList);
                params.putDouble(BuyerConstants.Keywords.KEY_ORDER_TOTAL_PRICE, mTotalPrice);
                ARouterUtils.openDetailFragment(getActivity(),
                        OrderSubmitFragment.class.getName(), params, R.color.white, false);
                break;
            case R.id.share_goods:
                if (mTotalCount == 0) {
                    WidgetUtils.toast(mContext, "请选择要分享的商品", false);
                    return;
                }
                WidgetUtils.toast(mContext, "分享成功", false);
                break;
            case R.id.collect_goods:
                if (mTotalCount == 0) {
                    WidgetUtils.toast(mContext, "请选择要收藏的商品", false);
                    return;
                }
                WidgetUtils.toast(mContext, "收藏成功", false);
                break;
            case R.id.del_goods:
                if (mTotalCount == 0) {
                    WidgetUtils.toast(mContext, "请选择要删除的商品", false);
                    return;
                }
                dialog = new AlertDialog.Builder(mContext).create();
                dialog.setMessage("确认要删除该商品吗?");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelete();
                            }
                        });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                dialog.show();
                break;
            case R.id.tv_right_text:
                mActionComplete = !mActionComplete;
                setActionBarEditor();
                setVisibility();
                break;
        }
    }

    /**
     * ActionBar标题上点编辑的时候，只显示每一个店铺的商品修改界面
     * ActionBar标题上点完成的时候，只显示每一个店铺的商品信息界面
     */
    private void setActionBarEditor() {
        for (int i = 0; i < mShopResultList.size(); i++) {
            ShopResult group = mShopResultList.get(i);
            if (group.isActionBarEditor()) {
                group.setActionBarEditor(false);
            } else {
                group.setActionBarEditor(true);
            }
        }
        mShopCartAdapter.notifyDataSetChanged();
    }

    /**
     * 全选和反选
     * 错误标记：在这里出现过错误
     */
    private void doCheckAll() {
        for (int i = 0; i < mShopResultList.size(); i++) {
            ShopResult group = mShopResultList.get(i);
            group.setChoosed(mAllCheckBoxView.isChecked());
            List<GoodsResult> child = mShopCartGoodsMap.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                child.get(j).setChecked(mAllCheckBoxView.isChecked());//这里出现过错误
            }
        }
        mShopCartAdapter.notifyDataSetChanged();
        calculate();
    }

    /**
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */
    private void calculate() {
        mTotalPrice = 0.00;
        mTotalCount = 0;
        if (null != mShopSelectedList) {
            mShopSelectedList.clear();
        }
        if (null != mGoodsSelectedMap) {
            mGoodsSelectedMap.clear();
        }

        boolean isAdded;
        List<GoodsResult> goodsList;
        for (int i = 0; i < mShopResultList.size(); i++) {
            isAdded = false;
            goodsList = new ArrayList<>();
            ShopResult group = mShopResultList.get(i);
            List<GoodsResult> child = mShopCartGoodsMap.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                GoodsResult goods = child.get(j);
                if (goods.isChecked()) {
                    if (!isAdded) {
                        mShopSelectedList.add(mShopResultList.get(i));
                        isAdded = true;
                    }
                    goodsList.add(goods);
                    mTotalCount++;
                    mTotalPrice += goods.getPrice() * goods.getCount();
                }
            }
            if (null != goodsList) {
                mGoodsSelectedMap.put(mShopResultList.get(i).getId(), goodsList);
            }
        }
        mTotalPriceView.setText("￥" + mTotalPrice + "");
        mSettlementView.setText(getString(R.string.go_settlement) + "(" + mTotalCount + ")");
        if (mTotalCount == 0) {
            changeShopCartNumber();
        } else {
            mShopCartNumView.setText("购物车(" + mTotalCount + ")");
        }
    }

    private void setVisibility() {
        if (mActionComplete) {
            mOrderInfoLayout.setVisibility(View.GONE);
            mShareInfoView.setVisibility(View.VISIBLE);
            mEditView.setText(R.string.complete);
        } else {
            mOrderInfoLayout.setVisibility(View.VISIBLE);
            mShareInfoView.setVisibility(View.GONE);
            mEditView.setText(R.string.text_edit);
        }
    }

    /**
     * 设置购物车的数量
     */
    private void changeShopCartNumber() {
        int count = 0;
        for (int i = 0; i < mShopResultList.size(); i++) {
            ShopResult group = mShopResultList.get(i);
            group.setChoosed(mAllCheckBoxView.isChecked());
            List<GoodsResult> Childs = mShopCartGoodsMap.get(group.getId());
            for (GoodsResult mShopCartGoodsMap : Childs) {
                count++;
            }
        }

        //购物车已经清空
        if (count == 0) {
            clearShopCart();
        } else {
            mShopCartNumView.setText("购物车(" + count + ")");
        }
    }

    /**
     * 清空购物车
     */
    private void clearShopCart() {
        mShopCartNumView.setText("购物车(0)");
        mEditView.setVisibility(View.GONE);
        mBottomLayout.setVisibility(View.GONE);
        mRefreshLayout.setVisibility(View.GONE);
        mShopCartEmptyView.setVisibility(View.VISIBLE);//TODO:这里发生过错误
    }

    /**
     * 删除操作
     * 1.不要边遍历边删除,容易出现数组越界的情况
     * 2.把将要删除的对象放进相应的容器中，待遍历完，用removeAll的方式进行删除
     */
    private void doDelete() {
        List<ShopResult> toDelShopList = new ArrayList<>(); //待删除的组元素(商铺)
        for (int i = 0; i < mShopResultList.size(); i++) {
            if (mShopResultList.get(i).isChoosed()) {
                toDelShopList.add(mShopResultList.get(i));
            }
            List<GoodsResult> toDelShopCartGoodsList = new ArrayList<>();//待删除的子元素(商品)
            List<GoodsResult> shopGoodsList =
                    mShopCartGoodsMap.get(mShopResultList.get(i).getId());
            for (int j = 0; j < shopGoodsList.size(); j++) {
                if (shopGoodsList.get(j).isChecked()) {
                    toDelShopCartGoodsList.add(shopGoodsList.get(j));
                }
            }
            shopGoodsList.removeAll(toDelShopCartGoodsList);
        }
        mShopResultList.removeAll(toDelShopList);
        //重新设置购物车
        changeShopCartNumber();
        mShopCartAdapter.notifyDataSetChanged();
    }

    private void bindViewData() {
        //show module
        initData();
    }

    /**
     * 模拟数据<br>
     * 遵循适配器的数据列表填充原则，组元素被放在一个list中，对应着组元素的下辖子元素被放在Map中
     * 其Key是组元素的Id
     */
    private void initData() {
        for (int i = 0; i < 5; i++) {
            mShopResultList.add(new ShopResult(i + "", "第" + (i + 1) + "号当铺"));
            List<GoodsResult> goods = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                //i-j 就是商品的id， 对应着第几个店铺的第几个商品，1-1 就是第一个店铺的第一个商品
                GoodsResult goodsResult = new GoodsResult();
                goodsResult.setId(i + "-" + j);
                goodsResult.setName((i + 1) + "号商铺第" + (j + 1) + "商品");
                goodsResult.setUrl("http://img.qqzhi.com/uploads/2019-04-03/060630691.jpg");
                goodsResult.setPrice(255.00 + new Random().nextInt(1500));
                goodsResult.setPrimePrice(1555 + new Random().nextInt(3000));
                goodsResult.setProperties("商品属性-颜色-大小等");
                goodsResult.setCount(new Random().nextInt(50));
                goods.add(goodsResult);
            }
            mShopCartGoodsMap.put(mShopResultList.get(i).getId(), goods);
        }

        //fill data
        mShopCartAdapter.setData(mShopResultList, mShopCartGoodsMap);
        mShopCartAdapter.notifyDataSetChanged();
        for (int i = 0; i < mShopCartAdapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mShopCartAdapter = null;
        mShopCartGoodsMap.clear();
        mShopResultList.clear();
        mTotalPrice = 0.00;
        mTotalCount = 0;
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}

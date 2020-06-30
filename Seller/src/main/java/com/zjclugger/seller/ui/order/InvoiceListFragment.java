package com.zjclugger.seller.ui.order;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zjclugger.lib.base.BaseListFragment;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.view.ExtendsRecyclerView;
import com.zjclugger.seller.R;
import com.zjclugger.seller.webapi.response.ExpressCompanyResult;
import com.zjclugger.seller.webapi.response.ExpressmanResult;
import com.zjclugger.seller.webapi.response.GoodsResult;
import com.zjclugger.seller.webapi.response.InvoiceResult;
import com.zjclugger.seller.webapi.response.OrderGoodsResult;
import com.zjclugger.seller.webapi.response.OrderResult;
import com.zjclugger.seller.webapi.response.ShopInfoResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 发货单列表 <br>
 * Created by King.Zi on 2020/5/19.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class InvoiceListFragment extends BaseListFragment {
    private static final String TAG = "InvoiceList";

    @BindView(R.id.order_recycler_view)
    ExtendsRecyclerView mOrderRecyclerView;
    private List<InvoiceResult> mInvoiceList;

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
        return R.layout.fragment_invoice_list;
    }

    @Override
    public void initViews() {
        mInvoiceList = new ArrayList<>();
        mQueryCondition = new HashMap<>();
        resetQueryCondition();

        initDetailTitleViews("发货单");
        super.initViews();
    }

    @Override
    protected void resetQueryCondition() {
        super.resetQueryCondition();
        mQueryCondition.put(Constants.QueryParameter.PAGE_INDEX, 1);
        mQueryCondition.put(Constants.QueryParameter.PAGE_SIZE, 10);
    }

    @Override
    public void initTabLayout() {
    }

    @Override
    public void bindData() {
        mAdapter = new InvoiceListAdapter(R.layout.item_invoice_list, mInvoiceList);
    }

    @Override
    public boolean isShowLastLine() {
        return true;
    }

    @Override
    public ExtendsRecyclerView getRecyclerView() {
        return mOrderRecyclerView;
    }

    @Override
    public void getDataFromServer(boolean isPagingQuery) {
        initData();
    }

    /**
     * 模拟数据<br>
     * 遵循适配器的数据列表填充原则，组元素被放在一个list中，对应着组元素的下辖子元素被放在Map中
     * 其Key是组元素的Id
     */
    private void initData() {
        if (mInvoiceList != null) {
            mInvoiceList.clear();
        }

        //todo: init data
        InvoiceResult result;
        for (int i = 0; i < 20; i++) {
            result = new InvoiceResult();
            result.setId("12000" + i);

            OrderResult orderResult = new OrderResult();
            orderResult.setId("3000" + i);
            orderResult.setOrderNO("0023209981" + i);
            orderResult.setOrderTime("2020-05-18: 17:16");
            orderResult.setCustomerName("张三" + i);
            orderResult.setCustomerAddress("山东省青岛市市北区抚顺路98" + i + "号");
            OrderGoodsResult goodsResult;
            for (int y = 0; y < 5; y++) {
                goodsResult = new OrderGoodsResult();
                goodsResult.setName("精美商品" + y);
                goodsResult.setTransactionPrice(new BigDecimal(120));
                goodsResult.setCount(5);
                goodsResult.setTotalAmount(new BigDecimal(600));
                GoodsResult goodsInfo = new GoodsResult();
                goodsInfo.setName("商品" + i);
                List<String> imageList = new ArrayList<>();
                imageList.add("http://img.qqzhi.com/uploads/2019-05-23/184503893.jpg");
                goodsInfo.setGoodsImageUrl(imageList);
                goodsResult.setGoods(goodsInfo);
                orderResult.getGoodsList().add(goodsResult);
            }
            orderResult.setTotalAmount(5000d);
            result.setOrderInfo(orderResult);

            //快递公司
            ExpressCompanyResult expressCompanyResult = new ExpressCompanyResult();
            expressCompanyResult.setId("6786" + i);
            expressCompanyResult.setName("快递公司" + i);
            expressCompanyResult.setAddress("山东省青岛市南区香港路123" + i + "号");
            for (int m = 0; m < 5; m++) {
                ExpressmanResult expressman = new ExpressmanResult();
                expressman.setId("8610" + m);
                expressman.setName("李" + m + "四");
                expressman.setPhoneNumber("1981234567" + m);
                expressCompanyResult.getExpressmanList().add(expressman);
            }
            result.setExpressCompany(expressCompanyResult);

            //店铺信息
            ShopInfoResult shopInfoResult = new ShopInfoResult();
            shopInfoResult.setName("店铺名" + i);
            shopInfoResult.setAddress("山东省青岛市山东路2" + i + "号");
            result.setShopInfo(shopInfoResult);

            mInvoiceList.add(result);
        }
        mAdapter.notifyDataChanged();
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}

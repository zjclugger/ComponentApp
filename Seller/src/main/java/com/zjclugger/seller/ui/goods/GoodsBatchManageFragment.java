package com.zjclugger.seller.ui.goods;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjclugger.lib.base.BaseListFragment;
import com.zjclugger.lib.ui.widget.JDialog;
import com.zjclugger.lib.utils.Constants;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.lib.view.ExtendsRecyclerView;
import com.zjclugger.seller.R;
import com.zjclugger.seller.webapi.response.GoodsCategoryResult;
import com.zjclugger.seller.webapi.response.GoodsResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 商品批量管理 <br>
 * Created by King.Zi on 2020/5/19.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsBatchManageFragment extends BaseListFragment {
    private static final String TAG = "InvoiceList";

    @BindView(R.id.goods_recycler_view)
    ExtendsRecyclerView mRecyclerView;
    @BindView(R.id.layout_left_button)
    Button mToShelvesButton;
    @BindView(R.id.layout_right_button)
    Button mCategoryButton;
    private List<GoodsResult> mGoodsList;

    private CategoryAdapter mCategoryAdapter;
    private List<GoodsCategoryResult> mCategoryList;
    private RecyclerView mCategoryListView;
    private JDialog mCategoryDialog;


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
        return R.layout.fragment_goods_batch_manage;
    }

    @Override
    public void initViews() {
        mGoodsList = new ArrayList<>();
        mCategoryList = new ArrayList<>();
        mQueryCondition = new HashMap<>();
        resetQueryCondition();

        initDetailTitleViews("批量管理");
        ViewUtils.setVisibility(true, mToShelvesButton, mCategoryButton);
        mToShelvesButton.setText("上架");
        mToShelvesButton.setOnClickListener(v -> {
            showMessage("批量管理-上架");
        });
        mCategoryButton.setText("分类");
        mCategoryButton.setOnClickListener(v -> {
            showMessage("批量管理-分类到");
        });

        createCategoryDialog();
        super.initViews();
    }

    private void createCategoryDialog() {
        mCategoryAdapter = new CategoryAdapter(mCategoryList, false);
        mCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //mCategoryAdapter.notifyDataChanged();
            }
        });
        mCategoryDialog = new JDialog(mContext, R.layout.dialog_goods_category_list);
        mCategoryDialog.getDialogView().findViewById(R.id.title_right_view).setOnClickListener(v -> mCategoryDialog.close());
        mCategoryDialog.setGravity(Gravity.BOTTOM)
                .setCanceledOnTouchOutside(false)
                .setMargin(1, 1, 1, -5);
        mCategoryListView =
                mCategoryDialog.getDialogView().findViewById(R.id.recycler_view);
        //TODO:adapter ?
        mCategoryListView.setLayoutManager(new LinearLayoutManager(mContext));
        mCategoryListView.setAdapter(mCategoryAdapter);

        mCategoryDialog.getDialogView().findViewById(R.id.category_add).setOnClickListener(v -> {
            WidgetUtils.toast(mContext, "添加新类别", false);
        });
        mCategoryDialog.getDialogView().findViewById(R.id.bottom_category_delete).setOnClickListener(v -> {
            WidgetUtils.toast(mContext, "删除分类", false);
        });
        mCategoryDialog.getDialogView().findViewById(R.id.bottom_category_ok).setOnClickListener(v -> {
            WidgetUtils.toast(mContext, "确认分类到。。。", false);
        });

        mCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCategoryList != null) {
                    mCategoryList.clear();
                }

                GoodsCategoryResult categoryResult;
                for (int i = 0; i < 10; i++) {
                    categoryResult = new GoodsCategoryResult();
                    categoryResult.setId(2324 + i);
                    categoryResult.setName("商品分类" + i);
                    categoryResult.setSelected(false);
                    mCategoryList.add(categoryResult);
                }
                mCategoryAdapter.notifyDataChanged();

                mCategoryDialog.show();
            }
        });
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
        mAdapter = new GoodsBatchManageAdapter(mGoodsList);
    }

    @Override
    public boolean isShowLastLine() {
        return true;
    }

    @Override
    public ExtendsRecyclerView getRecyclerView() {
        return mRecyclerView;
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
        if (mGoodsList != null) {
            mGoodsList.clear();
        }

        //todo: init data
        GoodsResult result;
        for (int i = 0; i < 20; i++) {
            result = new GoodsResult();
            result.setName("绝美商品-天字第" + i + "号");
            List<String> imageList = new ArrayList<>();
            imageList.add("http://img.qqzhi.com/uploads/2019-05-23/184503893.jpg");
            result.setGoodsImageUrl(imageList);
            result.setSalesVolume(800 + i);
            result.setPrice(66.02d + i);
            result.setStock(300 - i);
            mGoodsList.add(result);
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
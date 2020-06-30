package com.zjclugger.seller.ui.goods;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.JListItem;
import com.zjclugger.lib.ui.widget.JAlertDialog;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.seller.R;
import com.zjclugger.seller.ui.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页@title <br>
 * Created by King.Zi on 2020/4/17.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class CategoryManageFragment extends BaseFragment {
    //附近商家
    @BindView(R.id.category_add)
    TextView mCategoryAddView;
    @BindView(R.id.category_manage)
    TextView mCategoryManageView;
    @BindView(R.id.business_recycler)
    RecyclerView mBusinessRecyclerView;
    CategoryAdapter mCategoryAdapter;
    List<JListItem<Integer>> mCategoryList = new ArrayList<>();
    private JAlertDialog mAlertDialog;

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
        return R.layout.fragment_category_manage;
    }

    @Override
    public void initViews() {
        // mCurrentUser = UserManager.getInstance().getCurrentUser();
        initDetailTitleViews("分类管理");
        mAlertDialog = new JAlertDialog(mContext);
        mAlertDialog.setTitle("新建分类")
                .setCanceledOnTouchOutside(false)
                .setLeftButtonColor(R.color.text_gray)
                .setRightButtonColor(R.color.text_selected)
                .setOnClickListener(200,
                        "请输入商品分类", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAlertDialog.dismiss();

                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WidgetUtils.toast(mContext,
                                        "输入的内容=" + mAlertDialog.getContentView().getText(),
                                        false);
                                mAlertDialog.dismiss();
                            }
                        });

        mCategoryAddView.setOnClickListener(button -> {
            if (null != mAlertDialog) {
                mAlertDialog.show();
            }
        });
        //附近商家
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mBusinessRecyclerView.addItemDecoration(ViewUtils.getListDivider(mContext, 10f, true));
        mBusinessRecyclerView.setLayoutManager(layoutManager);
        mCategoryAdapter = new CategoryAdapter(R.layout.item_common_list, mCategoryList);
        mBusinessRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            WidgetUtils.toast(mContext, mCategoryList.get(position).getText(), false);
        });

        initBusinessListData();
    }

    private void initBusinessListData() {
        //TODO：内容不一样
        JListItem<Integer> result;
        for (int i = 0; i < 10; i++) {
            result = new JListItem();
            result.setId(500 + i);
            result.setText("商品分类" + result.getId());
            result.setValue(20 + i);
            mCategoryList.add(result);
        }
        mCategoryAdapter.notifyDataChanged();
    }


    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        return false;
    }
}
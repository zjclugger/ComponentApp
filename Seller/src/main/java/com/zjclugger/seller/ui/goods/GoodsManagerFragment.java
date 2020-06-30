package com.zjclugger.seller.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.jview.OptionsPickerView;
import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.JMediaItem;
import com.zjclugger.lib.ui.widget.JAlertDialog;
import com.zjclugger.lib.utils.CommonUtils;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.zjclugger.router.ARouterConstants;
import com.zjclugger.seller.R;
import com.zjclugger.seller.databinding.FragmentGoodsManagerBinding;
import com.zjclugger.seller.utils.SellerConstants;
import com.zjclugger.seller.webapi.response.GoodsCategoryResult;
import com.zjclugger.seller.webapi.response.GoodsResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加或编辑商品<br>
 * Created by King.Zi on 2020/4/15.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsManagerFragment extends BaseFragment {
    public static final String TAG = "GoodManager";
    /*private OriginalDetailResult mOriginalDetailResult;
    private OriginalDetailResult mGoodsResultCache;
    private OriginalDetailsEditAdapter mDetailsAdapter;*/
    private GoodsResult mGoodsResult;
    private GoodsResult mGoodsResultCache;
    private FragmentGoodsManagerBinding mBinding;
    private TextView mTitleView;
    private TextView mTitleRightView;
    private BaseFragment mSelectedMediaFragment;
    private ArrayList<JMediaItem> mGoodsImageList = new ArrayList<>();

    private JAlertDialog mAlertDialog;
    private OptionsPickerView mCategoryPickerView;
    private List<GoodsCategoryResult> mCategoryList = new ArrayList<>();
    private int mCategoryId = -1;
    private boolean mIsReadOnly = false;

    /*  @Override
      public List<UserPermission> getPermissionList() {
          List<UserPermission> permissionList = new ArrayList<>();
          permissionList.add(new UserPermission("finance:increment:update", R.id.tv_right_text));
          return permissionList;
      }*/
    @Override
    public boolean isPublicPermission() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_goods_manager, container,
                false);
        mView = mBinding.getRoot();
        if (mArguments != null && mArguments.containsKey(SellerConstants.Keywords.KEY_GOODS_MANAGE)) {
            mGoodsResult = mArguments.getParcelable(SellerConstants.Keywords.KEY_GOODS_MANAGE);
        }

        if (mGoodsResult != null) {
            mIsReadOnly = mArguments.getBoolean(SellerConstants.Keywords.KEY_GOODS_MANAGE_READ_ONLY
                    , false);
            mGoodsResultCache = CommonUtils.copyObject(mGoodsResult);
            initView();
            bindData();
        } else {
            WidgetUtils.toast(mContext, "nulllllll", false);
            close();
        }
        return mView;
    }

    private void initView() {
        //title
        mBinding.navTitleLayout.findViewById(R.id.iv_title_back).setOnClickListener(v -> close());

        mTitleView = mBinding.navTitleLayout.findViewById(R.id.tv_title_text);
        mTitleView.setText("商品");
        mTitleRightView = mBinding.navTitleLayout.findViewById(R.id.tv_right_text);
        if (TextUtils.isEmpty(mGoodsResult.getId())) {
            mTitleRightView.setText("新建");
        } else {
            mTitleRightView.setText(R.string.complete);
        }

        mTitleRightView.setOnClickListener(v -> {
            if (-1 != mCategoryId) {
                mGoodsResult.setCategoryId(mCategoryId);
            }

            String cacheValue = CommonUtils.object2Json(mGoodsResultCache);
            String currentValue = CommonUtils.object2Json(mGoodsResult);

            if (!cacheValue.equalsIgnoreCase(currentValue)) {
                updateOriginal(currentValue);
            } else {
                close();
            }
            //todo:获取要上传的图片
            /*if (mSelectedMediaFragment != null) {
                Bundle data = mSelectedMediaFragment.getArguments();
                mGoodsImageList =
                        data.getParcelableArrayList(Constants.Keywords.KEY_SELECTED_PICTURE);
                Log.d("kz", "the result media list is " + (mGoodsImageList == null ? "0" :
                        mGoodsImageList.size()));
            } else {
                Log.d("kz", "mSelectedMediaFragment is null");
            }*/
        });

        //bill image
       /* mBinding.billImageLayout.setOnClickListener(ARouterUtils.getPreviewImageListener(mContext,
                mOriginalDetailResult.getUrl()));*/

     /*   ViewUtils.setEditTextDigits(mBinding.letvTotalSmall.getEditTextView());
        ViewUtils.setEditTextDigits(mBinding.letvTotal.getEditTextView());

        mBinding.letvInputDate.setReadOnly();
        mBinding.letvInputDate.getEditTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerView mStartDatePickerDialog = new DateTimePickerView(getActivity(),
                        new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                mBinding.letvInputDate.getEditTextView().setText(CommonUtils
                                .getDateTime(date,
                                        "yyyy-MM-dd"));
                            }
                        }, DateTimeFormat.YMD);
                mStartDatePickerDialog.displayClassicLayout(false);
                mStartDatePickerDialog.show();
            }
        });*/

        mAlertDialog = new JAlertDialog(mContext);

        //mBinding.letvGoodsName.getEditTextView()

        mCategoryPickerView = new OptionsPickerView(mContext, (options1, options2, options3, v) -> {
            if (mCategoryList.get(options1).getId() != mGoodsResultCache.getCategoryId()) {
                mCategoryId = mCategoryList.get(options1).getId();
            } else {
                mCategoryId = -1;
            }

            mBinding.elvvGoodsCategory.setValueText(mCategoryList.get(options1).getName());
        });
        mCategoryPickerView.displayClassicLayout("商品分类", null, getString(R.string.text_confirm));

        //mBinding.elvvGoodsCategory.getLeftImageView().setImageResource(R.);
        mBinding.elvvGoodsCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsReadOnly) {
                    mCategoryPickerView.show();
                }
            }
        });

        if (mIsReadOnly) {
            mBinding.letvGoodsName.setEditTextReadOnly();
            mBinding.letvGoodsStock.setEditTextReadOnly();
            mBinding.letvGoodsPrice.setEditTextReadOnly();
            mBinding.elvvGoodsCategory.setEditTextReadOnly();
            mBinding.letvGoodsDescription.setEditTextReadOnly();
            mTitleRightView.setVisibility(View.INVISIBLE);
        }

       /* mBinding.letvDocType.setReadOnly();
        mBinding.letvDocType.getEditTextView().setCompoundDrawablePadding(5);
        mBinding.letvDocType.getEditTextView().setCompoundDrawablesWithIntrinsicBounds(null, null,
                mContext.getResources().getDrawable(R.mipmap.ic_dropdown_normal), null);
        mBinding.letvDocType.getEditTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryPickerView.show();
            }
        });*/

        //goods image
        initGoodsImageList();
        Bundle selectedBundle = new Bundle();
        selectedBundle.putParcelableArrayList(ARouterConstants.KeyWord.KEY_MEDIA_SELECTED_LIST,
                mGoodsImageList);
        mSelectedMediaFragment =
                (BaseFragment) ARouter.getInstance().build(ARouterConstants.Path.COM_SELECT_MEDIA)
                        .withBundle(ARouterConstants.KeyWord.KEY_MEDIA_PARAMETERS, selectedBundle)
                        .withBoolean(ARouterConstants.KeyWord.KEY_MEDIA_IS_PICTURE_SMALL, false)
                        .withBoolean(ARouterConstants.KeyWord.KEY_MEDIA_IS_SINGLE_LAYOUT, false)
                        .withBoolean(ARouterConstants.KeyWord.KEY_MEDIA_IS_READ_ONLY, mIsReadOnly)
                        .withInt(ARouterConstants.KeyWord.KEY_MEDIA_GRID_SPAN_COUNT, 3)
                        .withInt(ARouterConstants.KeyWord.KEY_MEDIA_MAX_NUM, 5)
                        .withInt(ARouterConstants.KeyWord.KEY_MEDIA_BACKGROUND_COLOR,
                                R.color.bg_white)
                        .navigation();

        //在部分低端手机，调用单独拍照时可能产生内存不足的问题，导致activity被回收，所以不重复创建fragment
        FragmentUtils.addFirstFragment(getActivity(), mSelectedMediaFragment, R.id.picture_content);

    }

    private void initGoodsImageList() {
        if (null != mGoodsResult.getGoodsImageUrl() && mGoodsResult.getGoodsImageUrl().size() > 0) {
            for (int i = 0; i < mGoodsResult.getGoodsImageUrl().size(); i++) {
                JMediaItem mediaItem = new JMediaItem();
                mediaItem.setMimeType(JMediaItem.TYPE_IMAGE);
                mediaItem.setFilePath(mGoodsResult.getGoodsImageUrl().get(i));
                mGoodsImageList.add(mediaItem);
            }
        } else {
          /*  ERPMediaItem mediaItem = new ERPMediaItem();
            mediaItem.setMimeType(ERPMediaItem.TYPE_IMAGE);
            mediaItem.setFilePath("http://img.qqzhi.com/uploads/2018-11-29/120313953.jpg");
            mGoodsImageList.add(mediaItem);

            mediaItem = new ERPMediaItem();
            mediaItem.setMimeType(ERPMediaItem.TYPE_IMAGE);
            mediaItem.setFilePath("http://img.qqzhi.com/uploads/2019-04-13/135309415.jpg");
            mGoodsImageList.add(mediaItem);

            //http://http://img.qqzhi.com/uploads/2019-04-05/043646862.jpg
            mediaItem = new ERPMediaItem();
            mediaItem.setMimeType(ERPMediaItem.TYPE_IMAGE);
            mediaItem.setFilePath("http://img.qqzhi.com/uploads/2019-04-13/115041745.jpg");
            mGoodsImageList.add(mediaItem);

            mediaItem = new ERPMediaItem();
            mediaItem.setMimeType(ERPMediaItem.TYPE_IMAGE);
            mediaItem.setFilePath("http://img.qqzhi.com/uploads/2019-04-05/043646862.jpg");
            mGoodsImageList.add(mediaItem);*/
        }

    }

    private void bindData() {
        mBinding.setGoods(mGoodsResult);
        getBillTypeList();

        if (!TextUtils.isEmpty(mGoodsResult.getId())) {
            mBinding.letvGoodsName.setEditTextValue(mGoodsResult.getName());
            mBinding.letvGoodsDescription.setEditTextValue(mGoodsResult.getDescription());
            mBinding.letvGoodsPrice.setEditTextValue(mGoodsResult.convertDoubleToString(mGoodsResult.getPrice()));
            mBinding.letvGoodsStock.setEditTextValue(mGoodsResult.convertIntToString(mGoodsResult.getStock()));
        }

       /* mBinding.setOriginal(mOriginalDetailResult);
        mDetailsAdapter = new OriginalDetailsEditAdapter(mOriginalDetailResult.getDetailList(),
                R.layout.item_original_bill_detail_edit);
        mLayoutManager = new LinearLayoutManager(mContext);
        //mLayoutManager.setSmoothScrollbarEnabled(true);
        mBinding.originalDetailRecyclerView.setHasFixedSize(true);
        mBinding.originalDetailRecyclerView.setLayoutManager(mLayoutManager);
        mBinding.originalDetailRecyclerView.setAdapter(mDetailsAdapter);

        mDetailsAdapter.setOnItemClickListener(new Monitor.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View view, final int position) {
                //如果position为final，多次快速无序删除时会出错下标越界的异常
                mPosition = position;
                mAlertDialog.setMessage(CommonUtils.getString(mContext,R.string
                .delete_purchase_sale,
                        CommonUtils.getString(mOriginalDetailResult.getDetailList().get
                        (mPosition).getServiceName())))
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //如果有header，还要+1
                                mOriginalDetailResult.getDetailList().remove(mPosition);
                                mDetailsAdapter.notifyItemRemoved(mPosition);    //刷新被删除的地方
                                mDetailsAdapter.notifyItemRangeChanged(mPosition,
                                        mOriginalDetailResult.getDetailList().size() - mPosition);
                                //刷新被删除数据，以及其后面的数据
                                if (mDetailsAdapter.getItemCount() == 0) {
                                    mDetailsAdapter.notifyDataSetChanged();
                                    mBinding.originalDetailRecyclerView.setVisibility(View.GONE);
                                }
                                mAlertDialog.dismiss();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAlertDialog.dismiss();
                            }
                        }).show();
            }
        });*/
    }

    private void updateOriginal(String originalJson) {
       /* showWaiting();
        mViewModel.updateOriginalBill(originalJson).observe(this,
                new Observer<ApiResponse<BaseWrapper<String>>>() {
                    @Override
                    public void onChanged(ApiResponse<BaseWrapper<String>> response) {
                        boolean success = false;
                        closeProgressDialog();
                        if (response != null && response.isSuccess()) {
                            BaseWrapper<String> result = response.body;
                            if (result != null) {
                                success = true;
                                WidgetUtils.toastMessage(mContext, result.getResult());
                            }
                            Log.D(TAG, "upload original is " + response.isSuccess() + " the " +
                                    "message is " + response.body);
                        }

                        if (!success) {
                            WidgetUtils.toastMessage(mContext, R.string.upload_failed);
                            Log.E(TAG, "upload original is failed the result is " + response);
                        }
                        close();
                    }
                });*/
    }

    private void getBillTypeList() {
      /*  showWaiting();
        mViewModel.getBillTypeAll().observe(this,
                new Observer<ApiResponse<BaseWrapperEntities<BillTypeResult>>>() {
                    @Override
                    public void onChanged(ApiResponse<BaseWrapperEntities<BillTypeResult>> 
                    response) {
                        boolean success = false;
                        closeProgressDialog();
                        if (response != null && response.isSuccess()) {
                            BaseWrapperEntities<BillTypeResult> result = response.body;
                            if (result != null) {
                                success = true;
                                if (mCategoryList != null) {
                                    mCategoryList.clear();
                                }
                                mCategoryList.addAll(result.getResult());
                                mCategoryPickerView.bindData(mCategoryList);
                            }
                        }

                        if (!success) {
                            WidgetUtils.toastMessage(mContext, R.string.error_get_document_type);
                            Log.E(TAG, "upload original is failed the result is " + response);
                        }
                    }
                });*/

        //TODO: for test data
        GoodsCategoryResult categoryResult;
        for (int i = 0; i < 5; i++) {
            categoryResult = new GoodsCategoryResult();
            categoryResult.setId(200 + i);
            categoryResult.setName("商品分类" + i);
            categoryResult.setDescription("商品分类" + i);
            mCategoryList.add(categoryResult);
        }

        mCategoryPickerView.bindData(mCategoryList);
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initViews() {

    }

    @Override
    public boolean doBackPress() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("KZ", "data=" + data);
    }

}
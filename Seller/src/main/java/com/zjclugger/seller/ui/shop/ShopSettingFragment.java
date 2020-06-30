package com.zjclugger.seller.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;

import com.zjclugger.lib.base.BaseFragment;
import com.zjclugger.lib.entity.common.JMediaItem;
import com.zjclugger.lib.ui.widget.JAlertDialog;
import com.zjclugger.lib.utils.CommonUtils;
import com.zjclugger.lib.utils.FragmentUtils;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.router.ARouterConstants;
import com.zjclugger.router.utils.ARouterUtils;
import com.zjclugger.seller.R;
import com.zjclugger.seller.business.PreferencesUtil;
import com.zjclugger.seller.databinding.FragmentShopSettingsBinding;
import com.zjclugger.seller.ui.SellerMainActivity;
import com.zjclugger.seller.utils.SellerConstants;
import com.zjclugger.seller.webapi.response.ShopInfoResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 门店设置<br>
 * Created by King.Zi on 2020/5/13.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ShopSettingFragment extends BaseFragment {
    private final static String TAG = "ShopSettings";
    private ShopInfoResult mShopInfoResult;
    private ShopInfoResult mShopInfoResultCache;
    private FragmentShopSettingsBinding mBinding;
    private BaseFragment mQualificationFragment;
    private BaseFragment mADFragment;
    private BaseFragment mShopImageFragment;
    private ArrayList<JMediaItem> mShopPhotoList = new ArrayList<>();
    private ArrayList<JMediaItem> mQualificationImageList = new ArrayList<>();
    private ArrayList<JMediaItem> mADImageList = new ArrayList<>();

    private boolean mIsJoined;
    private View.OnClickListener mSubmitListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarStyle(R.color.bg_white, false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_settings, container,
                false);
        mView = mBinding.getRoot();

        if (null != mArguments) {
            mIsJoined = mArguments.getBoolean(SellerConstants.Keywords.KEY_IS_JOINED);
            mShopInfoResult = new ShopInfoResult();
            mShopInfoResultCache = mShopInfoResult;
            getDataFromServer();
            mSubmitListener = v -> {
               /* if (-1 != mCategoryId) {
                    mGoodsResult.setCategoryId(mCategoryId);
                }*/

                ARouterUtils.getSelectedPictureList(mQualificationFragment);
                ARouterUtils.getSelectedPictureList(mADFragment);
                String cacheValue = CommonUtils.object2Json(mShopInfoResultCache);
                String currentValue = CommonUtils.object2Json(mShopInfoResult);

                if (!cacheValue.equalsIgnoreCase(currentValue)) {
                    updateShopInfo(currentValue);
                } else {
                    if (mIsJoined) {
                        close();
                    } else {
                        showMessage("请完善门店信息后再提交！");
                    }
                }
            };

            if (mIsJoined) {
                initDetailTitleViews("门店设置", "修改", mSubmitListener, true);

        /*mPayModeView.getValueView().setCompoundDrawablesWithIntrinsicBounds(mContext
        .getResources().getDrawable(R.mipmap.ic_alipay_small), null,
                        null,null);*/
            } else {
                //名称、品类、电话、地址、头像、资质
                initDetailTitleViews("入驻申请", "提交", mSubmitListener, false);
            }

            initViews();
        } else {
            close();
        }
        return mView;
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initViews() {
        initDialog(R.string.shop_name, 50, "请输入门店名称", mBinding.elvvShopName,
                mShopInfoResult.getName());
        initDialog(R.string.shop_phone, 15, "请输入门店电话", mBinding.elvvShopPhone,
                mShopInfoResult.getPhone());
        initDialog(R.string.shop_address, 100, "请输入门店地址", mBinding.elvvShopAddress,
                mShopInfoResult.getAddress());
        initDialog(R.string.shop_status, 10, "请输入门店营业状态", mBinding.elvvShopStatus,
                mShopInfoResult.getStatus());
        initDialog(R.string.shop_work_time, 20, "请输入门店营业时间", mBinding.elvvWorkTime,
                mShopInfoResult.getWorkTime());

        //门店图片
        if (mShopInfoResult != null) {
            if (!TextUtils.isEmpty(mShopInfoResult.getShopImageUrl())) {
                JMediaItem mediaItem = new JMediaItem();
                mediaItem.setMimeType(JMediaItem.TYPE_IMAGE);
                mediaItem.setFilePath(mShopInfoResult.getShopImageUrl());
                mShopPhotoList.add(mediaItem);
            }

            ImageUtils.loadImage(mContext, mBinding.citvShopPhoto,
                    mShopInfoResult.getShopImageUrl());
        }

        Bundle shopPhotoBundle = new Bundle();
        shopPhotoBundle.putParcelableArrayList(ARouterConstants.KeyWord.KEY_MEDIA_SELECTED_LIST,
                null);
        mShopImageFragment = ARouterUtils.getSelectPictureFragment(shopPhotoBundle, "", false,
                false, false, false,
                true, R.layout.tile_item_transparent_layout, R.id.item_media_layout, 3, 5,
                R.color.transparent);
        FragmentUtils.addFirstFragment(getActivity(), mShopImageFragment, R.id.shop_photo_content);

        //营业资质
        if (mShopInfoResult != null && mShopInfoResult.getQualificationUrls() != null) {
            for (int i = 0; i < mShopInfoResult.getQualificationUrls().size(); i++) {
                JMediaItem mediaItem = new JMediaItem();
                mediaItem.setMimeType(JMediaItem.TYPE_IMAGE);
                mediaItem.setFilePath(mShopInfoResult.getQualificationUrls().get(i));
                mQualificationImageList.add(mediaItem);
            }
        }
        Bundle qualificationBundle = new Bundle();
        qualificationBundle.putParcelableArrayList(ARouterConstants.KeyWord.KEY_MEDIA_SELECTED_LIST,
                mQualificationImageList);
        mQualificationFragment = getFragment(qualificationBundle);
        FragmentUtils.addFirstFragment(getActivity(), mQualificationFragment,
                R.id.qualification_content);

        //广告
        if (mShopInfoResult != null && mShopInfoResult.getAdImageUrls() != null) {
            for (int i = 0; i < mShopInfoResult.getAdImageUrls().size(); i++) {
                JMediaItem mediaItem = new JMediaItem();
                mediaItem.setMimeType(JMediaItem.TYPE_IMAGE);
                mediaItem.setFilePath(mShopInfoResult.getAdImageUrls().get(i));
                mADImageList.add(mediaItem);
            }
        }
        Bundle adBundle = new Bundle();
        adBundle.putParcelableArrayList(ARouterConstants.KeyWord.KEY_MEDIA_SELECTED_LIST,
                mADImageList);
        mADFragment = getFragment(adBundle);
        FragmentUtils.addFirstFragment(getActivity(), mADFragment, R.id.ad_content);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShopPhotoList = ARouterUtils.getSelectedPictureList(mShopImageFragment);
        if (mShopPhotoList != null && mShopPhotoList.size() > 0) {
            //todo:上传头像
            // mShopInfoResult.setShopImageUrl(mShopPhotoList.get(0).);
            ImageUtils.loadImage(mContext, mBinding.citvShopPhoto,
                    mShopPhotoList.get(0).getFilePath());
        }
    }

    private BaseFragment getFragment(Bundle params) {
        return ARouterUtils.getSelectPictureFragment(params, "", false, false, false, false,
                false, 0, 0, 3, 5,
                R.color.bg_white);
    }

    private void initDialog(@StringRes int titleResId, int maxLength, String hint,
                            ExtendLabelValueView view, String originValue) {
        JAlertDialog dialog = new JAlertDialog(mContext);
        dialog.setTitle(getString(titleResId))
                .setCanceledOnTouchOutside(false)
                .setLeftButtonColor(R.color.text_gray)
                .setRightButtonColor(R.color.text_selected)
                .setOnClickListener(maxLength, hint, v -> dialog.dismiss(), v -> {
                    String value = dialog.getContentView().getText().toString();
                    view.setValueText(value);

                    if (!value.equalsIgnoreCase(originValue)) {
                        switch (titleResId) {
                            case R.string.shop_name:
                                mShopInfoResult.setName(value);
                                break;
                            case R.string.shop_phone:
                                mShopInfoResult.setPhone(value);
                                break;
                            case R.string.shop_address:
                                mShopInfoResult.setAddress(value);
                                break;
                            case R.string.shop_status:
                                //todo:下拉选择?
                                mShopInfoResult.setStatus(value);
                                break;
                            case R.string.shop_work_time:
                                mShopInfoResult.setWorkTime(value);
                                break;
                            default:
                                break;
                        }
                    }

                    dialog.dismiss();
                });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.getContentView().setText(originValue);
                dialog.show();
            }
        });
    }

    private void updateShopInfo(String originalJson) {
        if (!mIsJoined) {
            JAlertDialog dialog = new JAlertDialog(mContext);
            dialog.setMessage("审核通过，进入系统!")
                    .setSingleButton(true)
                    .setConfirmText("确认")
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            //TODO:
                            PreferencesUtil.getInstance().put(SellerConstants.Keywords.KEY_IS_JOINED, true);
                            startActivity(new Intent(getActivity(), SellerMainActivity.class));
                            close();
                        }
                    }, null);
            dialog.show();
        }
    }

    private void getDataFromServer() {
        //test data
        if (mIsJoined) {
            //TODO:get from server
            initTestData();
        }
        mShopInfoResultCache = CommonUtils.copyObject(mShopInfoResult);
        mBinding.setShopInfo(mShopInfoResult);
    }

    private void initTestData() {
        List<String> imageList = new ArrayList<>();
        imageList.add("http://img.qqzhi.com/uploads/2019-04-06/103454404.jpg");
        imageList.add("http://img.qqzhi.com/uploads/2019-03-26/203922802.jpg");
        imageList.add("http://img.qqzhi.com/uploads/2019-03-25/153128925.jpg");
        imageList.add("http://img.qqzhi.com/uploads/2019-05-23/184503893.jpg");
        imageList.add("http://img.qqzhi.com/uploads/2019-04-19/181211409.jpg");
        imageList.add("http://img.qqzhi.com/uploads/2019-04-03/060630691.jpg");
        imageList.add("http://img.qqzhi.com/uploads/2019-03-25/121825762.jpg");

        List<String> adImageList = new ArrayList<>();
        adImageList.add("http://img.qqzhi.com/uploads/2019-04-06/103454404.jpg");
        adImageList.add("http://img.qqzhi.com/uploads/2019-03-25/153128925.jpg");
        adImageList.add("http://img.qqzhi.com/uploads/2019-04-19/181211409.jpg");
        adImageList.add("http://img.qqzhi.com/uploads/2019-04-03/060630691.jpg");
        adImageList.add("http://img.qqzhi.com/uploads/2019-03-25/121825762.jpg");

        ShopInfoResult result = new ShopInfoResult();
        result.setId("20052");
        result.setCategoryId(202);
        result.setName("商铺名称52");
        result.setQualificationUrls(imageList);
        result.setCategoryName("测试分类52");
        result.setDescription("这么好的商铺无须描述");
        result.setPhone("188-8888-9999");
        result.setAddress("山东省青岛市市北区抚顺路988号福彩天天乐");
        result.setAdImageUrls(adImageList);
        result.setShopImageUrl("http://img.qqzhi.com/uploads/2019-05-23/184503893.jpg");
        result.setWorkTime("9:00--21:00");
        result.setStatus("营业中");

        mShopInfoResult = result;
    }

    @Override
    public void closeFloatView() {
    }

    @Override
    public boolean doBackPress() {
        //return mIsJoined ? true : false;
        return false;
    }
}
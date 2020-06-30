package com.zjclugger.buyer.ui.goods;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zjclugger.buyer.R;
import com.zjclugger.buyer.webapi.response.ProductResult;
import com.zjclugger.lib.ui.widget.JDialog;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.utils.WidgetUtils;
import com.wuhenzhizao.sku.bean.Sku;
import com.wuhenzhizao.sku.bean.SkuAttribute;
import com.wuhenzhizao.sku.view.OnSkuListener;
import com.wuhenzhizao.sku.view.SkuSelectScrollView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品SKU对话框<br>
 * Created by King.Zi on 2020/4/30.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class GoodsSkuDialog extends JDialog {
    @BindView(R.id.tv_sku_info)
    TextView mSkuInfoView;
    @BindView(R.id.ll_sku_price)
    LinearLayout mPriceLayout;
    @BindView(R.id.tv_goods_price)
    TextView mGoodsPriceView;
    @BindView(R.id.tv_goods_unit)
    TextView mGoodsUnitView;
    @BindView(R.id.sku_list_scroll_view)
    SkuSelectScrollView mSkuListView;
    @BindView(R.id.sku_quantity_label)
    TextView mQuantityLabelView;
    @BindView(R.id.sku_quantity_value)
    TextView mQuantityValueView;
    @BindView(R.id.sku_quantity_plus_view)
    TextView mSkuPlusView;
    @BindView(R.id.sku_quantity_minus_view)
    TextView mSkuMinusView;
    @BindView(R.id.et_sku_quantity_input)
    EditText mSkuInputEditTextView;
    @BindView(R.id.sku_image_view)
    ImageView mGoodsImageView;
    @BindView(R.id.btn_submit)
    Button mSubmitButton;

    private ProductResult product;
    private List<Sku> skuList;
    private Callback callback;

    private Sku selectedSku;
    private String priceFormat;
    private String stockQuantityFormat;

    public GoodsSkuDialog(@NonNull Context context) {
        super(context, R.style.SkuStyle_CustomDialog, R.layout.dialog_goods_sku);
        ButterKnife.bind(this, getDialogView());
        initView();
    }


    private void initView() {
        getDialogView().findViewById(R.id.ib_sku_close).setOnClickListener(v -> close());

        mSkuMinusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = mSkuInputEditTextView.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 1) {
                    String newQuantity = String.valueOf(quantityInt - 1);
                    mSkuInputEditTextView.setText(newQuantity);
                    mSkuInputEditTextView.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt - 1);
                }
            }
        });
        mSkuPlusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = mSkuInputEditTextView.getText().toString();
                if (TextUtils.isEmpty(quantity) || selectedSku == null) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt < selectedSku.getStockQuantity()) {
                    String newQuantity = String.valueOf(quantityInt + 1);
                    mSkuInputEditTextView.setText(newQuantity);
                    mSkuInputEditTextView.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt + 1);
                }
            }
        });
        mSkuInputEditTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != EditorInfo.IME_ACTION_DONE || selectedSku == null) {
                    return false;
                }
                String quantity = mSkuInputEditTextView.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return false;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt <= 1) {
                    mSkuInputEditTextView.setText("1");
                    mSkuInputEditTextView.setSelection(1);
                    updateQuantityOperator(1);
                } else if (quantityInt >= selectedSku.getStockQuantity()) {
                    String newQuantity = String.valueOf(selectedSku.getStockQuantity());
                    mSkuInputEditTextView.setText(newQuantity);
                    mSkuInputEditTextView.setSelection(newQuantity.length());
                    updateQuantityOperator(selectedSku.getStockQuantity());
                } else {
                    mSkuInputEditTextView.setSelection(quantity.length());
                    updateQuantityOperator(quantityInt);
                }
                return false;
            }
        });
        mSkuListView.setListener(new OnSkuListener() {
            @Override
            public void onUnselected(SkuAttribute unselectedAttribute) {
                selectedSku = null;
                ImageUtils.loadImage(mContext, mGoodsImageView, product.getMainImage());

                //GImageLoader.displayUrl(context, binding.ivSkuLogo, product.getMainImage());
                mQuantityValueView.setText(String.format(stockQuantityFormat,
                        product.getStockQuantity()));
                String firstUnselectedAttributeName = mSkuListView.getFirstUnelectedAttributeName();
                mSkuInfoView.setText("请选择：" + firstUnselectedAttributeName);
                mSubmitButton.setEnabled(false);
                String quantity = mSkuInputEditTextView.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }

            @Override
            public void onSelect(SkuAttribute selectAttribute) {
                String firstUnselectedAttributeName =
                        mSkuListView.getFirstUnelectedAttributeName();
                mSkuInfoView.setText("请选择：" + firstUnselectedAttributeName);
            }

            @Override
            public void onSkuSelected(Sku sku) {
                selectedSku = sku;
                ImageUtils.loadImage(mContext, mGoodsImageView, selectedSku.getMainImage());
                // GImageLoader.displayUrl(context, binding.ivSkuLogo, selectedSku.getMainImage());
                List<SkuAttribute> attributeList = selectedSku.getAttributes();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < attributeList.size(); i++) {
                    if (i != 0) {
                        builder.append("　");
                    }
                    SkuAttribute attribute = attributeList.get(i);
                    builder.append("\"" + attribute.getValue() + "\"");
                }
                mSkuInfoView.setText("已选：" + builder.toString());
                mQuantityValueView.setText(String.format(stockQuantityFormat,
                        selectedSku.getStockQuantity()));
                mSubmitButton.setEnabled(true);
                String quantity = mSkuInputEditTextView.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = mSkuInputEditTextView.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 0 && quantityInt <= selectedSku.getStockQuantity()) {
                    callback.onAdded(selectedSku, quantityInt);

                    //TODO: for test add to local db

                    close();
                } else {
                    WidgetUtils.toast(mContext, "商品数量超出库存，请修改数量", false);
                }
            }
        });
    }

    public void setData(final ProductResult product, Callback callback) {
        this.product = product;
        this.skuList = product.getSkus();
        this.callback = callback;

        priceFormat = mContext.getString(R.string.goods_price);
        stockQuantityFormat = mContext.getString(R.string.goods_detail_sku_stock);

        updateSkuData();
        updateQuantityOperator(1);
    }

    private void updateSkuData() {
        mSkuListView.setSkuList(product.getSkus());

        Sku firstSku = product.getSkus().get(0);
        if (firstSku.getStockQuantity() > 0) {
            selectedSku = firstSku;
            // 选中第一个sku
            mSkuListView.setSelectedSku(selectedSku);
            ImageUtils.loadImage(mContext, mGoodsImageView, selectedSku.getMainImage());
            /*mGoodsPriceView.setText(CommonUtils.getString(mContext, R.string.goods_price,
                    selectedSku.getSellingPrice() / 100));*/
            //TODO:价格应该是float/double,不应该是long
            mGoodsPriceView.setText(String.valueOf(selectedSku.getSellingPrice() / 100));
            mGoodsUnitView.setText("/" + product.getMeasurementUnit());
            mQuantityValueView.setText(String.format(stockQuantityFormat,
                    selectedSku.getStockQuantity()));
            mSubmitButton.setEnabled(selectedSku.getStockQuantity() > 0);
            List<SkuAttribute> attributeList = selectedSku.getAttributes();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < attributeList.size(); i++) {
                if (i != 0) {
                    builder.append("　");
                }
                SkuAttribute attribute = attributeList.get(i);
                builder.append("\"" + attribute.getValue() + "\"");
            }
            mSkuInfoView.setText("已选：" + builder.toString());
        } else {
            ImageUtils.loadImage(mContext, mGoodsImageView, product.getMainImage());
            //GImageLoader.displayUrl(context, binding.ivSkuLogo, product.getMainImage());
           /* mGoodsPriceView.setText(CommonUtils.getString(mContext, R.string.goods_price,
                    product.getSellingPrice() / 100));*/
           //todo:暂时这样数据类型需要修改
            mGoodsPriceView.setText(String.valueOf(product.getSellingPrice() / 100));
            mGoodsUnitView.setText("/" + product.getMeasurementUnit());
            mQuantityValueView.setText(String.format(stockQuantityFormat,
                    product.getStockQuantity()));
            mSubmitButton.setEnabled(false);
            mSkuInfoView.setText("请选择：" + skuList.get(0).getAttributes().get(0).getKey());
        }
    }

    private void updateQuantityOperator(int newQuantity) {
        if (selectedSku == null) {
            mSkuMinusView.setEnabled(false);
            mSkuPlusView.setEnabled(false);
            mSkuInputEditTextView.setEnabled(false);
        } else {
            if (newQuantity <= 1) {
                mSkuMinusView.setEnabled(false);
                mSkuPlusView.setEnabled(true);
            } else if (newQuantity >= selectedSku.getStockQuantity()) {
                mSkuMinusView.setEnabled(true);
                mSkuPlusView.setEnabled(false);
            } else {
                mSkuMinusView.setEnabled(true);
                mSkuPlusView.setEnabled(true);
            }
            mSkuInputEditTextView.setEnabled(true);
        }

    }

   /* @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 解决键盘遮挡输入框问题
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.getDecorView().setPadding(0, 0, 0, 0);
        // KeyboardConflictCompat.assistWindow(getWindow());
        AppUtils.transparencyBar(getWindow());
    }
*/

    public interface Callback {
        void onAdded(Sku sku, int quantity);
    }
}

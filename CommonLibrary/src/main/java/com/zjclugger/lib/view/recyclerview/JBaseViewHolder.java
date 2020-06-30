package com.zjclugger.lib.view.recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zjclugger.lib.utils.ImageUtils;
import com.zjclugger.lib.view.ExtendLabelValueView;
import com.zjclugger.lib.view.ImageTextView;
import com.zjclugger.lib.view.LabelValueView;

public class JBaseViewHolder extends BaseViewHolder {
    public JBaseViewHolder(View view) {
        super(view);
    }

    /**
     * 设置标签
     *
     * @param viewId    LabelValueView
     * @param labelText
     * @return
     */
    public JBaseViewHolder setLabelText(@IdRes int viewId, String labelText) {
        LabelValueView view = getView(viewId);
        view.setLabelText(labelText);
        return this;
    }

    /**
     * 设置标签值
     *
     * @param viewId    LabelValueView
     * @param valueText
     * @return
     */
    public JBaseViewHolder setValueText(@IdRes int viewId, String valueText) {
        LabelValueView view = getView(viewId);
        view.setValueText(valueText);
        return this;
    }

    public JBaseViewHolder setLabelValueText(@IdRes int viewId, String labelText,
                                             String valueText) {
        LabelValueView view = getView(viewId);
        view.setLabelText(labelText);
        view.setValueText(valueText);
        return this;

    }

    public JBaseViewHolder setImageText(@IdRes int viewId, String valueText) {
        ImageTextView view = getView(viewId);
        view.setImageText(valueText);
        return this;
    }

    public JBaseViewHolder setImageTextView(@IdRes int viewId, String valueText,
                                            @DrawableRes int imageResId) {
        ImageTextView view = getView(viewId);
        view.setImageText(valueText);
        view.setImageView(imageResId);
        return this;
    }

    public JBaseViewHolder setImageTextView(@IdRes int viewId, @StringRes int valueResId,
                                            @DrawableRes int imageResId) {
        ImageTextView view = getView(viewId);
        view.setImageText(valueResId);
        view.setImageView(imageResId);
        return this;
    }

    public JBaseViewHolder setVisibility(@IdRes int viewId, boolean visible) {
        getView(viewId).setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public JBaseViewHolder setExtendLabelText(@IdRes int viewId, String labelText) {
        ExtendLabelValueView view = getView(viewId);
        view.setLabelText(labelText);
        return this;
    }

    public JBaseViewHolder setExtendValueText(@IdRes int viewId, String valueText) {
        ExtendLabelValueView view = getView(viewId);
        view.setValueText(valueText);
        return this;
    }

    public JBaseViewHolder setExtendMiddleText(@IdRes int viewId, String middleText) {
        ExtendLabelValueView view = getView(viewId);
        view.getMiddleView().setText(middleText);
        return this;
    }

    public JBaseViewHolder setExtendLabelViewText(@IdRes int viewId, String labelText,
                                                  String valueText) {
        ExtendLabelValueView view = getView(viewId);
        view.setLabelText(labelText);
        view.setValueText(valueText);
        return this;
    }

    public JBaseViewHolder setExtendLabelViewText(@IdRes int viewId, String labelText,
                                                  String middleText,
                                                  String valueText) {
        ExtendLabelValueView view = getView(viewId);
        view.setLabelText(labelText);
        view.getMiddleView().setText(middleText);
        view.setValueText(valueText);
        return this;
    }

    public JBaseViewHolder setImageView(@IdRes int viewId, Context context, String imageUrl) {
        ImageView view = getView(viewId);
        ImageUtils.loadImage(context, view, imageUrl);
        return this;
    }
}
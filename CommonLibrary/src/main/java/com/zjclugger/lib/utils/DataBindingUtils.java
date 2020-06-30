package com.zjclugger.lib.utils;

import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.adapters.ListenerUtil;
import androidx.databinding.adapters.TextViewBindingAdapter;

import com.bumptech.glide.Glide;
import com.zjclugger.lib.R;
import com.zjclugger.lib.view.ExtendLabelValueView;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class DataBindingUtils {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView imageView, int resId) {
        imageView.setImageResource(resId);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url)
                .placeholder(R.drawable.image_loading)
                .error(R.mipmap.img_error_default)
                .into(imageView);
    }

    @BindingAdapter("valueText")
    public static void setValueText(ExtendLabelValueView view, String value) {
        if (view != null) {
            String valueText = view.getValueView().getText() == null ? "" :
                    view.getValueView().getText().toString();
            value = value == null ? "" : value;
            if (valueText.equalsIgnoreCase(value)) {
                return;
            }
            view.setValueText(value);
        }
    }

    @BindingAdapter("editText")
    public static void setEditText(ExtendLabelValueView view, String value) {
        if (view != null) {
            String edTextString = view.getEditTextView().getText() == null ? "" :
                    view.getEditTextView().getText().toString();
            value = value == null ? "" : value;
            if (edTextString.equalsIgnoreCase(value)) {
                return;
            }
            view.setEditTextValue(value);
        }
    }

    @InverseBindingAdapter(attribute = "editText", event = "valueAttrChanged")
    public static String getEditText(ExtendLabelValueView view) {
        return view.getEditTextView().getText().toString();
    }

    @BindingAdapter(value = {"android:beforeTextChanged", "android:onTextChanged",
            "android:afterTextChanged", "valueAttrChanged"}, requireAll = false)
    public static void setTextWatcher(
            ExtendLabelValueView view,
            final TextViewBindingAdapter.BeforeTextChanged before,
            final TextViewBindingAdapter.OnTextChanged on,
            final TextViewBindingAdapter.AfterTextChanged after,
            final InverseBindingListener valueAttrChanged) {
        TextWatcher newWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (before != null) {
                    before.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (on != null) {
                    on.onTextChanged(s, start, before, count);
                }
                if (valueAttrChanged != null) {
                    valueAttrChanged.onChange();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (after != null) {
                    after.afterTextChanged(s);
                }
            }
        };
        TextWatcher oldWatcher = ListenerUtil.trackListener(view, newWatcher, R.id.textWatcher);
        if (oldWatcher != null) {
            view.getEditTextView().removeTextChangedListener(oldWatcher);
        }
        view.getEditTextView().addTextChangedListener(newWatcher);
    }
}
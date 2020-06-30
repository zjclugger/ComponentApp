package com.zjclugger.oa.utils;

import android.graphics.drawable.Drawable;

import com.zjclugger.lib.utils.ERPUtils;
import com.zjclugger.lib.view.CircleImageTextView;

import java.io.UnsupportedEncodingException;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class OAUtils {
    public static void setPhotoView(CircleImageTextView view, Drawable photo, String text,
                                    int length) {
        try {
            if (photo != null) {
                view.setImage(photo);
            } else {
                view.setCircleImageCenterText(ERPUtils.subString(text, length, "GBK", true));
            }
        } catch (UnsupportedEncodingException e) {
            view.setCircleImageCenterText(text);
        }
    }
}

package com.zjclugger.lib.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

public class SoftKeyboardUtils {
    public static void hideKeyboard(Context context, ViewGroup view) {
        view.requestFocus();
        InputMethodManager im =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            im.hideSoftInputFromWindow(view.getWindowToken(), 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showKeyboard(Context context, View view) {
        InputMethodManager im =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(view, 0);
    }
}

package com.zjclugger.lib.utils;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Typeface;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.zjclugger.lib.R;
import com.zjclugger.lib.view.recyclerview.LinearItemDecoration;

import java.util.List;

public class ViewUtils {
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_CENTER = 1;
    private static final int GRAVITY_RIGHT = 2;
    private static final int TEXT_STYLE_BOLD = 0;
    private static final int TEXT_STYLE_ITALIC = 1;
    private static final int TEXT_STYLE_ITALIC_BOLD = 2;

    private ViewUtils() {
    }

    public static void setVisibility(boolean visible, View... views) {
        for (View view : views) {
            if (view != null) {
                view.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    public static void setVisibility(int visibility, View... views) {
        for (View view : views) {
            if (view != null) {
                setVisibility(view, visibility);
            }
        }
    }

    public static void setViewVisible(List<View> viewList, View showView) {
        if (viewList != null && viewList.size() > 0) {
            for (int i = 0; i < viewList.size(); i++) {
                if (viewList.get(i).equals(showView)) {
                    viewList.get(i).setVisibility(View.VISIBLE);
                } else {
                    viewList.get(i).setVisibility(View.GONE);
                }
            }
        }
    }


    /**
     * 设置输入框提示文字的大小
     *
     * @param editText 输入框
     * @param textSize 字体大小
     */
    public static void setHintTextSize(EditText editText, int textSize) {
        if (editText == null || TextUtils.isEmpty(editText.getHint())) {
            return;
        }
        SpannableString hintText = new SpannableString(editText.getHint());
        //设置字体大小 true表示单位是sp
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, true);
        hintText.setSpan(ass, 0, hintText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(hintText));
    }

    public static void setHintTextViewSize(TextView view, int textSize) {
        if (view == null || TextUtils.isEmpty(view.getHint())) {
            return;
        }
        SpannableString hintText = new SpannableString(view.getHint());
        //设置字体大小 true表示单位是sp
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, true);
        hintText.setSpan(ass, 0, hintText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setHint(new SpannedString(hintText));
    }

    public static SpannedString getWrapperText(Context context, int fontSize, String text,
                                               int start, int end) {
        SpannableString hintText = new SpannableString(text);
        //设置字体大小 true表示单位是sp
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(fontSize, true);
        hintText.setSpan(ass, start, end == 0 ? hintText.length() : end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return new SpannedString(hintText);
    }

    public static void setEditTextDigits(EditText editText) {
        setEditTextDigits(editText, 2);
    }

    public static void setEditTextDigits(EditText editText, int digits) {
        if (editText != null) {
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setFilters(new InputFilter[]{new DigitsFilter().setDigits(digits)});
        }
    }

    public static void setWidth(View view, int width) {
        if (width > 0) {
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.width = width;
            layoutParams.weight = 0;
            view.setLayoutParams(layoutParams);
        }
    }

    public static void setHeight(View view, int height) {
        if (height > 0) {
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
    }

    public static void setWeight(View view, int weight) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.weight = weight;
        view.setLayoutParams(layoutParams);
    }

    public static void setTextStyle(TextView textView, int textStyle) {
        switch (textStyle) {
            case TEXT_STYLE_BOLD:
                textView.setTypeface(null, Typeface.BOLD);
                break;
            case TEXT_STYLE_ITALIC:
                textView.setTypeface(null, Typeface.ITALIC);
                break;
            case TEXT_STYLE_ITALIC_BOLD:
                textView.setTypeface(null, Typeface.BOLD_ITALIC);
                break;
            default:
                textView.setTypeface(null, Typeface.NORMAL);
                break;
        }
    }

    public static void setGravity(TextView textView, int gravity) {
        switch (gravity) {
            case GRAVITY_LEFT:
                textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;
            case GRAVITY_CENTER:
                textView.setGravity(Gravity.CENTER);
                break;
            case GRAVITY_RIGHT:
                textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
            default:
                break;
        }
    }

    public static void setScaleType(ImageView imageView, int scaleType) {
        switch (scaleType) {
            case 0:
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                break;
            case 1:
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 2:
                imageView.setScaleType(ImageView.ScaleType.FIT_START);
                break;
            case 3:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case 4:
                imageView.setScaleType(ImageView.ScaleType.FIT_END);
                break;
            case 5:
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                break;
            case 6:
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case 7:
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
            default:
                break;
        }
    }

    public static void setVisibility(View view, int visibility) {
        switch (visibility) {
            case 0:
                view.setVisibility(View.VISIBLE);
                break;
            case 4:
                view.setVisibility(View.INVISIBLE);
                break;
            case 8:
                view.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 列表分隔线
     *
     * @param context
     * @return
     */
    public static RecyclerView.ItemDecoration getListDivider(Context context) {
        return getListDivider(context, 10f, true);
    }

    /**
     * 列表分隔线
     *
     * @param context
     * @param isShowLastLine 是否显示最后一条分隔线
     * @return
     */
    public static RecyclerView.ItemDecoration getListDivider(Context context,
                                                             boolean isShowLastLine) {
        return getListDivider(context, 10f, isShowLastLine);
    }

    /**
     * 列表分隔线
     *
     * @param context
     * @param spanPixels     设置分割线宽（高）度
     * @param isShowLastLine 是否显示最后一条分隔线
     * @return
     */
    public static RecyclerView.ItemDecoration getListDivider(Context context, float spanPixels,
                                                             boolean isShowLastLine) {
        RecyclerView.ItemDecoration divider = new LinearItemDecoration.Builder(context)
                .setSpan(spanPixels)
                .setColorResource(R.color.list_line)
                .setShowLastLine(isShowLastLine)
                .build();
        return divider;
    }

    public static RecyclerView.ItemDecoration getListDivider(Context context, float spanPixels,
                                                             @ColorRes int colorResId,
                                                             boolean isShowLastLine) {
        RecyclerView.ItemDecoration divider = new LinearItemDecoration.Builder(context)
                .setSpan(spanPixels)
                .setColorResource(colorResId)
                .setShowLastLine(isShowLastLine)
                .build();
        return divider;
    }

    /**
     * 设置控件为只读
     *
     * @param views
     */
    public static void setReadOnly(View... views) {
        for (View view : views) {
            if (view != null) {
                view.setEnabled(false);
                view.setFocusable(false);
                view.setFocusableInTouchMode(false);
            }
        }
    }

    /**
     * dp转换为px
     *
     * @param context
     * @param value   单位dp
     * @return
     */
    public static int dp2px(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    /**
     * sp转换为px
     *
     * @param context
     * @param value   单位sp
     * @return
     */
    public static int sp2px(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }

    /**
     * px转换为dp
     *
     * @param context
     * @param value
     * @return
     */
    public static int px2dp(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().density;
        return (int) (value / v + 0.5f);
    }

    /**
     * px转换为sp
     *
     * @param context
     * @param value
     * @return
     */
    public static int px2sp(Context context, int value) {
        float v = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / v + 0.5f);
    }

    /**
     * 获取屏幕宽度(px)
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 设置比重
     *
     * @param view
     * @param weight
     */
    public static void setWeight(View view, float weight) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.weight = weight;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 将布局文件转成view，这里为了适配viewpager2中高宽必须为match_parent
     *
     * @param parent
     * @param layoutId
     * @return
     */
    public static View getView(@NonNull ViewGroup parent, @LayoutRes int layoutId) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        //这里判断高度和宽带是否都是match_parent
        if (params.height != -1 || params.width != -1) {
            params.height = -1;
            params.width = -1;
            view.setLayoutParams(params);
        }
        return view;
    }

    /**
     * 设置view圆角
     *
     * @param radius
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setViewRound(View view, float radius) {
        view.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
            }
        });
        view.setClipToOutline(true);
    }
}
package com.zjclugger.buyer.ui.banner;

import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zjclugger.lib.R;
import com.zjclugger.lib.entity.common.JListItem;
import com.zjclugger.lib.utils.ViewUtils;
import com.zjclugger.lib.view.banner.BannerImageHolder;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class BannerImageAdapter extends BannerAdapter<JListItem<String>, BannerImageHolder> {

    private boolean mIsRound;

    public BannerImageAdapter(List<JListItem<String>> dataList) {
        this(dataList, false);
    }

    public BannerImageAdapter(List<JListItem<String>> dataList, boolean isRound) {
        super(dataList);
        mIsRound = isRound;
    }

    @Override
    public BannerImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = (ImageView) ViewUtils.getView(parent, R.layout.item_banner_image);
        //通过裁剪实现圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mIsRound) {
            ViewUtils.setViewRound(imageView, 20);
        }
        return new BannerImageHolder(imageView);
    }

    @Override
    public void onBindView(BannerImageHolder holder, JListItem<String> data, int position,
                           int size) {
        //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
        Glide.with(holder.itemView)
                .load(data.getValue())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.imageView);
    }

}

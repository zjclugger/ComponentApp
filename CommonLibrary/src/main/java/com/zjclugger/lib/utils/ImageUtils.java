package com.zjclugger.lib.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zjclugger.lib.R;

/**
 * @title <br>
 * Created by King.Zi on 2020/4/9.<br>
 * Copyright (c) 2020 zjclugger.com
 */
public class ImageUtils {
    private ImageUtils() {
    }

    public static void loadImage(Context context, ImageView imageView, String imageUrl) {
        if (null == context || null == imageView || TextUtils.isEmpty(imageUrl)) {
            return;
        } else {
            final Animation animation = AnimationUtils.loadAnimation(context, R.anim.progress_img);
            imageView.setAnimation(animation);

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.image_loading)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target,
                                                    boolean isFirstResource) {
                            imageView.clearAnimation();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model,
                                                       Target<Drawable> target,
                                                       DataSource dataSource,
                                                       boolean isFirstResource) {
                            imageView.clearAnimation();
                            return false;
                        }
                    })
                    .error(R.mipmap.img_error_default)
                    .into(imageView);
        }
    }
}

package com.jyt.video.common.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.binbook.binbook.common.util.GlideHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.engine.ImageEngine;

public class Glide4Engine implements ImageEngine {
    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {

        RequestOptions requestOptions = GlideHelper.Companion.centerCrop();
        requestOptions.placeholder(placeholder);
        requestOptions.override(resize);
        Glide.with(context).load(uri).apply(requestOptions).into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        RequestOptions requestOptions = GlideHelper.Companion.centerCrop();
        requestOptions.placeholder(placeholder);
        requestOptions.override(resize);
        Glide.with(context).load(uri).apply(requestOptions).into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        RequestOptions requestOptions = GlideHelper.Companion.centerCrop();
        requestOptions.override(resizeX,resizeY);
        Glide.with(context).load(uri).apply(requestOptions).into(imageView);
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        RequestOptions requestOptions = GlideHelper.Companion.centerCrop();
        requestOptions.override(resizeX,resizeY);
        Glide.with(context).load(uri).apply(requestOptions).into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }
}

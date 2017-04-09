package com.example.zqb.photogallery.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zqb.photogallery.R;

/**
 * Created by zqb on 2017/4/7.
 */

public class LoadImage {
    private static Context mcontext;
    private static LoadImage loadImage=null;
    public LoadImage(Context context)
    {
        mcontext=context;
    }
    public void loadImage(String path, ImageView imageView)
    {
        Glide.with(mcontext)
                .load(path)
                .asBitmap()
                .placeholder(R.drawable.error_pic)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

}

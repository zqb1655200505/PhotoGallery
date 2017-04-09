package com.example.zqb.photogallery.activities;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.example.zqb.photogallery.R;

/**
 * 解决glide给图片setTag出错问题
 * Created by zqb on 2017/4/8.
 */

public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);
    }
}

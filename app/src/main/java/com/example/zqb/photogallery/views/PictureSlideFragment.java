package com.example.zqb.photogallery.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.zqb.photogallery.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

/**
 * Created by zqb on 2017/4/9.
 */

public class PictureSlideFragment extends Fragment {
    private String pic_url;
    private PhotoViewAttacher mAttacher;
    private PhotoView imageView;

    public static PictureSlideFragment getInstance(String url)
    {
        PictureSlideFragment mFragment=new PictureSlideFragment();

        //activity与fragment通信（传递参数）
        Bundle bundle=new Bundle();
        bundle.putString("pic_url",url);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pic_url = getArguments() != null ? getArguments().getString("pic_url") : "http://img.my.csdn.net/uploads/201308/31/1377949599_3416.jpg";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.fragment_picture_slide,container,false);
        imageView= (PhotoView) mView.findViewById(R.id.iv_main_pic);
        mAttacher = new PhotoViewAttacher(imageView);//使用PhotoViewAttacher为图片添加支持缩放、平移的属性

        Glide.with(getActivity())
                .load(pic_url)
                .placeholder(R.drawable.error_pic)
                .crossFade()
                .into(new GlideDrawableImageViewTarget(imageView){
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        mAttacher.update();//调用PhotoViewAttacher的update()方法，使图片重新适配布局
                    }
                });
        return mView;
    }
}

package com.example.zqb.photogallery.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.zqb.photogallery.R;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

public class ImageDetailActivity extends AppCompatActivity {

    private PhotoViewAttacher mAttacher;
    private static final String TAG="ImageDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        String pic_url=getIntent().getStringExtra("image_path");
        System.out.println(TAG+": "+pic_url);
        ImageView imageView= (ImageView) findViewById(R.id.img_detail);
        mAttacher=new PhotoViewAttacher(imageView);
        Glide.with(this)
                .load(pic_url)
                .placeholder(R.drawable.error_pic)
                .into(new GlideDrawableImageViewTarget(imageView){
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        mAttacher.update();//调用PhotoViewAttacher的update()方法，使图片重新适配布局
                    }
                });

    }
}

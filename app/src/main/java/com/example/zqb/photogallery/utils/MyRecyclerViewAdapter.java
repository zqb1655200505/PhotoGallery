package com.example.zqb.photogallery.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.zqb.photogallery.R;
import com.example.zqb.photogallery.activities.PicViewerActivity;
import com.example.zqb.photogallery.model.Images;

/**
 * Created by zqb on 2017/4/9.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.BaseViewHolder> {

    private int width;
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //获取屏幕宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager manager=((Activity)(parent.getContext())).getWindowManager();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels;
        return new OneViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position)
    {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return Images.imageUrls.length;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder
    {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
        void setData(int position) {
        }
    }

    private class OneViewHolder extends BaseViewHolder
    {
        private ImageView ivImage;
        public OneViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }

        @Override
        void setData(final int position)
        {
            String pic_url=Images.imageUrls[position];
            Glide.with(ivImage.getContext())
                    .load(pic_url)
                    .asBitmap()
                    .placeholder(R.drawable.error_pic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            //原始图片宽高
                            int imageWidth = resource.getWidth();
                            int imageHeight = resource.getHeight();

                            //按比例收缩图片
                            float ratio=(float) ((imageWidth*1.0)/(width/3*1.0));
                            int height=(int) (imageHeight*1.0/ratio);
                            ViewGroup.LayoutParams params = ivImage.getLayoutParams();
                            params.width=width/3;
                            params.height=height;
                            ivImage.setLayoutParams(params);
                            ivImage.setImageBitmap(resource);
                            ivImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ivImage.getContext(), PicViewerActivity.class);
                                    intent.putExtra("image_path", Images.imageUrls[position]);
                                    intent.putExtra("image_index",position);
                                    ivImage.getContext().startActivity(intent);
                                }
                            });
                        }
                    });
        }
    }
}

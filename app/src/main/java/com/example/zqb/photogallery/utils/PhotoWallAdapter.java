package com.example.zqb.photogallery.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zqb.photogallery.R;
import com.example.zqb.photogallery.activities.PicViewerActivity;
import com.example.zqb.photogallery.model.Images;

public class PhotoWallAdapter extends ArrayAdapter<String> implements AbsListView.OnScrollListener
{
    private GridView mPhotoWall;//GridView的实例
    private int mFirstVisibleItem;//第一张可见图片的下标
    private int mVisibleItemCount;//一屏有多少张图片可见
    //记录是否刚打开程序，用于解决进入程序不滚动屏幕，不会下载图片的问题。
    private boolean isFirstEnter = true;
    private Context mContext=null;
    public PhotoWallAdapter(Context context, int textViewResourceId, String[] objects,GridView photoWall)
    {
        super(context, textViewResourceId, objects);
        mContext=context;
        mPhotoWall = photoWall;
        mPhotoWall.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 仅当GridView静止时才去下载图片，GridView滑动时取消所有正在下载的任务
        if (scrollState == SCROLL_STATE_IDLE) {
            loadBitmaps(mFirstVisibleItem, mVisibleItemCount);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final String url = getItem(position);
        if (convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.grid_pic_item, null);
        }
        ImageView photo= (ImageView) convertView.findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PicViewerActivity.class);
                intent.putExtra("image_path", url);
                intent.putExtra("image_index",position);
                getContext().startActivity(intent);
            }
        });
        photo.setTag(url);
        Glide.with(mContext)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.error_pic)
                .into(photo);
        return convertView;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;
        // 下载的任务应该由onScrollStateChanged里调用，但首次进入程序时onScrollStateChanged并不会调用，
        // 因此在这里为首次进入程序开启下载任务。
        if (isFirstEnter && visibleItemCount > 0) {
            loadBitmaps(firstVisibleItem, visibleItemCount);
            isFirstEnter = false;
        }
    }

    /**
     * @param firstVisibleItem
     *            第一个可见的ImageView的下标
     * @param visibleItemCount
     *            屏幕中总共可见的元素数
     */
    private void loadBitmaps(int firstVisibleItem, int visibleItemCount)
    {
        for(int i=firstVisibleItem;i<firstVisibleItem+visibleItemCount;i++)
        {
            String url= Images.imageUrls[i];
            ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(url);
            Glide.with(mContext)
                    .load(url)
                    .asBitmap()
                    .placeholder(R.drawable.error_pic)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }
}

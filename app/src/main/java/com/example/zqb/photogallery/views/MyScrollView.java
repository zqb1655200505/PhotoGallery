package com.example.zqb.photogallery.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.zqb.photogallery.R;
import com.example.zqb.photogallery.activities.PicViewerActivity;
import com.example.zqb.photogallery.model.Images;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zqb on 2017/4/7.
 */

public class MyScrollView extends ScrollView implements View.OnTouchListener {


    public static final int PAGE_SIZE=15;//每页图片数目
    private int page;//当前页码
    private int columnWidth;//每列宽度
    //各列当前高度（此处共有3列）
    private int firstColumnHeight;
    private int secondColumnHeight;
    private int thirdColumnHeight;
    private boolean loadOnce;//是否已加载过一次layout，这里onLayout中的初始化只需加载一次
    private LinearLayout firstColumn=null;
    private LinearLayout secondColumn=null;
    private LinearLayout thirdColumn=null;
    private static View scrollLayout;// MyScrollView下的直接子布局。
    private static int scrollViewHeight;//MyScrollView布局的高度。
    private static int lastScrollY = -1;//记录垂直方向的滚动距离。
    //记录所有界面上的图片，用以可以随时控制对图片的释放。
    private List<ImageView> imageViewList = new ArrayList<>();
    private boolean hasIDCard=false;//检查是否有SD卡
    //在Handler中进行图片可见性检查的判断，以及加载更多图片的操作。
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MyScrollView scrollView= (MyScrollView) msg.obj;
            int scrollY=scrollView.getScrollY();

            //与之前处于同样高度，未滑动
            if(scrollY==lastScrollY)
            {
                //当滚动的最底部，并且当前没有正在下载的任务时，开始加载下一页的图片
                if(scrollViewHeight+scrollY>=scrollLayout.getHeight())
                {
                    loadMoreImages();
                }
                checkVisibility();
            }
            else
            {
                lastScrollY = scrollY;
                Message message = new Message();
                message.obj = scrollView;
                // 5毫秒后再次对滚动位置进行判断
                mHandler.sendMessageDelayed(message, 5);
            }
        }
    };

    /*
    *    进行一些关键性的初始化操作，获取MyScrollView的高度，
     *   以及得到第一列的宽度值。并在这里开始加载第一页的图片。
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Glide.get(getContext()).clearMemory();
        if(changed&&!loadOnce)
        {
            scrollViewHeight=getHeight();
            scrollLayout=getChildAt(0);
            firstColumn= (LinearLayout) findViewById(R.id.first_column);
            secondColumn= (LinearLayout) findViewById(R.id.second_column);
            thirdColumn= (LinearLayout) findViewById(R.id.third_column);
            columnWidth=firstColumn.getWidth();
            loadOnce=true;
            loadMoreImages();
        }
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        hasIDCard=Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        setOnTouchListener(this);
    }

    /*
    *   监听用户的触屏事件，如果用户手指离开屏幕则开始进行滚动检测
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            Message message=new Message();
            message.obj=this;
            mHandler.sendMessage(message);
        }
        return false;
    }

    /**
     * 遍历imageViewList中的每张图片，对图片的可见性进行检查，如果图片已经离开屏幕可见范围，则将图片替换成一张空图。
     */
    public void checkVisibility()
    {
        for(int i=0;i<imageViewList.size();i++)
        {
            ImageView imageView = imageViewList.get(i);
            int borderTop= (int) imageView.getTag(R.string.border_top);
            int borderBottom= (int) imageView.getTag(R.string.border_bottom);

            if (borderBottom > getScrollY() && borderTop < getScrollY() + scrollViewHeight)
            {
                String url= (String) imageView.getTag(R.string.image_url);
                Glide.with(getContext())
                        .load(url)
                        .asBitmap()
                        .placeholder(R.drawable.error_pic)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            }
//            else
//            {
//                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.error_pic));
//            }

        }
    }

    public void loadMoreImages()
    {

        int startIndex=page*PAGE_SIZE;
        int endIndex=(page+1)*PAGE_SIZE;
        if(startIndex< Images.imageUrls.length)
        {
            if (endIndex > Images.imageUrls.length) {
                endIndex = Images.imageUrls.length;
            }
            for(int i=startIndex;i<endIndex;i++)
            {
                final ImageView imageView=new ImageView(getContext());
                final int temp = i;
                Glide.with(getContext()).load(Images.imageUrls[i])
                        .asBitmap()
                        .placeholder(R.drawable.error_pic)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                int imageWidth = resource.getWidth();
                                int imageHeight = resource.getHeight();
                                float ratio=(float) (imageWidth*1.0/firstColumn.getWidth()*1.0);
                                int height=(int) (imageHeight*1.0/ratio);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        firstColumn.getWidth(), height);
                                imageView.setLayoutParams(params);
                                imageView.setImageBitmap(resource);
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                imageView.setPadding(5, 5, 5, 5);
                                imageView.setTag(R.string.image_url, Images.imageUrls[temp]);
                                imageView.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getContext(), PicViewerActivity.class);
                                        intent.putExtra("image_path", Images.imageUrls[temp]);
                                        intent.putExtra("image_index",temp);
                                        getContext().startActivity(intent);
                                    }
                                });
                                findColumnToAdd(imageView, height).addView(imageView);
                                imageViewList.add(imageView);
                            }
                });
            }
            page++;
        }
    }

    /**
     * 找到此时应该添加图片的一列。原则就是对三列的高度进行判断，当前高度最小的一列就是应该添加的一列。
     *
     * @param imageView
     * @param imageHeight
     * @return 应该添加图片的一列
     */
    private LinearLayout findColumnToAdd(ImageView imageView, int imageHeight) {
        if (firstColumnHeight <= secondColumnHeight)
        {
            if(firstColumnHeight<=thirdColumnHeight)//放在第一列
            {
                imageView.setTag(R.string.border_top,firstColumnHeight);
                firstColumnHeight+=imageHeight;
                imageView.setTag(R.string.border_bottom,firstColumnHeight);
                return firstColumn;
            }
            else //放在第三列
            {
                imageView.setTag(R.string.border_top,thirdColumnHeight);
                thirdColumnHeight+=imageHeight;
                imageView.setTag(R.string.border_bottom,thirdColumnHeight);
                return thirdColumn;
            }
        }
        else
        {
            if(secondColumnHeight<=thirdColumnHeight)//放在第二列
            {
                imageView.setTag(R.string.border_top,secondColumnHeight);
                secondColumnHeight+=imageHeight;
                imageView.setTag(R.string.border_bottom,secondColumnHeight);
                return secondColumn;
            }
            else //放在第三列
            {
                imageView.setTag(R.string.border_top,thirdColumnHeight);
                thirdColumnHeight+=imageHeight;
                imageView.setTag(R.string.border_bottom,thirdColumnHeight);
                return thirdColumn;
            }
        }
    }
}

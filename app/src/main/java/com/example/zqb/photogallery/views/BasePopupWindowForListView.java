package com.example.zqb.photogallery.views;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import java.util.List;

/**
 * 自定义popupWindow,用以展示列表
 * 若要定义新的需求可继承该类并重写抽象方法
 * Created by zqb on 2017/4/10.
 */

public abstract class BasePopupWindowForListView<T> extends PopupWindow {

    // 布局文件的最外层View
    protected View mContentView;
    protected Context context;
    // ListView的数据集
    protected List<T> mDatas;

    public BasePopupWindowForListView(View contentView, int width, int height, boolean focusable)
    {
        this(contentView, width, height, focusable, null);
    }

    public BasePopupWindowForListView(View contentView, int width, int height,
                                      boolean focusable, List<T> mDatas)
    {
        this(contentView, width, height, focusable, mDatas, new Object[0]);
    }
    public BasePopupWindowForListView(View contentView, int width, int height,
                                      boolean focusable, List<T> mDatas, Object... params)
    {
        super(contentView, width, height, focusable);
        this.mContentView = contentView;
        context = contentView.getContext();
        if (mDatas != null)
        {
            this.mDatas = mDatas;
        }
        if(params!=null&&params.length>0)
        {
            beforeInitWeNeedSomeParams(params);
        }

        //做一些必要的设置
        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        initViews();
        initEvents();
        init();
    }

    //抽象方法，待重写
    protected abstract void beforeInitWeNeedSomeParams(Object... params);
    public abstract void initViews();
    public abstract void initEvents();
    public abstract void init();

//    public View findViewById(int id)
//    {
//        return mContentView.findViewById(id);
//    }
    //dp转px
    protected static int dpToPx(Context context, int dp)
    {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}

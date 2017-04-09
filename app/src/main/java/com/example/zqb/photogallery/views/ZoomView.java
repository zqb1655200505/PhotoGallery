package com.example.zqb.photogallery.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义图片缩放控件
 * Created by zqb on 2017/4/9.
 */

public class ZoomView extends View {

    public static final int STATUS_INIT= 1 ;//初始化状态常量
    public static final int STATUS_ZOOM_OUT = 2;//图片放大状态常量
    public static final int STATUS_ZOOM_IN = 3;//图片缩小状态常量
    public static final int STATUS_MOVE = 4;//图片拖动状态常量
    private Matrix matrix = new Matrix();//用于对图片进行移动和缩放变换的矩阵
    private Bitmap sourceBitmap;//待展示图片
    //记录当前操作的状态，可选值为STATUS_INIT、STATUS_ZOOM_OUT、STATUS_ZOOM_IN和STATUS_MOVE
    private int currentStatus=1;

    //ZoomImageView控件的宽度,高度
    private int width;
    private int height;

    //记录两指同时放在屏幕上时，中心点的横纵坐标值
    private float centerPointX;
    private float centerPointY;

    //当前图片的宽高，随着缩放操作而变化
    private float currentBitmapWidth;
    private float currentBitmapHeight;

    //记录上次手指移动时的横纵坐标
    private float lastXMove=-1f;
    private float lastYMove=-1f;

    //记录手指在横坐标方向上的移动距离
    private float movedDistanceX;
    private float movedDistanceY;

    //记录图片在矩阵上的横向偏移值
    private float totalTranslateX;
    private float totalTranslateY;

    private float totalRatio;//记录图片在矩阵上的总缩放比例
    private float scaledRatio;//记录手指移动的距离所造成的缩放比例
    private float initRatio;//记录图片初始化时的缩放比例
    private double lastFingerDis;//记录上次两指之间的距离

    public ZoomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
}

package com.example.zqb.photogallery.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zqb.photogallery.R;
import com.example.zqb.photogallery.model.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ChooseLocalPic extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    private int mPicsSize;//存储文件夹中的图片数量
    private File mImgDir;//图片数量最多的文件夹
    private List<String> mImgs;//所有的图片
    private GridView mGirdView;
    //临时的辅助类，用于防止同一个文件夹的多次扫描
    private HashSet<String> mDirPaths = new HashSet<>();
    // 扫描拿到所有的图片文件夹
    private List<ImageInfo> mImageFloders = new ArrayList<>();
    private RelativeLayout mBottomLy;
    private TextView mChooseDir;
    private TextView mImageCount;
    int totalCount = 0;
    private int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_local_pic);
    }

    private void init()
    {

    }
}

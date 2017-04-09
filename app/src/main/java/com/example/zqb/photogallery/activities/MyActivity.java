package com.example.zqb.photogallery.activities;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.zqb.photogallery.R;

public class MyActivity extends AppCompatActivity {

    private Button btn_gridView;
    private Button btn_recyclerView;
    private Button btn_own_scrollView;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        toolbar= (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);



        btn_gridView= (Button) findViewById(R.id.btn_gridView);
        btn_recyclerView= (Button) findViewById(R.id.btn_recyclerView);
        btn_own_scrollView= (Button) findViewById(R.id.btn_own_scrollView);

        btn_gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btn_recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),WaterfallFlowUseRecyclerViewActivity.class);
                startActivity(intent);
            }
        });
        btn_own_scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),WaterfallFlowActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}

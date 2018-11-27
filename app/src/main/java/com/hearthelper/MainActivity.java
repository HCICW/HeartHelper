package com.hearthelper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MainActivity extends FragmentActivity implements View.OnClickListener ,MyListener {

    private FrameLayout mFl;
    private TextView mTvFirst;
    private TextView mTvSecond;
    private TextView mTvThird;
    private TextView mTvFoutrh;
    private TextView mTvFive;
    private FragmentManager fm;
    private FirstFragment fragment1;
    private SecondFragmeng fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private FiveFragment fragment5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    private void initView() {
        mFl = findViewById(R.id.fl);
        mTvFirst = findViewById(R.id.tv1);
        mTvSecond = findViewById(R.id.tv2);
        mTvThird = findViewById(R.id.tv3);
        mTvFoutrh = findViewById(R.id.tv4);
        mTvFive = findViewById(R.id.tv5);

        initFragment();

        mTvFirst.setOnClickListener(this);
        mTvSecond.setOnClickListener(this);
        mTvThird.setOnClickListener(this);
        mTvFoutrh.setOnClickListener(this);
        mTvFive.setOnClickListener(this);
    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        fragment1 = new FirstFragment();
        fragment2 = new SecondFragmeng();
        fragment3 = new ThirdFragment();
        fragment4 = new FourFragment();
        fragment5 = new FiveFragment();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fl, fragment1);
        fragmentTransaction.add(R.id.fl, fragment2).hide(fragment2);
        fragmentTransaction.add(R.id.fl, fragment3).hide(fragment3);
        fragmentTransaction.add(R.id.fl, fragment4).hide(fragment4);
        fragmentTransaction.add(R.id.fl, fragment5).hide(fragment5);

        fragmentTransaction.show(fragment1).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1: {
                FragmentTransaction fragmentTransaction1 = fm.beginTransaction();
                fragmentTransaction1.show(fragment1)
                        .hide(fragment2)
                        .hide(fragment3)
                        .hide(fragment4)
                        .hide(fragment5)
                        .commitAllowingStateLoss();
            }
            break;
            case R.id.tv2: {
                FragmentTransaction fragmentTransaction1 = fm.beginTransaction();
                fragmentTransaction1.show(fragment2)
                        .hide(fragment1)
                        .hide(fragment3)
                        .hide(fragment4)
                        .hide(fragment5)
                        .commitAllowingStateLoss();
            }
            break;
            case R.id.tv3: {
                FragmentTransaction fragmentTransaction1 = fm.beginTransaction();
                fragmentTransaction1.show(fragment3)
                        .hide(fragment1)
                        .hide(fragment2)
                        .hide(fragment4)
                        .hide(fragment5)
                        .commitAllowingStateLoss();
            }
            break;
            case R.id.tv4:
            {
                FragmentTransaction fragmentTransaction1 = fm.beginTransaction();
                fragmentTransaction1.show(fragment4)
                        .hide(fragment1)
                        .hide(fragment3)
                        .hide(fragment2)
                        .hide(fragment5)
                        .commitAllowingStateLoss();
            }
                break;
            case R.id.tv5:
            {
                FragmentTransaction fragmentTransaction1 = fm.beginTransaction();
                fragmentTransaction1.show(fragment5)
                        .hide(fragment1)
                        .hide(fragment3)
                        .hide(fragment4)
                        .hide(fragment2)
                        .commitAllowingStateLoss();
            }
                break;
        }
    }


    @SuppressLint("NewApi")
    public boolean camerePermission(){
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},200);
            return false;
        }else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("hci", "onRequestPermissionsResult: agree");
                Intent intent = new Intent(MainActivity.this,PreviewActivity.class);
                startActivity(intent);
            }
        }
    }


    private boolean need = false;

    public boolean getNeed(){
        return need;
    }

    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public void myage(boolean b) {
        fragment1.ageset(b);
    }

    @Override
    public void history(boolean c) {
        fragment1.hstset(c);
    }


    @Override
    public void mydata(int num) {
        fragment2.setData1(fragment1.getNum1()+num);
        fragment1.settextcolor(this.num+num);

    }

}

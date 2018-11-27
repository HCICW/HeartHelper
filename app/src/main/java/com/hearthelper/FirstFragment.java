package com.hearthelper;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class FirstFragment extends Fragment {
    private View mRootView;
    private TextView mTvNum;
    private TextView mTvCheck;
    private TextView mTvEve;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = getLayoutInflater().inflate(R.layout.fragment1, null);
            initView();
        }
        if (mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        return mRootView;
    }

    private void initView() {
        mTvNum = mRootView.findViewById(R.id.tv_num);
        mTvEve = mRootView.findViewById(R.id.tv_eve);
        setData();

        mTvCheck = mRootView.findViewById(R.id.tv_check);
        mTvCheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    if (((MainActivity) getActivity()).camerePermission()) {
                        Intent intent = new Intent(getActivity(),PreviewActivity.class);
                        getActivity().startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setData() {
        Random random = new Random();
        int i = random.nextInt(30) + 8;
        Log.e("hci", "initView: "+i);
        mNum = i;
        try {
            ((MainActivity) getActivity()).setNum(mNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        settextcolor(i);
    }


    public void settextcolor(int percent) {

        if (percent<=0) {
            percent = 5;
        }
        try {
            mTvNum.setText(percent + "%");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            GradientDrawable background = (GradientDrawable) mTvNum.getBackground();
            if (percent<=30) {
                mTvEve.setText(getResources().getString(R.string.A));
                background.setColor(Color.parseColor("#00f100"));

            }else if (percent<=50){
                mTvEve.setText(getResources().getString(R.string.B));
                background.setColor(Color.parseColor("#FFA500"));
            }else {
                mTvEve.setText(getResources().getString(R.string.C));
                background.setColor(Color.parseColor("#FF0000"));
            }
            mTvNum.setBackgroundDrawable(background);
        }
    }

    private int mNum = 0;
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            try {
                MainActivity activity = (MainActivity) getActivity();
                if (activity.getNum()!=0) {
                    mNum = activity.getNum();
                    settextcolor(mNum);
                    activity.setNum(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void ageset(boolean b1){
        settextcolor(mNum+(b1?20:0));
    }

    public void hstset(boolean b1){
        settextcolor(mNum+(b1?30:0));
    }

    public int getNum1(){
        return mNum;
    }

}

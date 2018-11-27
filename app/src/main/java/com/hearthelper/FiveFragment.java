package com.hearthelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class FiveFragment extends Fragment {
    private View mRootView;
    private EditText mEtAge;
    private CheckBox mCb;

    private MyListener myListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = getLayoutInflater().inflate(R.layout.fragment5, null);
            initView();
            myListener = ((MyListener) getActivity());
        }
        if (mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        return mRootView;
    }

    private void initView() {
        mEtAge = mRootView.findViewById(R.id.et_age);
        mEtAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCb = mRootView.findViewById(R.id.cb);
        mCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (myListener != null) {
                    myListener.history(isChecked);
                }
            }
        });
    }

    private String ageString = "";
    private boolean mCbIsCheck = false;
    public boolean ageAbove50(){
        String s  =  ageString= mEtAge.getText().toString();
        if (s.isEmpty()) {
            return false;
        }
        try {
            int i = Integer.parseInt(s);
            return i>=50;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasDiseaseHis(){
        return mCb.isChecked();
    }
    public boolean isChange(){
        if (TextUtils.equals(ageString,mEtAge.getText().toString())) {
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (isChange()) {
                if (myListener != null) {
                    myListener.myage(ageAbove50());
                }
            }
        }
    }
}

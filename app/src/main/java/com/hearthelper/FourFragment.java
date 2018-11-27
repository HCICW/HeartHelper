package com.hearthelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class FourFragment extends Fragment {
    private View mRootView;
    private EditText mEtSearch;
    private RecyclerView mRv;


    private HospticalAdapter adapter;
    private List<HospitalData.ResultData.InfoData> dataList = new ArrayList<>();
    private String url = "http://www.opendata.nhs.scot/api/3/action/datastore_search?resource_id=c698f450-eeed-41a0-88f7-c1e40a568acc&q=";
    private Map<String,String> map = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = getLayoutInflater().inflate(R.layout.fragment4, null);
            initView();
        }
        if (mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        return mRootView;
    }

    private void initView() {
        mEtSearch = mRootView.findViewById(R.id.et_search);
        mRv = mRootView.findViewById(R.id.rv);

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                synchronized (this){
                    requset(s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try {
            adapter= new HospticalAdapter(getContext(),dataList);
            mRv.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getAllInfo();
    }

    private void requset(CharSequence s) {
        map.clear();
        OkHttpUtils
                .post()
                .url(url+s)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("hci", "onError: failure"+e.getMessage());
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    HospitalData hospitalData = gson.fromJson(response, HospitalData.class);
                                    if (hospitalData.isSuccess()) {
                                        List<HospitalData.ResultData.InfoData> records = hospitalData.getResult().getRecords();
                                        if (records!=null&&!records.isEmpty()) {
                                            dataList.clear();
                                            dataList.addAll(records);

                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getAllInfo(){
        String allUrl = "https://www.opendata.nhs.scot/api/3/action/datastore_search?resource_id=c698f450-eeed-41a0-88f7-c1e40a568acc&limit=30";
        OkHttpUtils
                .post()
                .url(allUrl)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("hci", "onError: failure"+e.getMessage());
                    }

                    @Override
                    public void onResponse(final String response, int id) {

                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    HospitalData hospitalData = gson.fromJson(response, HospitalData.class);
                                    if (hospitalData.isSuccess()) {
                                        List<HospitalData.ResultData.InfoData> records = hospitalData.getResult().getRecords();
                                        if (records!=null&&!records.isEmpty()) {
                                            dataList.clear();
                                            dataList.addAll(records);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}

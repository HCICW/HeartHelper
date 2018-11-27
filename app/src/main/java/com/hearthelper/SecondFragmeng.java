package com.hearthelper;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SecondFragmeng extends Fragment implements View.OnClickListener {

    private View mRootView;
    private BarChart mChart;
    private TextView mTvAdd;
    private TextView mTvClear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = getLayoutInflater().inflate(R.layout.fragment2, null);
            initView();
        }
        if (mRootView.getParent() != null) {
            ((ViewGroup) mRootView.getParent()).removeView(mRootView);
        }
        return mRootView;
    }

    private void initView() {
        mChart = mRootView.findViewById(R.id.chart);
        mTvAdd = mRootView.findViewById(R.id.tv_add);
        mTvClear = mRootView.findViewById(R.id.tv_clear);


        mTvClear.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        initChart();

        setChartData(7);
        setChartData(8);
        setChartData(6);
        setChartData(5);
        setChartData(15);
    }

    private void initChart() {
        mChart.setDescription("");
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxisValueFormatter custom = new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return String.valueOf(value) + "%";
            }
        };

        YAxis leftAxis = mChart.getAxisLeft();

        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f);


        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);

        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

    ArrayList<String> xVals = new ArrayList<String>();
    ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

    private void setChartData(int result) {
        synchronized (this) {
            int i = xVals.size();
            Calendar instance = Calendar.getInstance();

            instance.add(Calendar.DATE,i);
            int month = instance.get(Calendar.MONTH);
            int day = instance.get(Calendar.DAY_OF_MONTH);
            xVals.add(String.format(Locale.CHINA, "%d/%d", (month+1),day));

            yVals1.add(new BarEntry(result, i));
            BarDataSet set1;
            mChart.animateY(2000);
            set1 = new BarDataSet(yVals1, "DataSet");
            set1.setBarSpacePercent(35f);
            set1.setColors(ColorTemplate.LIBERTY_COLORS);

            BarDataSet dataSets = new BarDataSet(yVals1, "");

            List<Integer> list = new ArrayList<Integer>();
            list.add(Color.rgb(179, 48, 80));
            list.add(Color.rgb(106, 167, 134));
            list.add(Color.rgb(53, 194, 209));
            list.add(Color.rgb(118, 174, 175));
            list.add(Color.rgb(42, 109, 130));
            list.add(Color.rgb(106, 150, 31));
            list.add(Color.rgb(179, 100, 53));
            list.add(Color.rgb(193, 37, 82));
            list.add(Color.rgb(255, 102, 0));
            list.add(Color.rgb(217, 80, 138));
            list.add(Color.rgb(254, 149, 7));
            list.add(Color.rgb(254, 247, 120));
            //dataSets.setColors(list);

            BarData data = new BarData(xVals, dataSets);
            data.setValueTextSize(10f);
            mChart.setData(data);
        }
    }

    private void clearChartData(){
        yVals1.clear();
        xVals.clear();
        BarDataSet dataSets = new BarDataSet(yVals1, "");
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        mChart.setData(data);
        mChart.invalidate();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:

                try {
                    Dialog1 dialog1 = new Dialog1(getContext());
                    dialog1.show();
                    dialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case R.id.tv_clear:
                clearChartData();
                break;
        }
    }

    public void setData1(int num){
        MainActivity activity = (MainActivity) getActivity();
        int i = activity.getNum() + num;
        activity.setNum( i);
        setChartData(i);
    }

}

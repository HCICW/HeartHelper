package com.hearthelper;

import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class PreviewActivity extends FragmentActivity {

    private SurfaceView mSurfaceView;
    private Camera camera;
    private LineChart mChart;
    private TextView mTvdes;
    private ArrayList<String> xVals =  new ArrayList<String>();
    private ArrayList<Entry> yVals = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initView();
    }

    private void initView() {
        mSurfaceView = findViewById(R.id.preview);
        mTvdes = findViewById(R.id.text);
        mChart = findViewById(R.id.chart);
        try {
            camera = Camera.open(0);
            mSurfaceView.getHolder().addCallback(surfaceCallback);
            mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            startTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initChart();
        setChartData("demo",1);

    }

    private void initChart() {
        mChart.setTouchEnabled(false);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setPinchZoom(true);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTextSize(6f);
    }

    private void setChartData(String content,int index){
        synchronized (this){

            String value = String.valueOf(xVals.size() + 1);
            xVals.add(value);
            yVals.add(new Entry(index,Integer.parseInt(value)));

            LineDataSet set1 = new LineDataSet(yVals, "");
            set1.setDrawCircles(false);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(false);
            set1.setLineWidth(2f);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.rgb(244, 117, 117));
            LineData data = new LineData(xVals,set1);
            mChart.setData(data);
        }

    }

    private void notifyChart(int num){
        setChartData("",num);
        mChart.animateX(2000);
        mChart.setScaleMinima(0.5f, 1f);
        Legend l = mChart.getLegend();
        l.setTextColor(Color.rgb(244, 117, 117));
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setYOffset(100);
        l.setFormSize(20f);
        mChart.invalidate();
    }


    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];
    private static int averageIndex = 0;


    private static int beatsIndex = 0;

    private static final int beatsArraySize = 3;

    private static final int[] beatsArray = new int[beatsArraySize];

    private static double beats = 0;

    private static long startTime = 0;

    public static enum TYPE {
        GREEN, RED
    }

    private static TYPE currentType = TYPE.GREEN;

    public static TYPE getCurrent() {
        return currentType;
    }

    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera cam) {
            if (data == null)
                throw new NullPointerException();
            Camera.Size size = cam.getParameters().getPreviewSize();
            if (size == null)
                throw new NullPointerException();

            int width = size.width;
            int height = size.height;

            int imgAvg = ImageProcessing.PtoRedAvg(data.clone(), height, width);

            if (imgAvg == 0 || imgAvg == 255) {

                return;
            }

            int averageArrayAvg = 0;
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }

            int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
            TYPE newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;

                    Log.e("hci", "onPreviewFrame: impluse" + beats);
                    try {
                        notifyChart((int) beats);
                    } catch (Exception e) {
                        e.printStackTrace();
                        notifyChart(1);
                    }
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if (averageIndex == averageArraySize)
                averageIndex = 0;
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            // Transitioned from one state to another to the same
            if (newType != currentType) {
                currentType = newType;
            }

            long endTime = System.currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000d;
            if (totalTimeInSecs >= 2) {
                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 30 || dpm > 180 || imgAvg < 200) {
                    startTime = System.currentTimeMillis();
                    beats = 0;
                    return;
                }

                if (beatsIndex == beatsArraySize)
                    beatsIndex = 0;
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;
                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                int beatsAvg = (beatsArrayAvg / beatsArrayCnt);
                mTvdes.setText("Current heartrate: "+beatsAvg);
                startTime = System.currentTimeMillis();
                beats = 0;
            }
        }
    };


    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(mSurfaceView.getHolder());
                camera.setPreviewCallback(previewCallback);
            } catch (Throwable t) {
                Log.e("surfaceCallback", "Exception in setPreviewDisplay()", t);
            }
        }


        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Camera.Size size = getSmallestPreviewSize(width, height, parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
            }
            camera.setParameters(parameters);
            camera.startPreview();
        }


        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    };

    private Camera.Size getSmallestPreviewSize(int width, int height,
                                               Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea < resultArea)
                        result = size;
                }
            }
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

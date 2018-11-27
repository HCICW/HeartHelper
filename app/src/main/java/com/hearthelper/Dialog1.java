package com.hearthelper;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Dialog1 extends Dialog {


    private Context context;

    private Button mBtnOk;
    private MyListener myListener;
    private EditText editText;
    public Dialog1(Context context) {
        super(context);
        this.context = context;
        myListener = ((MainActivity) context);
        if (getWindow() != null) {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setCancelable(true);
        setContentView(R.layout.dialog1);

        initView();

    }

    private void initView() {
        editText = findViewById(R.id.e1);
        mBtnOk = findViewById(R.id.ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        String s = editText.getText().toString();
        if (!s.isEmpty()) {
            try {
                int i = Integer.parseInt(s);
                if (myListener != null) {
                    myListener.mydata(i);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}

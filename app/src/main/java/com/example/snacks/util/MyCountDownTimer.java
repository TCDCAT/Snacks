package com.example.snacks.util;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

public class MyCountDownTimer extends CountDownTimer {
private TextView textView;
    //millisInFuture：总时间  countDownInterval：每隔多少时间刷新一次
    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView=textView;
    }

    //计时过程
    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long lm) {
        //不允许再次点击
        textView.setClickable(false);
        textView.setText(lm / 1000 + "秒");
    }

    //计时结束
    @Override
    public void onFinish() {
        textView.setClickable(true);
        textView.setText("重新获取");
    }
}

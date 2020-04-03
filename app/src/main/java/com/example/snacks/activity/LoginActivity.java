package com.example.snacks.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.snacks.R;
import com.example.snacks.base.BaseActivity;
import com.example.snacks.contract.LoginContract;
import com.example.snacks.presenter.LoginPresenter;
import com.example.snacks.util.MyCountDownTimer;
import com.example.snacks.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {
    TextView mTvSendSms;
    EditText mEtPhone;
    LoginPresenter mPresenter;
    private MyCountDownTimer mTimer;
    private String mSmsCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();// 隐藏ActionBar
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initPresenter();


    }
    public  boolean isPhoneNumber(String phone) {
        String regExp = "^1[3|4|5|7|8]\\d{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.find();
    }

    private void clearTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTvSendSms.setText("获取验证码");
            mTvSendSms.setClickable(true);
        }
    }
    private void initPresenter() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
    }
    //初始化控件
    protected void initView() {
        this.mTvSendSms = (TextView) findViewById(R.id.get_verification_code);
        this.mEtPhone = (EditText)findViewById(R.id.phone_edit);
    }
    //初始化事件
    protected void initEvent() {
        mTvSendSms.setOnClickListener(this);
    }

    //初始化数据
    @Override
    protected void initData() {
        mSmsCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        mTimer = new MyCountDownTimer(60000, 1000, mTvSendSms);
    }


    @Override
    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.get_verification_code:
                if (TextUtils.isEmpty(mEtPhone.getText())) {
                    ToastUtil.showConfuse("请输入手机号！");
                } else if (isPhoneNumber(mEtPhone.getText().toString())) {
                    mPresenter.sendSmsCode(mEtPhone.getText().toString(), mSmsCode);
                } else {
                    ToastUtil.showConfuse("请输入正确的手机号");
                }
                break;
            case R.id.login:

                break;
            default:break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showSuccess(String message) {

    }

    @Override
    public void showFailed(String message) {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void onRetry() {

    }

    @Override
    public void startTimer() {
        mTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearTimer();
    }
}

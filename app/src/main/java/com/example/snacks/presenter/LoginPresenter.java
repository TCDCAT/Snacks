package com.example.snacks.presenter;

import androidx.annotation.MainThread;

import com.example.snacks.base.BaseGson;
import com.example.snacks.contract.LoginContract;
import com.example.snacks.http.BaseObserver;
import com.example.snacks.http.RetrofitUtils;
import com.example.snacks.util.ToastUtil;

import rx.schedulers.Schedulers;
import rx.android.schedulers.*;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    @Override
    public void attachView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {

    }


    @Override
    public void sendSmsCode(String phone, String code) {
        RetrofitUtils.getInstance().create().sendSmsCode(phone,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<String>>() {
                    @Override
                    public void onError(String error) {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<String> stringBaseGson) {
                        if (stringBaseGson.isStatus()) {
                            mView.startTimer();
                            ToastUtil.showSuccess("发送验证码成功");
                        } else {
                            ToastUtil.showError("发送验证码失败");
                        }
                    }

                });
    }

    @Override
    public void login(String phone, String code) {

    }
}

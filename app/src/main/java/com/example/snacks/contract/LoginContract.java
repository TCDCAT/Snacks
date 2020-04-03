package com.example.snacks.contract;

import com.example.snacks.base.BaseContact;

public interface LoginContract {
    interface View extends BaseContact.BaseView{
        void startTimer();
    }

    interface Presenter extends BaseContact.BasePresenter<View> {
        void sendSmsCode(String phone, String code);
        void login(String phone, String code);
    }
}

package com.hacknife.example.ui.base.impl;

import android.app.Application;
import android.content.Context;

import com.hacknife.example.ui.base.IBaseModel;
import com.hacknife.example.ui.base.IBaseViewModel;

/**
 * author : 段泽全(hacknife) * e-mail : hacknife@outlook.com * time   : 2019/8/5 * desc   : MVVM * version: 1.0
 */
public abstract class BaseModel<VM extends IBaseViewModel> implements IBaseModel {
    protected VM viewModel;

    public BaseModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onReady() {
    }

    @Override
    public Application application() {
        return viewModel.application();
    }

    @Override
    public Context applicationContext() {
        return viewModel.applicationContext();
    }

    @Override
    public Context context() {
        return viewModel.context();
    }

    @Override
    public void onCleared() {
        viewModel = null;
    }
}
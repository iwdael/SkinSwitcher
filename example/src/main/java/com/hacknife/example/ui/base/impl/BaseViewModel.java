package com.hacknife.example.ui.base.impl;

import android.app.Application;
import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import com.hacknife.example.BR;
import com.hacknife.example.ui.base.IBaseModel;
import com.hacknife.example.ui.base.IBaseView;
import com.hacknife.example.ui.base.IBaseViewModel;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public abstract class BaseViewModel<V extends IBaseView, M extends IBaseModel, D extends ViewDataBinding> extends ViewModel implements IBaseViewModel {
    protected V view;
    protected D binding;
    protected M model;

    public BaseViewModel(V view, D binding) {
        this.view = view;
        this.binding = binding;
        binding.setVariable(BR.viewModel, this);
        model = createModel();
    }

    protected abstract M createModel();

    @Override
    public void onReady() {
        model.onReady();
    }

    @Override
    public Application application() {
        return view.application();
    }

    @Override
    public Context applicationContext() {
        return view.applicationContext();
    }

    @Override
    public Context context() {
        return view.context();
    }

    @Override
    protected void onCleared() {
        model.onCleared();
        model = null;
        view = null;
    }
}

package com.hacknife.example.ui.base.impl;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.hacknife.example.ui.base.IBaseView;
import com.hacknife.example.ui.base.IBaseViewModel;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public abstract class BaseFragment<VM extends IBaseViewModel, BINDING extends ViewDataBinding> extends Fragment implements IBaseView {
    protected VM viewModel;
    protected BINDING binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, attachLayoutRes(), container, false);
        viewModel = (VM) new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                try {
                    return (T) modelClass.getConstructors()[0].newInstance(BaseFragment.this, binding);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).get(createViewModel());
        viewModel.onReady();
        return binding.getRoot();
    }

    protected abstract int attachLayoutRes();

    protected abstract Class<? extends ViewModel> createViewModel();

    @Override
    public Application application() {
        return getActivity().getApplication();
    }

    @Override
    public Context applicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public Context context() {
        return getActivity();
    }
}

package com.hacknife.example.ui.base.impl;

import com.hacknife.example.R;

import android.app.Application;
import android.view.ViewGroup;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hacknife.example.ui.base.IBaseView;
import com.hacknife.example.ui.base.IBaseViewModel;
import com.hacknife.immersive.Immersive;
import com.hacknife.skinswitcher.SkinSwitcher;
import com.hacknife.skinswitcher.SkinSwitcherFactory;

/**
 * author : 段泽全(hacknife) * e-mail : hacknife@outlook.com * time   : 2019/8/5 * desc   : MVVM * version: 1.0
 */
public abstract class BaseActivity<VM extends IBaseViewModel, BINDING extends ViewDataBinding> extends AppCompatActivity implements IBaseView {
    protected VM viewModel;
    protected BINDING binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinSwitcher.setFactory(getLayoutInflater(), SkinSwitcherFactory.class);
        Immersive.setContentView(this, attachLayoutRes(), attachStatusColor(), attachNavigationColor(), attachStatusEmbed(), attachNavigationEmbed());
        binding = DataBindingUtil.bind(((ViewGroup) findViewById(R.id.immersive_content)).getChildAt(0));
        viewModel = (VM) new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                try {
                    return (T) modelClass.getConstructors()[0].newInstance(BaseActivity.this, binding);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).get(createViewModel());
        viewModel.onReady();
        onReady();
    }

    protected void onReady() {

    }

    protected boolean attachNavigationEmbed() {
        return false;
    }

    protected boolean attachStatusEmbed() {
        return false;
    }

    protected int attachNavigationColor() {
        return R.color.colorAccent;
    }

    protected int attachStatusColor() {
        return R.color.colorAccent;
    }

    protected abstract int attachLayoutRes();

    protected abstract Class<? extends ViewModel> createViewModel();

    @Override
    public Application application() {
        return getApplication();
    }

    @Override
    public Context applicationContext() {
        return getApplicationContext();
    }

    @Override
    public Context context() {
        return this;
    }
}
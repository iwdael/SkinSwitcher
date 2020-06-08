package com.hacknife.example.ui.viewmodel.impl;

import com.hacknife.example.ui.base.impl.BaseViewModel;
import com.hacknife.example.ui.model.IMainModel;
import com.hacknife.example.ui.model.impl.MainModel;
import com.hacknife.example.ui.view.IMainView;
import com.hacknife.example.ui.viewmodel.IMainViewModel;
import com.hacknife.example.databinding.ActivityMainBinding;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public class MainViewModel extends BaseViewModel<IMainView, IMainModel, ActivityMainBinding> implements IMainViewModel {

    public MainViewModel(IMainView view, ActivityMainBinding binding) {
        super(view, binding);
    }

    @Override
    protected IMainModel createModel() {
        return new MainModel(this);
    }


}

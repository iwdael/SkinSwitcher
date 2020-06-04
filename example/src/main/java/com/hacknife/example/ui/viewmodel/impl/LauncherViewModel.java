package com.hacknife.example.ui.viewmodel.impl;

import com.hacknife.example.ui.base.impl.BaseViewModel;
import com.hacknife.example.ui.model.ILauncherModel;
import com.hacknife.example.ui.model.impl.LauncherModel;
import com.hacknife.example.ui.view.ILauncherView;
import com.hacknife.example.ui.viewmodel.ILauncherViewModel;
import com.hacknife.example.databinding.ActivityLauncherBinding;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public class LauncherViewModel extends BaseViewModel<ILauncherView, ILauncherModel, ActivityLauncherBinding> implements ILauncherViewModel {

    public LauncherViewModel(ILauncherView view, ActivityLauncherBinding binding) {
        super(view, binding);
    }

    @Override
    protected ILauncherModel createModel() {
        return new LauncherModel(this);
    }


}

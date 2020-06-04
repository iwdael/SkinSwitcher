package com.hacknife.example.ui.model.impl;

import com.hacknife.example.ui.base.impl.BaseModel;
import com.hacknife.example.ui.model.ILauncherModel;
import com.hacknife.example.ui.viewmodel.ILauncherViewModel;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public class LauncherModel extends BaseModel<ILauncherViewModel> implements ILauncherModel {
    public LauncherModel(ILauncherViewModel viewModel) {
        super(viewModel);
    }
}

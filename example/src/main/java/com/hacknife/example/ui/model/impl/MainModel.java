package com.hacknife.example.ui.model.impl;

import com.hacknife.example.ui.base.impl.BaseModel;
import com.hacknife.example.ui.model.IMainModel;
import com.hacknife.example.ui.viewmodel.IMainViewModel;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public class MainModel extends BaseModel<IMainViewModel> implements IMainModel {
    public MainModel(IMainViewModel viewModel) {
        super(viewModel);
    }
}

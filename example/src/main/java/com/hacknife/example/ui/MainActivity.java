package com.hacknife.example.ui;

import android.view.View;

import com.hacknife.example.R;

import androidx.lifecycle.ViewModel;

import com.hacknife.example.dialog.CloseDialog;
import com.hacknife.example.ui.base.impl.BaseActivity;
import com.hacknife.example.ui.view.IMainView;
import com.hacknife.example.ui.viewmodel.impl.MainViewModel;
import com.hacknife.example.ui.viewmodel.IMainViewModel;
import com.hacknife.example.databinding.ActivityMainBinding;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public class MainActivity extends BaseActivity<IMainViewModel, ActivityMainBinding> implements IMainView {
    @Override
    protected Class<? extends ViewModel> createViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onReady() {
        binding.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CloseDialog(MainActivity.this).show();
            }
        });
    }
}

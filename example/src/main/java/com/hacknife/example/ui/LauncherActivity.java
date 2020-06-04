package com.hacknife.example.ui;

import com.hacknife.example.R;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hacknife.example.adapter.MessageAdapter;
import com.hacknife.example.entity.Message;
import com.hacknife.example.ui.base.impl.BaseActivity;
import com.hacknife.example.ui.view.ILauncherView;
import com.hacknife.example.ui.viewmodel.impl.LauncherViewModel;
import com.hacknife.example.ui.viewmodel.ILauncherViewModel;
import com.hacknife.example.databinding.ActivityLauncherBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public class LauncherActivity extends BaseActivity<ILauncherViewModel, ActivityLauncherBinding> implements ILauncherView {
    @Override
    protected Class<? extends ViewModel> createViewModel() {
        return LauncherViewModel.class;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void onReady() {
        MessageAdapter adapter = new MessageAdapter();
        binding.rcView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rcView.setAdapter(adapter);
        List<Message> list = new ArrayList<>();
        list.add(new Message("A"));
        list.add(new Message("B"));
        list.add(new Message("C"));
        list.add(new Message("D"));
        list.add(new Message("E"));
        list.add(new Message("F"));
        list.add(new Message("G"));
        adapter.bindData(list);
    }
}

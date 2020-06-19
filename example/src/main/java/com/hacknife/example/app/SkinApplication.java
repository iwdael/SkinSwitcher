package com.hacknife.example.app;

import android.app.Application;

import com.hacknife.example.skinswitcher.SkinSwitcherView2Adapter;
import com.hacknife.example.skinswitcher.TextViewSkinSwitcherAdapter;
import com.hacknife.example.skinswitcher.ViewSkinSwitcherAdapter;
import com.hacknife.skinswitcher.SkinSwitcher;


public class SkinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinSwitcher.addSkinSwitcherAdapter(new ViewSkinSwitcherAdapter(), new TextViewSkinSwitcherAdapter(),new SkinSwitcherView2Adapter());
    }
}

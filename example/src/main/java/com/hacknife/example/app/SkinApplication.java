package com.hacknife.example.app;

import android.app.Application;

import com.hacknife.example.skinswitcher.TextViewSkinSwitcherAdapter;
import com.hacknife.example.skinswitcher.ViewSkinSwitcherAdapter;
import com.hacknife.skinswitcher.SkinSwitcher;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class SkinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinSwitcher.addSkinSwitcherAdapter(new ViewSkinSwitcherAdapter(), new TextViewSkinSwitcherAdapter());
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy() // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("SkinSwitcher")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}

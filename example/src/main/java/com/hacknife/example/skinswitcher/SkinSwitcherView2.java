package com.hacknife.example.skinswitcher;

import android.view.View;

import com.hacknife.skinswitcher.SkinSwitcherView;
import com.hacknife.skinswitcher.annotation.Filter;
import com.hacknife.skinswitcher.annotation.Switcher;
import com.hacknife.skinswitcher.annotation.Target;

@Target(SkinSwitcherView.class)
public class SkinSwitcherView2 {

    @Filter
    static boolean defaultView(String attr) {
        return attr.equals("defaultView");
    }

    @Switcher
    static void defaultView(SkinSwitcherView view, String resource) {
        view.setDefaultView(resource);
    }
}

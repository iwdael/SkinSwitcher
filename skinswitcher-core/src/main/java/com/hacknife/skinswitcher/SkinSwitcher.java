package com.hacknife.skinswitcher;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.Arrays;
import java.util.List;

public class SkinSwitcher {
    public static void addSkinSwitcherAdapter(SkinSwitcherAdapter... adapter) {
        SkinSwitcherConfig.get().adapters.addAll(Arrays.asList(adapter));
    }

    public static List<SkinSwitcherAdapter> skinSwitcherAdapters() {
        return SkinSwitcherConfig.get().adapters;
    }

    public static LayoutInflater setFactory(LayoutInflater inflater, Factory factory) {
        return SkinSwitcherConfig.setFactory(SkinSwitcherConfig.context2LifeRecycle(inflater.getContext()), inflater, factory);
    }

    public static LayoutInflater setFactory(Context context, Factory factory) {
        return SkinSwitcherConfig.setFactory(SkinSwitcherConfig.context2LifeRecycle(context), LayoutInflater.from(context), factory);
    }

    public static void skinSwitch() {
        SkinSwitcherConfig.skinSwitch();
    }
}

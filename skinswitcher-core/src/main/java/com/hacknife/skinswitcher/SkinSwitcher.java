package com.hacknife.skinswitcher;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.lifecycle.Lifecycle;

import com.hacknife.skinswitcher.core.Factory;

import java.util.Arrays;
import java.util.List;

import static com.hacknife.skinswitcher.SkinSwitcherConfig.context2LifeRecycle;

public class SkinSwitcher {
    public static void addSkinSwitcherAdapter(SkinSwitcherAdapter... adapter) {
        SkinSwitcherConfig.get().adapters.addAll(Arrays.asList(adapter));
    }

    public static List<SkinSwitcherAdapter> skinSwitcherAdapters() {
        return SkinSwitcherConfig.get().adapters;
    }

    public static LayoutInflater setFactory(LayoutInflater inflater, Factory factory) {
        return SkinSwitcherConfig.setFactory(context2LifeRecycle(inflater.getContext()), inflater, factory);
    }

    public static LayoutInflater setFactory(Context context, Factory factory) {
        return SkinSwitcherConfig.setFactory(context2LifeRecycle(context), LayoutInflater.from(context), factory);
    }
}

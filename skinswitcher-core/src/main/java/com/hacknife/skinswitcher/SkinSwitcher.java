package com.hacknife.skinswitcher;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import androidx.lifecycle.Lifecycle;

import java.util.Arrays;
import java.util.List;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class SkinSwitcher {
    public static final String TAG = SkinSwitcher.class.getSimpleName();

    public static void addSkinSwitcherAdapter(SkinSwitcherAdapter... adapter) {
        SkinSwitcherConfig.get().adapters.addAll(Arrays.asList(adapter));
    }

    public static List<SkinSwitcherAdapter> skinSwitcherAdapters() {
        return SkinSwitcherConfig.get().adapters;
    }

    public static Factory setFactory(LayoutInflater inflater, Class<? extends Factory> factory) {
        Lifecycle lifecycle = SkinSwitcherConfig.context2LifeRecycle(inflater.getContext());
        if (lifecycle == null) {
            Log.e(SkinSwitcher.TAG, String.format("setFactory: lifecycle not find by layoutInflater!"));
            return null;
        }
        Factory instanceF = SkinSwitcherConfig.inflaterHasFactory(inflater);
        if (instanceF != null) {
            Log.v(SkinSwitcher.TAG, String.format("context already has a Factory !"));
            return instanceF;
        } else {
            Log.v(SkinSwitcher.TAG, String.format("setFactory2 will replace Factory form %s to Factory !", inflater.getFactory2().getClass().getSimpleName()));
        }
        return SkinSwitcherConfig.setFactory(lifecycle, inflater, factory);
    }

    public static Factory setFactory(Context context, Class<? extends Factory> factory) {
        return setFactory(SkinSwitcherConfig.context2LayoutInflater(context), factory);
    }

    public static LayoutInflater setFactory2(LayoutInflater inflater, Class<? extends Factory> factory) {
        Lifecycle lifecycle = SkinSwitcherConfig.context2LifeRecycle(inflater.getContext());
        if (lifecycle == null) {
            Log.e(SkinSwitcher.TAG, String.format("setFactory2: lifecycle not find by layoutInflater!"));
            return inflater;
        }
        Factory instanceF = SkinSwitcherConfig.inflaterHasFactory(inflater);
        if (instanceF != null) {
            Log.v(SkinSwitcher.TAG, String.format("context already has a Factory !"));
            return inflater;
        } else {
            Log.v(SkinSwitcher.TAG, String.format("setFactory2 will replace Factory form %s to Factory ! ", inflater.getFactory2().getClass().getSimpleName()));
        }
        SkinSwitcherConfig.setFactory(lifecycle, inflater, factory);
        return inflater;
    }


    public static LayoutInflater setFactory2(Context context, Class<? extends Factory> factory) {
        return setFactory2(SkinSwitcherConfig.context2LayoutInflater(context), factory);
    }

    public static void skinSwitch() {
        SkinSwitcherConfig.skinSwitch();
    }
}

package com.hacknife.skinswitcher;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import static com.hacknife.skinswitcher.helper.ViewInflater.forceSetFactory2;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */

public class SkinSwitcherConfig {

    protected static volatile SkinSwitcherConfig switcher;
    protected List<SkinSwitcherAdapter> adapters;
    protected WeakHashMap<String, Factory> hashFactory;
    protected List<OnSkinSwitcherListener> onSkinSwitcherListeners;

    protected static SkinSwitcherConfig get() {
        if (switcher == null) {
            synchronized (SkinSwitcherConfig.class) {
                if (switcher == null) {
                    switcher = new SkinSwitcherConfig();
                    switcher.adapters = new ArrayList<>();
                    switcher.hashFactory = new WeakHashMap<>();
                    switcher.onSkinSwitcherListeners = new ArrayList<>();
                }
            }
        }
        return switcher;
    }

    public static void registerSkinSwitcherListener(OnSkinSwitcherListener listener) {
        get().onSkinSwitcherListeners.add(listener);
    }

    public static void skinSwitch() {
        List<OnSkinSwitcherListener> list = get().onSkinSwitcherListeners;
        for (OnSkinSwitcherListener onSkinSwitcherListener : list) {
            onSkinSwitcherListener.onSwitch();
        }
    }

    public static void unRegisterSkinSwitcherListener(OnSkinSwitcherListener listener) {
        get().onSkinSwitcherListeners.remove(listener);
    }

    protected static LayoutInflater setFactory(Lifecycle lifecycle, LayoutInflater inflater, Factory factory) {
        try {
            Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySet.setAccessible(true);
            mFactorySet.set(inflater, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (lifecycle != null) lifecycle.addObserver(factory);
        inflater.setFactory2(factory);
        if (Build.VERSION.SDK_INT < 21) {
            final LayoutInflater.Factory f = inflater.getFactory();
            if (f instanceof LayoutInflater.Factory2) {
                forceSetFactory2(inflater, (LayoutInflater.Factory2) f);
            } else {
                forceSetFactory2(inflater, factory);
            }
        }
        return inflater;
    }

    protected static Lifecycle context2LifeRecycle(Context context) {
        if (context instanceof AppCompatActivity)
            return ((AppCompatActivity) context).getLifecycle();
        return null;
    }

}

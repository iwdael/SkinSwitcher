package com.hacknife.skinswitcher;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    protected HashMap<String, Map<String, Factory>> hashFactory;
    protected List<OnSkinSwitcherListener> onSkinSwitcherListeners;

    protected static SkinSwitcherConfig get() {
        if (switcher == null) {
            synchronized (SkinSwitcherConfig.class) {
                if (switcher == null) {
                    switcher = new SkinSwitcherConfig();
                    switcher.adapters = new ArrayList<>();
                    switcher.hashFactory = new HashMap<>();
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

    public static void unRegisterSkinSwitcherListener(Lifecycle lifecycle) {
        if (!get().hashFactory.containsKey(String.valueOf(lifecycle.hashCode()))) return;
        Map<String, Factory> mapF = get().hashFactory.remove(String.valueOf(lifecycle.hashCode()));
        for (Map.Entry<String, Factory> entry : mapF.entrySet()) {
            get().onSkinSwitcherListeners.remove(entry.getValue());
        }
    }

    protected static Factory setFactory(Lifecycle lifecycle, LayoutInflater inflater, Class<? extends Factory> factoryClass) {
        Factory factory = null;
        try {
            factory = factoryClass.getConstructor().newInstance();
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
        Map<String, Factory> mapF;
        if (get().hashFactory.containsKey(String.valueOf(lifecycle.hashCode())))
            mapF = get().hashFactory.get(String.valueOf(lifecycle.hashCode()));
        else {
            mapF = new HashMap<>();
            get().hashFactory.put(String.valueOf(lifecycle.hashCode()), mapF);
        }
        mapF.put(String.valueOf(inflater.hashCode()), factory);
        return factory;
    }

    protected static Lifecycle context2LifeRecycle(Context context) {
        if (context instanceof AppCompatActivity)
            return ((AppCompatActivity) context).getLifecycle();
        if (context instanceof ContextWrapper)
            return context2LifeRecycle(((ContextWrapper) context).getBaseContext());
        Log.v(SkinSwitcher.TAG, String.format("context(%s) has not a lifecycle !", context.getClass().getSimpleName()));
        return null;
    }


    protected static LayoutInflater context2LayoutInflater(Context context) {
        if (context instanceof AppCompatActivity) {
            LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
            if (inflater != null)
                return inflater;
        }
        Log.v(SkinSwitcher.TAG, String.format("context(%s) has not a LayoutInflater!", context.getClass().getSimpleName()));
        return LayoutInflater.from(context);
    }

    public static Factory inflaterHasFactory(LayoutInflater inflater) {
        Lifecycle lifecycle = context2LifeRecycle(inflater.getContext());
        if (get().hashFactory.containsKey(String.valueOf(lifecycle.hashCode()))) {
            Map<String, Factory> map = get().hashFactory.get(String.valueOf(lifecycle.hashCode()));
            if (map.containsKey(String.valueOf(inflater.hashCode())))
                return map.get(String.valueOf(inflater.hashCode()));
        }
        return null;
    }
}

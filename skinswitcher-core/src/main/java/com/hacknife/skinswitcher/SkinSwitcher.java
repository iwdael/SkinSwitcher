package com.hacknife.skinswitcher;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.lifecycle.Lifecycle;
import com.hacknife.skinswitcher.core.Factory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public class SkinSwitcher {
    private static final String TAG = SkinSwitcher.class.getName();
    private static volatile SkinSwitcher switcher;
    private List<SkinSwitcherAdapter> adapters;
    private static boolean sCheckedField;
    private static Field sLayoutInflaterFactory2Field;

    private static SkinSwitcher get() {
        if (switcher == null) {
            synchronized (SkinSwitcher.class) {
                if (switcher == null) {
                    switcher = new SkinSwitcher();
                    switcher.adapters = new ArrayList<>();
                }
            }
        }
        return switcher;
    }

    public static void addSkinSwitcherAdapter(SkinSwitcherAdapter... adapter) {
        get().adapters.addAll(Arrays.asList(adapter));
    }

    public static List<SkinSwitcherAdapter> skinSwitcherAdapters() {
        return get().adapters;
    }

    public static void setFactory(Lifecycle lifecycle, LayoutInflater inflater, Factory factory) {
        try {
            Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySet.setAccessible(true);
            mFactorySet.set(inflater, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lifecycle.addObserver(factory);
        inflater.setFactory2(factory);
        if (Build.VERSION.SDK_INT < 21) {
            final LayoutInflater.Factory f = inflater.getFactory();
            if (f instanceof LayoutInflater.Factory2) {
                forceSetFactory2(inflater, (LayoutInflater.Factory2) f);
            } else {
                forceSetFactory2(inflater, factory);
            }
        }
    }

    private static void forceSetFactory2(LayoutInflater inflater, LayoutInflater.Factory2 factory) {
        if (!sCheckedField) {
            try {
                sLayoutInflaterFactory2Field = LayoutInflater.class.getDeclaredField("mFactory2");
                sLayoutInflaterFactory2Field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "forceSetFactory2 Could not find field 'mFactory2' on class " + LayoutInflater.class.getName() + "; inflation may have unexpected results.", e);
            }
            sCheckedField = true;
        }
        if (sLayoutInflaterFactory2Field != null) {
            try {
                sLayoutInflaterFactory2Field.set(inflater, factory);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "forceSetFactory2 could not set the Factory2 on LayoutInflater " + inflater + "; inflation may have unexpected results.", e);
            }
        }
    }
}

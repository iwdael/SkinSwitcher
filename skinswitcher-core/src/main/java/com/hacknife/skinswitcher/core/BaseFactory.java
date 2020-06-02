package com.hacknife.skinswitcher.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.hacknife.skinswitcher.entity.Type;
import com.hacknife.skinswitcher.helper.TypeHelper;
import com.hacknife.skinswitcher.entity.SkinAttr;
import com.hacknife.skinswitcher.helper.ViewInflater;
import com.hacknife.skinswitcher.SkinSwitcherAdapter;
import com.hacknife.skinswitcher.SkinSwitcher;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public abstract class BaseFactory implements Factory {

    protected List<SkinSwitcherAdapter> switcherAdapters = new ArrayList<>();
    protected List<SkinView> skinViews = new ArrayList<>();

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attributeSet) {
        if ("fragment".equals(name)) return null;
        List<SkinAttr> attrs = parseSkinView(context, name, attributeSet);
        if (attrs.isEmpty()) return null;
        @SuppressLint("RestrictedApi") View view = new ViewInflater().createView(parent, name, context, attributeSet, false, Build.VERSION.SDK_INT < 21, true, VectorEnabledTintResources.shouldBeUsed());
        skinViews.add(new SkinView(view, name, attrs));
        return view;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event != Lifecycle.Event.ON_START) return;
        skinSwitch();
    }

    public class SkinView {
        public View view;
        public String name;
        public List<SkinAttr> attrs;

        public SkinView(View view, String name, List<SkinAttr> attrs) {
            this.view = view;
            this.name = name;
            this.attrs = attrs;
        }

        public void skinSwitch() {
            List<SkinSwitcherAdapter> adapters = SkinSwitcher.skinSwitcherAdapters();
            for (SkinSwitcherAdapter adapter : adapters) {
                for (SkinAttr attr : attrs) {
                    if (adapter.switcher(view, name, attr.name, attr.value, attr.type)) return;
                }
            }
            List<SkinSwitcherAdapter> skinSwitcherAdapters = switcherAdapters;
            for (SkinSwitcherAdapter adapter : skinSwitcherAdapters) {
                for (SkinAttr attr : attrs) {
                    if (adapter.switcher(view, name,attr.name, attr.value, attr.type)) return;
                }
            }
        }
    }


    private List<SkinAttr> parseSkinView(Context context, String name, AttributeSet attributeSet) {
        List<SkinAttr> attrSet = new ArrayList<>();
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            String attr = attributeSet.getAttributeName(i);
            String id = attributeSet.getAttributeValue(i);
            if (!id.contains("@")) continue;
            int resId = Integer.parseInt(id.replace("@", ""));
            String value = context.getResources().getResourceEntryName(resId);
            String typeName = context.getResources().getResourceTypeName(resId);
            Type type = TypeHelper.getType(typeName);
            boolean needSwitcher = needSwitcher(name, attr, value, type);
            if (!needSwitcher) continue;
            attrSet.add(new SkinAttr(attr, value, type));
        }
        return attrSet;
    }

    private boolean needSwitcher(String name, String attr, String value, Type type) {
        List<SkinSwitcherAdapter> adapters = SkinSwitcher.skinSwitcherAdapters();
        for (SkinSwitcherAdapter adapter : adapters) {
            if (adapter.filter(name, attr, value, type)) return true;
        }
        List<SkinSwitcherAdapter> skinSwitcherAdapters = switcherAdapters;
        for (SkinSwitcherAdapter adapter : skinSwitcherAdapters) {
            if (adapter.filter(name, attr, value, type)) return true;
        }
        return false;
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {
        return null;
    }
}

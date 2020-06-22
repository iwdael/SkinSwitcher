package com.hacknife.skinswitcher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.hacknife.skinswitcher.entity.SkinAttr;
import com.hacknife.skinswitcher.entity.Type;
import com.hacknife.skinswitcher.helper.SwitcherHelper;
import com.hacknife.skinswitcher.helper.ViewInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
abstract class BaseFactory implements Factory {

    List<SkinSwitcherAdapter> switcherAdapters = new ArrayList<>();
    List<SkinView> skinViews = new ArrayList<>();

    Lifecycle.Event lifeEvent = Lifecycle.Event.ON_ANY;
    int refresh = 0;
    boolean initRefresh = false;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attributeSet) {
        if ("fragment".equals(name)) return null;
        View view = ViewInflater.createView(parent, name, context, attributeSet, false, Build.VERSION.SDK_INT < 21, true, VectorEnabledTintResources.shouldBeUsed());
        if (view == null) return null;
        List<SkinAttr> attrs = parseSkinView(context, view, name, attributeSet);
        if (attrs.isEmpty()) return view;
        SkinView skinView = new SkinView(view, name, attrs);
        skinViews.add(skinView);
        return view;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        lifeEvent = event;
        if (event == Lifecycle.Event.ON_CREATE) {
            skinSwitch(++refresh);
            SkinSwitcherConfig.registerSkinSwitcherListener(this);
        } else if (event == Lifecycle.Event.ON_START) {
            if (!initRefresh) {
                initRefresh = true;
                refresh++;
            }
            skinSwitch(refresh);
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            SkinSwitcherConfig.unRegisterSkinSwitcherListener(source.getLifecycle());
        }
    }

    @Override
    public void onSwitch() {
        ++refresh;
        if (lifeEvent != Lifecycle.Event.ON_RESUME) return;
        skinSwitch(refresh);
    }

    private void skinSwitch(int refresh) {
        for (SkinView skinView : skinViews) {
            if (skinView.refresh != refresh) {
                skinView.skinSwitch();
                skinView.refresh = refresh;
            }
        }
    }

    public class SkinView {
        public View view;
        public String name;
        public List<SkinAttr> attrs;
        public int refresh;

        public SkinView(View view, String name, List<SkinAttr> attrs) {
            this.view = view;
            this.name = name;
            this.attrs = attrs;
            this.refresh = 0;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"view\":" + view.hashCode() +
                    ", \"name\":\'" + name + "\'" +
                    ", \"attrs\":" + attrs +
                    '}';
        }

        public void skinSwitch() {
            for (SkinAttr attr : attrs) {
                boolean switcher = false;
                List<SkinSwitcherAdapter> adapters = SkinSwitcher.skinSwitcherAdapters();
                for (SkinSwitcherAdapter adapter : adapters) {
                    if (adapter.switcher(view, name, attr.name, attr.value, attr.type)) {
                        switcher = true;
                        break;
                    }
                }
                if (switcher) continue;
                List<SkinSwitcherAdapter> skinSwitcherAdapters = switcherAdapters;
                for (SkinSwitcherAdapter adapter : skinSwitcherAdapters) {
                    if (adapter.switcher(view, name, attr.name, attr.value, attr.type)) return;
                }
            }
        }
    }


    private List<SkinAttr> parseSkinView(Context context, View view, String name, AttributeSet attributeSet) {
        List<SkinAttr> attrSet = new ArrayList<>();
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            String attr = attributeSet.getAttributeName(i);
            String id = attributeSet.getAttributeValue(i);
            if (!id.contains("@")) continue;
            int resId = Integer.parseInt(id.replace("@", ""));
            if (resId == 0) continue;
            String value = context.getResources().getResourceEntryName(resId);
            String typeName = context.getResources().getResourceTypeName(resId);
            Type type = SwitcherHelper.getType(typeName);
            boolean needSwitcher = needSwitcher(view, name, attr, value, type);
            if (!needSwitcher) continue;
            attrSet.add(new SkinAttr(attr, value, type));
        }
        return attrSet;
    }

    private boolean needSwitcher(View view, String name, String attr, String value, Type type) {
        List<SkinSwitcherAdapter> adapters = SkinSwitcher.skinSwitcherAdapters();
        for (SkinSwitcherAdapter adapter : adapters) {
            if (adapter.filter(view, name, attr, value, type)) return true;
        }
        List<SkinSwitcherAdapter> skinSwitcherAdapters = switcherAdapters;
        for (SkinSwitcherAdapter adapter : skinSwitcherAdapters) {
            if (adapter.filter(view, name, attr, value, type)) return true;
        }
        return false;
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attr) {
        return null;
    }
}

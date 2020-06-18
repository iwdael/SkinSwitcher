package com.hacknife.skinswitcher;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public abstract class SkinSwitcherView extends FrameLayout {
    protected View view;
    private List<SkinSwitcherMethod> methods = new ArrayList<>();

    public SkinSwitcherView(@NonNull Context context) {
        super(context);
    }

    public SkinSwitcherView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SkinSwitcherView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SkinSwitcherView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setDefaultView(String defaultView) {
        try {
            setDefaultView(Class.forName(defaultView));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    protected void setDefaultView(Class<?> defaultView) {

    }
    protected void restoreView() {
        for (SkinSwitcherMethod method : methods) {
            restore(method);
        }
    }

    protected abstract void restore(SkinSwitcherMethod method);

    public View getView() {
        return view;
    }

    protected void addStickyMethod(SkinSwitcherMethod method) {
        methods.add(method);
    }

    protected void addMethod(SkinSwitcherMethod method) {
        List<SkinSwitcherMethod> skinSwitcherMethods = new ArrayList<>();
        for (SkinSwitcherMethod skinSwitcherMethod : methods) {
            if (skinSwitcherMethod.getMethodId() == method.getMethodId()) {
                skinSwitcherMethods.add(skinSwitcherMethod);
            }
        }
        for (SkinSwitcherMethod skinSwitcherMethod : skinSwitcherMethods) {
            methods.remove(skinSwitcherMethod);
        }
        methods.add(method);
    }
}

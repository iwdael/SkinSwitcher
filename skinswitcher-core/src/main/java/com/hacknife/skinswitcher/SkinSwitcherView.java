package com.hacknife.skinswitcher;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * author  : Hacknife
 * e-mail  : hacknife@outlook.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */
public abstract class SkinSwitcherView extends FrameLayout {
    protected View view;

    protected AttributeSet attrs;

    protected Context context;

    private List<SkinSwitcherMethod> methods = new ArrayList<>();

    public SkinSwitcherView(@NonNull Context context) {
        this(context, null);
    }

    public SkinSwitcherView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinSwitcherView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinSwitcherView);
        String className = typedArray.getString(R.styleable.SkinSwitcherView_defaultView);
        typedArray.recycle();
        try {
            view = (View) Class.forName(className).getConstructor(Context.class, AttributeSet.class).newInstance(context, attrs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(view, layoutParams);
        this.attrs = attrs;
        this.context = context;
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

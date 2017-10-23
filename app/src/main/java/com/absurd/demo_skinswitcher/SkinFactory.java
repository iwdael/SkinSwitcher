package com.absurd.demo_skinswitcher;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;

/**
 * Created by 段泽全 on 2017/10/23.
 * Github：https://github.com/mr-absurd
 * Emile:4884280@qq.com
 */

public class SkinFactory implements LayoutInflater.Factory2 {
    private static String[] preListPackage = new String[]{
            "android.view.",
            "android.widgit.",
            "android.webkit."
    };

    @Override
    public View onCreateView(View v, String name, Context context, AttributeSet attributeSet) {
        View view = null;
        if (name.contains(".")) {
            view = createView(context, attributeSet, name);
        } else {
            for (String pre : preListPackage) {
                view = createView(context, attributeSet, pre + name);
                if (view!=null){
                    break;
                }
            }
        }
        if (view!=null){
            parseSkinView(context,attributeSet,view);
        }
        return view;
    }

    private void parseSkinView(Context context, AttributeSet attributeSet, View view) {
    }

    private View createView(Context context, AttributeSet attributeSet, String name) {
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {
        try {
            Class<?> clazz = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor = (Constructor<? extends View>) clazz.getConstructor(new Class[]{Context.class, AttributeSet.class});
            return constructor.newInstance(context, attributeSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

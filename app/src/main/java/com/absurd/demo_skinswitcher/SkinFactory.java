package com.absurd.demo_skinswitcher;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 段泽全 on 2017/10/23.
 * Github：https://github.com/mr-absurd
 * Emile:4884280@qq.com
 */

public class SkinFactory implements LayoutInflater.Factory2 {
    private List<SkinView> skins = new ArrayList<>();
    private static String[] preListPackage = new String[]{
            "android.view.",
            "android.widget.",
            "android.webkit."
    };

    @Override
    public View onCreateView(View v, String name, Context context, AttributeSet attributeSet) {
        View view = null;
        Log.v("TAG","--nameView-->>"+name);
        if (name.contains(".")) {
            view = createView(context, attributeSet, name);
        } else {
            for (String pre : preListPackage) {
                view = createView(context, attributeSet, pre + name);
                if (view != null) {
                    break;
                }
            }
        }
        if (view != null) {
            parseSkinView(context, attributeSet, view);
        }
        return view;
    }

    private void parseSkinView(Context context, AttributeSet attributeSet, View view) {
        List<SkinEntity> skinSet = new ArrayList<>();
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            String name = attributeSet.getAttributeName(i);
            String value = attributeSet.getAttributeValue(i);

            Log.v("TAG", "---name-->>" + name);

            Log.v("TAG", "--value-->>" + value);


            if (name.equals("background") | name.equals("textColor")) {
                Log.v("TAG","------------background--------------->>");
                int id = Integer.parseInt(value.replace("@",""));
                String value_name = context.getResources().getResourceEntryName(id);
                String value_type = context.getResources().getResourceTypeName(id);
                SkinEntity entity = new SkinEntity(name, value_name, id, value_type);
                skinSet.add(entity);
            }
        }
        if (!skinSet.isEmpty()) {
            SkinView skinView = new SkinView(view, skinSet);
            skins.add(skinView);
            skinView.apply();
        }
    }

    private View createView(Context context, AttributeSet attributeSet, String name) {
        try {
            Class<?> clazz = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor = (Constructor<? extends View>) clazz.getConstructor(new Class[]{Context.class, AttributeSet.class});
            return constructor.newInstance(context, attributeSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {

        return null;
    }
}

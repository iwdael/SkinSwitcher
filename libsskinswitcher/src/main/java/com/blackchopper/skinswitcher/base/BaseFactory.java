package com.blackchopper.skinswitcher.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : SkinSwitcher
 */
public abstract class BaseFactory extends Factory {
    private List<SkinView> skins = new ArrayList<>();
    private static String[] preListPackage = new String[]{
            "android.view.",
            "android.widget.",
            "android.webkit."
    };

    @Override
    public View onCreateView(View v, String name, Context context, AttributeSet attributeSet) {
        View view = null;
        //如果布局中的控件是全类名，直接反射，否则只能挨着试
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
            //解析控件，查找是否能够换肤
            parseSkinView(context, attributeSet, view);
        }
        return view;
    }

    private void parseSkinView(Context context, AttributeSet attributeSet, View view) {
        List<SkinAttr> attrSet = new ArrayList<>();
        //遍历控件的所有属性
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            String attrName = attributeSet.getAttributeName(i);
            String value = attributeSet.getAttributeValue(i);


            //测试专用，当出现以下属性时，认为用有换肤的可能
            if (attributeFilter(attrName, value)) {
                int id = Integer.parseInt(value.replace("@", ""));
                String attrValue = context.getResources().getResourceEntryName(id);
                String attrType = context.getResources().getResourceTypeName(id);
                SkinAttr entity = new SkinAttr(attrName, attrValue, attrType);
                attrSet.add(entity);
            }
        }
        if (!attrSet.isEmpty()) {
            SkinView skinView = new SkinView(view, attrSet);
            skins.add(skinView);
            skinView.apply();
        }
    }

    public void apply() {
        for (SkinView skin : skins) {
            skin.apply();
        }
    }

    protected abstract void switcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj);

    protected abstract boolean attributeFilter(String attrName, String value);

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


    public class SkinView {
        private View view;
        private List<SkinAttr> attrs;

        public SkinView(View view, List<SkinAttr> attrs) {
            this.view = view;
            this.attrs = attrs;
        }

        public void apply() {
            for (SkinAttr attr : attrs) {
                Object attrObj = SkinManager.getInstance().getAttrValue(attr.getAttrType(), attr.getAttrValue());
                if (attrObj == null) return;
                switcher(view, attr.getAttrType(), attr.getAttrName(), attr.getAttrValue(), attrObj);
            }
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {

        return null;
    }
}

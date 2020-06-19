package com.hacknife.example.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.hacknife.example.R;
import com.hacknife.skinswitcher.SkinSwitcherViewAttribute;
import com.hacknife.skinswitcher.annotation.Method;
import com.hacknife.skinswitcher.annotation.State;
import com.hacknife.skinswitcher.annotation.SwitcherView;
import com.hacknife.skinswitcher.entity.Attribute;

@SwitcherView(value = 123, proxy = "TextView")
public class MarvelTextView extends androidx.appcompat.widget.AppCompatTextView implements SkinSwitcherViewAttribute {
    int color = 0;

    public MarvelTextView(Context context) {
        super(context);
    }

    public MarvelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(context.getResources().getColor(R.color.default_textColor));
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarvelTextView);
        typedArray.recycle();
    }

    public MarvelTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Method(State.once)
    public void setText(String text) {
        super.setText(text);
    }

    @Override
    public Attribute saveAttribute() {
        return new Attribute().put(R.attr.txtColor, color).put(android.R.attr.gravity, getGravity());
    }

    @Override
    public void restoreAttribute(Attribute attrs) {
        setTextColor((Integer) attrs.get(R.attr.txtColor));
        color = (Integer) attrs.get(R.attr.txtColor);
        setGravity((Integer) attrs.get(android.R.attr.gravity));
    }
}

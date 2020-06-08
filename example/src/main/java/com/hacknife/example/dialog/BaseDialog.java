package com.hacknife.example.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;
import androidx.databinding.DataBindingUtil;

import com.hacknife.example.R;
import com.hacknife.example.databinding.DialogCloseBinding;
import com.hacknife.skinswitcher.SkinSwitcher;
import com.hacknife.skinswitcher.SkinSwitcherFactory;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public abstract class BaseDialog extends Dialog {
    LayoutInflater inflater;

    public BaseDialog(Context context) {
        super(context, R.style.common_dialog_transparent_shadowed);
        init();
    }


    public void init() {
        inflater = SkinSwitcher.setFactory2(getLayoutInflater(), SkinSwitcherFactory.class);
        setContentView(DataBindingUtil.bind(inflater.inflate(attachLayoutRes(), null)).getRoot(),new ViewGroup.LayoutParams(WRAP_CONTENT,WRAP_CONTENT));
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    protected abstract int attachLayoutRes();
}

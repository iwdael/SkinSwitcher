package com.hacknife.example.dialog;

import android.content.Context;

import com.hacknife.example.R;

public class CloseDialog extends BaseDialog {
    public CloseDialog(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.dialog_close;
    }
}

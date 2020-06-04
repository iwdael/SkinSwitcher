package com.hacknife.example.adapter.base;

import android.view.View;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
@Deprecated
public interface OnRecyclerListener<E> {
    void onClick(E e, int position, View v);

    boolean onLongClick(E e, int position, View v);

    void selectCallback(int position);
}

package com.hacknife.example.adapter.base;

import android.view.View;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public interface OnItemClickListener2<E> extends OnRecyclerViewListener<E> {
    boolean onItemClick(E t, int last, int current, View view);
}

package com.hacknife.example.adapter.base;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public interface OnItemClickListener<E> extends OnRecyclerViewListener<E> {
    void onItemClick(E t);
}
